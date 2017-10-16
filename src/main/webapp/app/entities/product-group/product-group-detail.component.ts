import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProductGroup } from './product-group.model';
import { ProductGroupService } from './product-group.service';

@Component({
    selector: 'jhi-product-group-detail',
    templateUrl: './product-group-detail.component.html'
})
export class ProductGroupDetailComponent implements OnInit, OnDestroy {

    productGroup: ProductGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private productGroupService: ProductGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductGroups();
    }

    load(id) {
        this.productGroupService.find(id).subscribe((productGroup) => {
            this.productGroup = productGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productGroupListModification',
            (response) => this.load(this.productGroup.id)
        );
    }
}
