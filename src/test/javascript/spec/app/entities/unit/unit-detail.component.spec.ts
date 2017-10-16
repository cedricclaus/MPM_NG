/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UnitDetailComponent } from '../../../../../../main/webapp/app/entities/unit/unit-detail.component';
import { UnitService } from '../../../../../../main/webapp/app/entities/unit/unit.service';
import { Unit } from '../../../../../../main/webapp/app/entities/unit/unit.model';

describe('Component Tests', () => {

    describe('Unit Management Detail Component', () => {
        let comp: UnitDetailComponent;
        let fixture: ComponentFixture<UnitDetailComponent>;
        let service: UnitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [UnitDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UnitService,
                    JhiEventManager
                ]
            }).overrideTemplate(UnitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UnitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Unit('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.unit).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
