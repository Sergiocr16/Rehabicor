/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MinorEventUpdateComponent } from 'app/entities/minor-event/minor-event-update.component';
import { MinorEventService } from 'app/entities/minor-event/minor-event.service';
import { MinorEvent } from 'app/shared/model/minor-event.model';

describe('Component Tests', () => {
    describe('MinorEvent Management Update Component', () => {
        let comp: MinorEventUpdateComponent;
        let fixture: ComponentFixture<MinorEventUpdateComponent>;
        let service: MinorEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MinorEventUpdateComponent]
            })
                .overrideTemplate(MinorEventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MinorEventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MinorEventService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinorEvent(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.minorEvent = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinorEvent();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.minorEvent = entity;
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
