/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProcedureTypeDetailComponent } from '../../../../../../main/webapp/app/entities/procedure-type/procedure-type-detail.component';
import { ProcedureTypeService } from '../../../../../../main/webapp/app/entities/procedure-type/procedure-type.service';
import { ProcedureType } from '../../../../../../main/webapp/app/entities/procedure-type/procedure-type.model';

describe('Component Tests', () => {

    describe('ProcedureType Management Detail Component', () => {
        let comp: ProcedureTypeDetailComponent;
        let fixture: ComponentFixture<ProcedureTypeDetailComponent>;
        let service: ProcedureTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ProcedureTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProcedureTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProcedureTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProcedureType('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.procedureType).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
