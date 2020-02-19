/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MinorEventsSessionUpdateComponent } from 'app/entities/minor-events-session/minor-events-session-update.component';
import { MinorEventsSessionService } from 'app/entities/minor-events-session/minor-events-session.service';
import { MinorEventsSession } from 'app/shared/model/minor-events-session.model';

describe('Component Tests', () => {
    describe('MinorEventsSession Management Update Component', () => {
        let comp: MinorEventsSessionUpdateComponent;
        let fixture: ComponentFixture<MinorEventsSessionUpdateComponent>;
        let service: MinorEventsSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MinorEventsSessionUpdateComponent]
            })
                .overrideTemplate(MinorEventsSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MinorEventsSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MinorEventsSessionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinorEventsSession(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.minorEventsSession = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MinorEventsSession();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.minorEventsSession = entity;
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
