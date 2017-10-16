/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MpmNgTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductGroupDetailComponent } from '../../../../../../main/webapp/app/entities/product-group/product-group-detail.component';
import { ProductGroupService } from '../../../../../../main/webapp/app/entities/product-group/product-group.service';
import { ProductGroup } from '../../../../../../main/webapp/app/entities/product-group/product-group.model';

describe('Component Tests', () => {

    describe('ProductGroup Management Detail Component', () => {
        let comp: ProductGroupDetailComponent;
        let fixture: ComponentFixture<ProductGroupDetailComponent>;
        let service: ProductGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MpmNgTestModule],
                declarations: [ProductGroupDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductGroupService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProductGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductGroup('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productGroup).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
