import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IPatient } from 'app/shared/model/patient.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PatientService } from './patient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-patient',
    templateUrl: './patient.component.html'
})
export class PatientComponent implements OnInit, OnDestroy {
    patients: IPatient[];
    currentAccount: any;
    eventSubscriber: Subscription;
    routeData: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    previousPage: any;
    ready = false;
    constructor(
        protected patientService: PatientService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.patients = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }
    trackIdentity(index, item) {
        return item.id;
    }
    loadAll() {
        this.patientService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IPatient[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    reset() {
        this.page = 0;
        this.patients = [];
        this.loadAll();
    }

    loadPage(page) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['./'], {
            relativeTo: this.activatedRoute.parent,
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPatients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPatient) {
        return item.id;
    }

    registerChangeInPatients() {
        this.eventSubscriber = this.eventManager.subscribe('patientListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.patients = data;
        this.ready = true;
    }

    private onError(error) {}
}
