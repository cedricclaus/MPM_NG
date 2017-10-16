/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PharmaceuticalFormDetailComponent } from '../../../../../../main/webapp/app/entities/pharmaceutical-form/pharmaceutical-form-detail.component';
import { PharmaceuticalFormService } from '../../../../../../main/webapp/app/entities/pharmaceutical-form/pharmaceutical-form.service';
import { PharmaceuticalForm } from '../../../../../../main/webapp/app/entities/pharmaceutical-form/pharmaceutical-form.model';

describe('Component Tests', () => {

    describe('PharmaceuticalForm Management Detail Component', () => {
        let comp: PharmaceuticalFormDetailComponent;
        let fixture: ComponentFixture<PharmaceuticalFormDetailComponent>;
        let service: PharmaceuticalFormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [PharmaceuticalFormDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PharmaceuticalFormService,
                    JhiEventManager
                ]
            }).overrideTemplate(PharmaceuticalFormDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PharmaceuticalFormDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PharmaceuticalFormService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PharmaceuticalForm('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pharmaceuticalForm).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
