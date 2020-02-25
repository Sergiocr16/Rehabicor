import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { GlobalVariablesService } from '../../shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RehabilitationCenterService } from './rehabilitation-center.service';
// import { IComorbiditie } from 'app/shared/model/comorbiditie.model';

@Component({
    selector: 'jhi-rehabilitation-center',
    templateUrl: './rehabilitation-center.component.html'
})
export class RehabilitationCenterComponent implements OnInit, OnDestroy {
    rehabilitationCenters: IRehabilitationCenter[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    rehabCenterId;
    rehabilitationCenter: any;
    constructor(
        protected rehabilitationCenterService: RehabilitationCenterService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected global: GlobalVariablesService,
        protected modal: ModalService
    ) {
        this.rehabilitationCenters = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.global.loading();
        this.rehabilitationCenterService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe((res: HttpResponse<IRehabilitationCenter[]>) => this.paginateRehabilitationCenters(res.body, res.headers));
    }

    reset() {
        this.page = 0;
        this.rehabilitationCenters = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRehabilitationCenters();
        this.global.setTitle('Centros de rehabilitación');
        this.rehabCenterId = this.global.rehabCenter;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRehabilitationCenter) {
        return item.id;
    }

    registerChangeInRehabilitationCenters() {
        this.eventSubscriber = this.eventManager.subscribe('rehabilitationCenterListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRehabilitationCenters(data: IRehabilitationCenter[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.rehabilitationCenters.push(data[i]);
        }
    }

    delete(rehabilitationCenter) {
        this.modal.confirmDialog('delete', () => {
            rehabilitationCenter.deleted = true;
            this.subscribeToSaveResponse(this.rehabilitationCenterService.update(rehabilitationCenter));
        });
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRehabilitationCenter>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.reset();
        this.modal.message('El centro de rehabilitación se ha eliminado correctamente.');
    }

    protected onSaveError() {
        this.modal.message('Ups! Sucedió un error.');
    }
}
