/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { ComorbiditieUpdateComponent } from 'app/entities/comorbiditie/comorbiditie-update.component';
import { ComorbiditieService } from 'app/entities/comorbiditie/comorbiditie.service';
import { Comorbiditie } from 'app/shared/model/comorbiditie.model';

describe('Component Tests', () => {
    describe('Comorbiditie Management Update Component', () => {
        let comp: ComorbiditieUpdateComponent;
        let fixture: ComponentFixture<ComorbiditieUpdateComponent>;
        let service: ComorbiditieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [ComorbiditieUpdateComponent]
            })
                .overrideTemplate(ComorbiditieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComorbiditieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComorbiditieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Comorbiditie(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.comorbiditie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Comorbiditie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.comorbiditie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
