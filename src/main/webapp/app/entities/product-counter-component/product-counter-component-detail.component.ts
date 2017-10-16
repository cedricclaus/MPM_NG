import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProductCounterComponent } from './product-counter-component.model';
import { ProductCounterComponentService } from './product-counter-component.service';

@Component({
    selector: 'jhi-product-counter-component-detail',
    templateUrl: './product-counter-component-detail.component.html'
})
export class ProductCounterComponentDetailComponent implements OnInit, OnDestroy {

    productCounterComponent: ProductCounterComponent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private productCounterComponentService: ProductCounterComponentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductCounterComponents();
    }

    load(id) {
        this.productCounterComponentService.find(id).subscribe((productCounterComponent) => {
            this.productCounterComponent = productCounterComponent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductCounterComponents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productCounterComponentListModification',
            (response) => this.load(this.productCounterComponent.id)
        );
    }
}
