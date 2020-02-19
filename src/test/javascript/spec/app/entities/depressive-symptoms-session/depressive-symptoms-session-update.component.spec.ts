/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { DepressiveSymptomsSessionUpdateComponent } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session-update.component';
import { DepressiveSymptomsSessionService } from 'app/entities/depressive-symptoms-session/depressive-symptoms-session.service';
import { DepressiveSymptomsSession } from 'app/shared/model/depressive-symptoms-session.model';

describe('Component Tests', () => {
    describe('DepressiveSymptomsSession Management Update Component', () => {
        let comp: DepressiveSymptomsSessionUpdateComponent;
        let fixture: ComponentFixture<DepressiveSymptomsSessionUpdateComponent>;
        let service: DepressiveSymptomsSessionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [DepressiveSymptomsSessionUpdateComponent]
            })
                .overrideTemplate(DepressiveSymptomsSessionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepressiveSymptomsSessionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepressiveSymptomsSessionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepressiveSymptomsSession(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depressiveSymptomsSession = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepressiveSymptomsSession();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depressiveSymptomsSession = entity;
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
