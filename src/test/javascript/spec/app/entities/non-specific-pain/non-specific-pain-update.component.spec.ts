/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RehabicorTestModule } from '../../../test.module';
import { NonSpecificPainUpdateComponent } from 'app/entities/non-specific-pain/non-specific-pain-update.component';
import { NonSpecificPainService } from 'app/entities/non-specific-pain/non-specific-pain.service';
import { NonSpecificPain } from 'app/shared/model/non-specific-pain.model';

describe('Component Tests', () => {
    describe('NonSpecificPain Management Update Component', () => {
        let comp: NonSpecificPainUpdateComponent;
        let fixture: ComponentFixture<NonSpecificPainUpdateComponent>;
        let service: NonSpecificPainService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RehabicorTestModule],
                declarations: [NonSpecificPainUpdateComponent]
            })
                .overrideTemplate(NonSpecificPainUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NonSpecificPainUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NonSpecificPainService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new NonSpecificPain(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nonSpecificPain = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new NonSpecificPain();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nonSpecificPain = entity;
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
