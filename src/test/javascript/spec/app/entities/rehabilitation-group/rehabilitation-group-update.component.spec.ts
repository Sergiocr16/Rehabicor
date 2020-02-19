/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { RehabilitationGroupUpdateComponent } from 'app/entities/rehabilitation-group/rehabilitation-group-update.component';
import { RehabilitationGroupService } from 'app/entities/rehabilitation-group/rehabilitation-group.service';
import { RehabilitationGroup } from 'app/shared/model/rehabilitation-group.model';

describe('Component Tests', () => {
    describe('RehabilitationGroup Management Update Component', () => {
        let comp: RehabilitationGroupUpdateComponent;
        let fixture: ComponentFixture<RehabilitationGroupUpdateComponent>;
        let service: RehabilitationGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [RehabilitationGroupUpdateComponent]
            })
                .overrideTemplate(RehabilitationGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RehabilitationGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RehabilitationGroupService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RehabilitationGroup(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rehabilitationGroup = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RehabilitationGroup();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rehabilitationGroup = entity;
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
