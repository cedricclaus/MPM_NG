/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuthorisationTypeDetailComponent } from '../../../../../../main/webapp/app/entities/authorisation-type/authorisation-type-detail.component';
import { AuthorisationTypeService } from '../../../../../../main/webapp/app/entities/authorisation-type/authorisation-type.service';
import { AuthorisationType } from '../../../../../../main/webapp/app/entities/authorisation-type/authorisation-type.model';

describe('Component Tests', () => {

    describe('AuthorisationType Management Detail Component', () => {
        let comp: AuthorisationTypeDetailComponent;
        let fixture: ComponentFixture<AuthorisationTypeDetailComponent>;
        let service: AuthorisationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [AuthorisationTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuthorisationTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(AuthorisationTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuthorisationTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthorisationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuthorisationType('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.authorisationType).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
