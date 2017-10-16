/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RouteOfAdministrationDetailComponent } from '../../../../../../main/webapp/app/entities/route-of-administration/route-of-administration-detail.component';
import { RouteOfAdministrationService } from '../../../../../../main/webapp/app/entities/route-of-administration/route-of-administration.service';
import { RouteOfAdministration } from '../../../../../../main/webapp/app/entities/route-of-administration/route-of-administration.model';

describe('Component Tests', () => {

    describe('RouteOfAdministration Management Detail Component', () => {
        let comp: RouteOfAdministrationDetailComponent;
        let fixture: ComponentFixture<RouteOfAdministrationDetailComponent>;
        let service: RouteOfAdministrationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [RouteOfAdministrationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RouteOfAdministrationService,
                    JhiEventManager
                ]
            }).overrideTemplate(RouteOfAdministrationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RouteOfAdministrationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RouteOfAdministrationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RouteOfAdministration('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.routeOfAdministration).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
