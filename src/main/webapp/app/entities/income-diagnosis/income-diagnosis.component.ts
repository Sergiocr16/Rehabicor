import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IIncomeDiagnosis } from 'app/shared/model/income-diagnosis.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IncomeDiagnosisService } from './income-diagnosis.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';

@Component({
    selector: 'jhi-income-diagnosis',
    templateUrl: './income-diagnosis.component.html'
})
export class IncomeDiagnosisComponent implements OnInit, OnDestroy {
    incomeDiagnoses: IIncomeDiagnosis[];
    rehabilitationCenters: IRehabilitationCenter[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    rcId: number;

    constructor(
        protected incomeDiagnosisService: IncomeDiagnosisService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected global: GlobalVariablesService,
        protected modal: ModalService,
        protected rehabilitationCenterService: RehabilitationCenterService
    ) {
        this.rehabilitationCenters = [];
        this.incomeDiagnoses = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.incomeDiagnosisService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                rehabilitationId: this.rcId
            })
            .subscribe((res: HttpResponse<IIncomeDiagnosis[]>) => this.paginateIncomeDiagnoses(res.body, res.headers));
    }
    loadRC() {
        this.rehabilitationCenterService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe((res: HttpResponse<IRehabilitationCenter[]>) => this.getRehabilitationCenter(res.body));
    }
    protected getRehabilitationCenter(data: IRehabilitationCenter[]) {
        this.rehabilitationCenters = this.global.paginateRehabilitationCenters(data);
    }
    reset() {
        this.page = 0;
        this.incomeDiagnoses = [];
        this.loadAll();
    }
    changeRC(index) {
        this.rcId = this.rehabilitationCenters[index].id;
        this.reset();
    }
    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadRC();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIncomeDiagnoses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncomeDiagnosis) {
        return item.id;
    }

    registerChangeInIncomeDiagnoses() {
        this.eventSubscriber = this.eventManager.subscribe('incomeDiagnosisListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    delete(incomeDiagnosis) {
        this.modal.confirmDialog('delete', () => {
            incomeDiagnosis.deleted = true;
            this.subscribeToSaveResponse(this.incomeDiagnosisService.update(incomeDiagnosis));
        });
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeDiagnosis>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }
    protected onSaveSuccess() {
        this.reset();
        this.modal.message('El diagnóstico de ingreso se ha eliminado correctamente.');
    }

    protected onSaveError() {
        this.modal.message('Ups! Sucedió un error.');
    }
    protected paginateIncomeDiagnoses(data: IIncomeDiagnosis[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.incomeDiagnoses.push(data[i]);
        }
    }
}
