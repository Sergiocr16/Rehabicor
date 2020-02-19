/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { IncomeDiagnosisPatientService } from 'app/entities/income-diagnosis-patient/income-diagnosis-patient.service';
import { IIncomeDiagnosisPatient, IncomeDiagnosisPatient } from 'app/shared/model/income-diagnosis-patient.model';

describe('Service Tests', () => {
    describe('IncomeDiagnosisPatient Service', () => {
        let injector: TestBed;
        let service: IncomeDiagnosisPatientService;
        let httpMock: HttpTestingController;
        let elemDefault: IIncomeDiagnosisPatient;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IncomeDiagnosisPatientService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new IncomeDiagnosisPatient(0, 'AAAAAAA', false, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a IncomeDiagnosisPatient', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new IncomeDiagnosisPatient(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a IncomeDiagnosisPatient', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        exist: true,
                        incomeDiagnosisRelation: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of IncomeDiagnosisPatient', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        exist: true,
                        incomeDiagnosisRelation: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a IncomeDiagnosisPatient', async () => {
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
