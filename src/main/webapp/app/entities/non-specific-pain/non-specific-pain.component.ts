import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { GlobalVariablesService } from '../../shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { INonSpecificPain } from 'app/shared/model/non-specific-pain.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NonSpecificPainService } from './non-specific-pain.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';

@Component({
    selector: 'jhi-non-specific-pain',
    templateUrl: './non-specific-pain.component.html'
})
export class NonSpecificPainComponent implements OnInit, OnDestroy {
    rehabilitationCenters: IRehabilitationCenter[];
    nonSpecificPains: INonSpecificPain[];
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
        protected nonSpecificPainService: NonSpecificPainService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected global: GlobalVariablesService,
        protected modal: ModalService,
        protected rehabilitationCenterService: RehabilitationCenterService
    ) {
        this.rehabilitationCenters = [];
        this.nonSpecificPains = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.nonSpecificPainService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                rehabilitationId: this.rcId
            })
            .subscribe((res: HttpResponse<INonSpecificPain[]>) => this.paginateNonSpecificPains(res.body, res.headers));
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
        this.nonSpecificPains = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    changeRC(index) {
        this.rcId = this.rehabilitationCenters[index].id;
        this.reset();
    }

    ngOnInit() {
        this.loadRC();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNonSpecificPains();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INonSpecificPain) {
        return item.id;
    }

    registerChangeInNonSpecificPains() {
        this.eventSubscriber = this.eventManager.subscribe('nonSpecificPainListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    delete(nonSpecificPain) {
        this.modal.confirmDialog('delete', () => {
            nonSpecificPain.deleted = true;
            this.subscribeToSaveResponse(this.nonSpecificPainService.update(nonSpecificPain));
        });
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMayorEvent>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.reset();
        this.modal.message('El evento mayor se ha eliminado correctamente.');
    }

    protected onSaveError() {
        this.modal.message('Ups! Sucedió un error.');
    }

    protected paginateNonSpecificPains(data: INonSpecificPain[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.nonSpecificPains.push(data[i]);
        }
    }
}
