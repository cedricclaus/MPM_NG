/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UsageDetailComponent } from '../../../../../../main/webapp/app/entities/usage/usage-detail.component';
import { UsageService } from '../../../../../../main/webapp/app/entities/usage/usage.service';
import { Usage } from '../../../../../../main/webapp/app/entities/usage/usage.model';

describe('Component Tests', () => {

    describe('Usage Management Detail Component', () => {
        let comp: UsageDetailComponent;
        let fixture: ComponentFixture<UsageDetailComponent>;
        let service: UsageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [UsageDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UsageService,
                    JhiEventManager
                ]
            }).overrideTemplate(UsageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Usage('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.usage).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
