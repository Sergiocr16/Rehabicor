import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { GlobalVariablesService } from '../../shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { IComorbiditie } from 'app/shared/model/comorbiditie.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ComorbiditieService } from './comorbiditie.service';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
@Component({
    selector: 'jhi-comorbiditie',
    templateUrl: './comorbiditie.component.html'
})
export class ComorbiditieComponent implements OnInit, OnDestroy {
    rehabilitationCenters: IRehabilitationCenter[];
    comorbidities: IComorbiditie[];
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
        protected comorbiditieService: ComorbiditieService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected global: GlobalVariablesService,
        protected modal: ModalService,
        protected rehabilitationCenterService: RehabilitationCenterService
    ) {
        this.rehabilitationCenters = [];
        this.comorbidities = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.comorbiditieService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                rehabilitationId: this.rcId
            })
            .subscribe((res: HttpResponse<IComorbiditie[]>) => this.paginateComorbidities(res.body, res.headers));
    }

    loadRC() {
        this.rehabilitationCenterService
            .query({
                page: 0,
                size: 10000,
                sort: this.sort()
            })
            .subscribe((res: HttpResponse<IRehabilitationCenter[]>) => this.getRehabilitationCenter(res.body));
    }
    protected getRehabilitationCenter(data: IRehabilitationCenter[]) {
        this.rehabilitationCenters = this.global.paginateRehabilitationCenters(data);
    }

    reset() {
        this.page = 0;
        this.comorbidities = [];
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
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInComorbidities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IComorbiditie) {
        return item.id;
    }

    registerChangeInComorbidities() {
        this.eventSubscriber = this.eventManager.subscribe('comorbiditieListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    delete(comorbiditie) {
        this.modal.confirmDialog('delete', () => {
            comorbiditie.deleted = true;
            this.subscribeToSaveResponse(this.comorbiditieService.update(comorbiditie));
        });
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IComorbiditie>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }
    protected onSaveSuccess() {
        this.reset();
        this.modal.message('La comorbilidad se ha eliminado correctamente.');
    }

    protected onSaveError() {
        this.modal.message('Ups! Sucedió un error.');
    }
    protected paginateComorbidities(data: IComorbiditie[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.comorbidities.push(data[i]);
        }
    }
}
