/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { MayorEventsSessionUpdateComponent } from 'app/entities/mayor-events-session/mayor-events-session-update.component';
import { MayorEventsSessionService } from 'app/entities/mayor-events-session/mayor-events-session.service';
import { MayorEventsSession } from 'app/shared/model/mayor-events-session.model';

describe('Component Tests', () => {
    describe('MayorEventsSession Management Update Component', () => {
        let comp: MayorEventsSessionUpdateComponent;
        let fixture: ComponentFixture<MayorEventsSessionUpdateComponent>;
        let service: MayorEventsSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [MayorEventsSessionUpdateComponent]
            })
                .overrideTemplate(MayorEventsSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MayorEventsSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MayorEventsSessionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MayorEventsSession(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mayorEventsSession = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MayorEventsSession();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mayorEventsSession = entity;
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
