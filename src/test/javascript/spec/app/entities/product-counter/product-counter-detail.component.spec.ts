/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductCounterDetailComponent } from '../../../../../../main/webapp/app/entities/product-counter/product-counter-detail.component';
import { ProductCounterService } from '../../../../../../main/webapp/app/entities/product-counter/product-counter.service';
import { ProductCounter } from '../../../../../../main/webapp/app/entities/product-counter/product-counter.model';

describe('Component Tests', () => {

    describe('ProductCounter Management Detail Component', () => {
        let comp: ProductCounterDetailComponent;
        let fixture: ComponentFixture<ProductCounterDetailComponent>;
        let service: ProductCounterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ProductCounterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductCounterService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProductCounterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductCounterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductCounterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductCounter('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productCounter).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
