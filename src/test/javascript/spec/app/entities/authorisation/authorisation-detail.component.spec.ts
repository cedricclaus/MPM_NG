/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuthorisationDetailComponent } from '../../../../../../main/webapp/app/entities/authorisation/authorisation-detail.component';
import { AuthorisationService } from '../../../../../../main/webapp/app/entities/authorisation/authorisation.service';
import { Authorisation } from '../../../../../../main/webapp/app/entities/authorisation/authorisation.model';

describe('Component Tests', () => {

    describe('Authorisation Management Detail Component', () => {
        let comp: AuthorisationDetailComponent;
        let fixture: ComponentFixture<AuthorisationDetailComponent>;
        let service: AuthorisationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [AuthorisationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuthorisationService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuthorisationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuthorisationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthorisationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Authorisation('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.authorisation).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
