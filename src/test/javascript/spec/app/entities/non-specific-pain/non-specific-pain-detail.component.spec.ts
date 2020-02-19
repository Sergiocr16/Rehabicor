/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { NonSpecificPainDetailComponent } from 'app/entities/non-specific-pain/non-specific-pain-detail.component';
import { NonSpecificPain } from 'app/shared/model/non-specific-pain.model';

describe('Component Tests', () => {
    describe('NonSpecificPain Management Detail Component', () => {
        let comp: NonSpecificPainDetailComponent;
        let fixture: ComponentFixture<NonSpecificPainDetailComponent>;
        const route = ({ data: of({ nonSpecificPain: new NonSpecificPain(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [NonSpecificPainDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NonSpecificPainDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NonSpecificPainDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nonSpecificPain).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
