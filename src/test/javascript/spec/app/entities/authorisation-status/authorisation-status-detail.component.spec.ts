/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuthorisationStatusDetailComponent } from '../../../../../../main/webapp/app/entities/authorisation-status/authorisation-status-detail.component';
import { AuthorisationStatusService } from '../../../../../../main/webapp/app/entities/authorisation-status/authorisation-status.service';
import { AuthorisationStatus } from '../../../../../../main/webapp/app/entities/authorisation-status/authorisation-status.model';

describe('Component Tests', () => {

    describe('AuthorisationStatus Management Detail Component', () => {
        let comp: AuthorisationStatusDetailComponent;
        let fixture: ComponentFixture<AuthorisationStatusDetailComponent>;
        let service: AuthorisationStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [AuthorisationStatusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuthorisationStatusService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuthorisationStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuthorisationStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthorisationStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuthorisationStatus('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.authorisationStatus).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
