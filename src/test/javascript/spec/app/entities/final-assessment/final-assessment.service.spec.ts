/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FinalAssessmentService } from 'app/entities/final-assessment/final-assessment.service';
import { IFinalAssessment, FinalAssessment } from 'app/shared/model/final-assessment.model';

describe('Service Tests', () => {
    describe('FinalAssessment Service', () => {
        let injector: TestBed;
        let service: FinalAssessmentService;
        let httpMock: HttpTestingController;
        let elemDefault: IFinalAssessment;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FinalAssessmentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new FinalAssessment(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        executionDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a FinalAssessment', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        executionDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        executionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new FinalAssessment(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a FinalAssessment', async () => {
                const returnedFromService = Object.assign(
                    {
                        smoking: 'BBBBBB',
                        weight: 'BBBBBB',
                        size: 'BBBBBB',
                        iMC: 'BBBBBB',
                        hbiac: 'BBBBBB',
                        baselineFunctionalCapacity: 'BBBBBB',
                        lDL: 'BBBBBB',
                        hDL: 'BBBBBB',
                        cardiovascularRisk: 'BBBBBB',
                        isWorking: true,
                        deceased: true,
                        abandonment: true,
                        abandonmentMedicCause: true,
                        hospitalized: true,
                        deleted: true,
                        reevaluation: true,
                        executionDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        executionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of FinalAssessment', async () => {
                const returnedFromService = Object.assign(
                    {
                        smoking: 'BBBBBB',
                        weight: 'BBBBBB',
                        size: 'BBBBBB',
                        iMC: 'BBBBBB',
                        hbiac: 'BBBBBB',
                        baselineFunctionalCapacity: 'BBBBBB',
                        lDL: 'BBBBBB',
                        hDL: 'BBBBBB',
                        cardiovascularRisk: 'BBBBBB',
                        isWorking: true,
                        deceased: true,
                        abandonment: true,
                        abandonmentMedicCause: true,
                        hospitalized: true,
                        deleted: true,
                        reevaluation: true,
                        executionDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        executionDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a FinalAssessment', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
