/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MayorEventUpdateComponent } from 'app/entities/mayor-event/mayor-event-update.component';
import { MayorEventService } from 'app/entities/mayor-event/mayor-event.service';
import { MayorEvent } from 'app/shared/model/mayor-event.model';

describe('Component Tests', () => {
    describe('MayorEvent Management Update Component', () => {
        let comp: MayorEventUpdateComponent;
        let fixture: ComponentFixture<MayorEventUpdateComponent>;
        let service: MayorEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MayorEventUpdateComponent]
            })
                .overrideTemplate(MayorEventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MayorEventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MayorEventService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MayorEvent(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mayorEvent = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MayorEvent();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mayorEvent = entity;
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
