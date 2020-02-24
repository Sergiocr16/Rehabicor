import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IRehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RehabilitationGroupService } from './rehabilitation-group-panel-data.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';

@Component({
    selector: 'jhi-rehabilitation-group',
    templateUrl: './rehabilitation-group-panel-data.component.html'
})
export class RehabilitationGroupPanelDataComponent implements OnInit, OnDestroy {
    rehabilitationGroups: IRehabilitationGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected rehabilitationGroupService: RehabilitationGroupService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected modal: ModalService,
        private global: GlobalVariablesService
    ) {
        this.rehabilitationGroups = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.rehabilitationGroupService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                rehabilitationId: this.global.rehabCenter
            })
            .subscribe((res: HttpResponse<IRehabilitationGroup[]>) => this.paginateRehabilitationGroups(res.body, res.headers));
    }

    reset() {
        this.page = 0;
        this.rehabilitationGroups = [];
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
        this.registerChangeInRehabilitationGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRehabilitationGroup) {
        return item.id;
    }

    registerChangeInRehabilitationGroups() {
        this.eventSubscriber = this.eventManager.subscribe('rehabilitationGroupListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    delete(rehabGroup) {
        this.modal.confirmDialog('delete', () => {
            this.rehabilitationGroupService.delete(rehabGroup.id).subscribe(response => {
                this.rehabilitationGroups.splice(this.rehabilitationGroups.indexOf(rehabGroup), 1);
                this.modal.message('Se ha eliminado el grupo correctamente');
            });
        });
    }

    protected paginateRehabilitationGroups(data: IRehabilitationGroup[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.rehabilitationGroups.push(data[i]);
        }
    }
}
