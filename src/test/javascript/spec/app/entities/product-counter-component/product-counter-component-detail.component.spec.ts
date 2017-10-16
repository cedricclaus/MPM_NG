/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductCounterComponentDetailComponent } from '../../../../../../main/webapp/app/entities/product-counter-component/product-counter-component-detail.component';
import { ProductCounterComponentService } from '../../../../../../main/webapp/app/entities/product-counter-component/product-counter-component.service';
import { ProductCounterComponent } from '../../../../../../main/webapp/app/entities/product-counter-component/product-counter-component.model';

describe('Component Tests', () => {

    describe('ProductCounterComponent Management Detail Component', () => {
        let comp: ProductCounterComponentDetailComponent;
        let fixture: ComponentFixture<ProductCounterComponentDetailComponent>;
        let service: ProductCounterComponentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ProductCounterComponentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductCounterComponentService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProductCounterComponentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductCounterComponentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductCounterComponentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductCounterComponent('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productCounterComponent).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
