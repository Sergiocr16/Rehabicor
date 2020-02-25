import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { ModalService } from 'app/shared/util/modal.service';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IMinorEvent } from 'app/shared/model/minor-event.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MinorEventService } from './minor-event.service';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { IRehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';

@Component({
    selector: 'jhi-minor-event',
    templateUrl: './minor-event.component.html'
})
export class MinorEventComponent implements OnInit, OnDestroy {
    rehabilitationCenters: IRehabilitationCenter[];
    minorEvents: IMinorEvent[];
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
        protected minorEventService: MinorEventService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected global: GlobalVariablesService,
        protected modal: ModalService,
        protected rehabilitationCenterService: RehabilitationCenterService
    ) {
        this.rehabilitationCenters = [];
        this.minorEvents = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.minorEventService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                rehabilitationId: this.rcId
            })
            .subscribe((res: HttpResponse<IMinorEvent[]>) => this.paginateMinorEvents(res.body, res.headers));
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
        this.minorEvents = [];
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
        this.registerChangeInMinorEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMinorEvent) {
        return item.id;
    }

    registerChangeInMinorEvents() {
        this.eventSubscriber = this.eventManager.subscribe('minorEventListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    delete(minorEvent) {
        this.modal.confirmDialog('delete', () => {
            minorEvent.deleted = true;
            this.subscribeToSaveResponse(this.minorEventService.update(minorEvent));
        });
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMayorEvent>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.reset();
        this.modal.message('El evento menor se ha eliminado correctamente.');
    }

    protected onSaveError() {
        this.modal.message('Ups! Sucedió un error.');
    }
    protected paginateMinorEvents(data: IMinorEvent[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.minorEvents.push(data[i]);
        }
    }
}
