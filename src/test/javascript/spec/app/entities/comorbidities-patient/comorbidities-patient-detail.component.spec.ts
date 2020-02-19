/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { ComorbiditiesPatientDetailComponent } from 'app/entities/comorbidities-patient/comorbidities-patient-detail.component';
import { ComorbiditiesPatient } from 'app/shared/model/comorbidities-patient.model';

describe('Component Tests', () => {
    describe('ComorbiditiesPatient Management Detail Component', () => {
        let comp: ComorbiditiesPatientDetailComponent;
        let fixture: ComponentFixture<ComorbiditiesPatientDetailComponent>;
        const route = ({ data: of({ comorbiditiesPatient: new ComorbiditiesPatient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [ComorbiditiesPatientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ComorbiditiesPatientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComorbiditiesPatientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.comorbiditiesPatient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
