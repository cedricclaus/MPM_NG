import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProductCounter } from './product-counter.model';
import { ProductCounterService } from './product-counter.service';

@Component({
    selector: 'jhi-product-counter-detail',
    templateUrl: './product-counter-detail.component.html'
})
export class ProductCounterDetailComponent implements OnInit, OnDestroy {

    productCounter: ProductCounter;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private productCounterService: ProductCounterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductCounters();
    }

    load(id) {
        this.productCounterService.find(id).subscribe((productCounter) => {
            this.productCounter = productCounter;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductCounters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productCounterListModification',
            (response) => this.load(this.productCounter.id)
        );
    }
}
