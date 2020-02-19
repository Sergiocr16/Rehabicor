/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { ComorbiditieDetailComponent } from 'app/entities/comorbiditie/comorbiditie-detail.component';
import { Comorbiditie } from 'app/shared/model/comorbiditie.model';

describe('Component Tests', () => {
    describe('Comorbiditie Management Detail Component', () => {
        let comp: ComorbiditieDetailComponent;
        let fixture: ComponentFixture<ComorbiditieDetailComponent>;
        const route = ({ data: of({ comorbiditie: new Comorbiditie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [ComorbiditieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ComorbiditieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComorbiditieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.comorbiditie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
