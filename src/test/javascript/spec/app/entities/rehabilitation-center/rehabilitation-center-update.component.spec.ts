/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { RehabilitationCenterUpdateComponent } from 'app/entities/rehabilitation-center/rehabilitation-center-update.component';
import { RehabilitationCenterService } from 'app/entities/rehabilitation-center/rehabilitation-center.service';
import { RehabilitationCenter } from 'app/shared/model/rehabilitation-center.model';

describe('Component Tests', () => {
    describe('RehabilitationCenter Management Update Component', () => {
        let comp: RehabilitationCenterUpdateComponent;
        let fixture: ComponentFixture<RehabilitationCenterUpdateComponent>;
        let service: RehabilitationCenterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [RehabilitationCenterUpdateComponent]
            })
                .overrideTemplate(RehabilitationCenterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RehabilitationCenterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RehabilitationCenterService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RehabilitationCenter(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rehabilitationCenter = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RehabilitationCenter();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rehabilitationCenter = entity;
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
