/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExcipientDetailComponent } from '../../../../../../main/webapp/app/entities/excipient/excipient-detail.component';
import { ExcipientService } from '../../../../../../main/webapp/app/entities/excipient/excipient.service';
import { Excipient } from '../../../../../../main/webapp/app/entities/excipient/excipient.model';

describe('Component Tests', () => {

    describe('Excipient Management Detail Component', () => {
        let comp: ExcipientDetailComponent;
        let fixture: ComponentFixture<ExcipientDetailComponent>;
        let service: ExcipientService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ExcipientDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExcipientService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExcipientDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExcipientDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExcipientService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Excipient('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.excipient).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
