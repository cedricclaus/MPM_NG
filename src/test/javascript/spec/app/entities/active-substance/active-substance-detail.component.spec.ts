/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActiveSubstanceDetailComponent } from '../../../../../../main/webapp/app/entities/active-substance/active-substance-detail.component';
import { ActiveSubstanceService } from '../../../../../../main/webapp/app/entities/active-substance/active-substance.service';
import { ActiveSubstance } from '../../../../../../main/webapp/app/entities/active-substance/active-substance.model';

describe('Component Tests', () => {

    describe('ActiveSubstance Management Detail Component', () => {
        let comp: ActiveSubstanceDetailComponent;
        let fixture: ComponentFixture<ActiveSubstanceDetailComponent>;
        let service: ActiveSubstanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ActiveSubstanceDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActiveSubstanceService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActiveSubstanceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActiveSubstanceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActiveSubstanceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ActiveSubstance('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.activeSubstance).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
