/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { DepressiveSymptomsSessionDetailComponent } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session-detail.component';
import { DepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

describe('Component Tests', () => {
    describe('DepressiveSymptomsSession Management Detail Component', () => {
        let comp: DepressiveSymptomsSessionDetailComponent;
        let fixture: ComponentFixture<DepressiveSymptomsSessionDetailComponent>;
        const route = ({ data: of({ depressiveSymptomsSession: new DepressiveSymptomsSession(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [DepressiveSymptomsSessionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepressiveSymptomsSessionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepressiveSymptomsSessionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.depressiveSymptomsSession).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
