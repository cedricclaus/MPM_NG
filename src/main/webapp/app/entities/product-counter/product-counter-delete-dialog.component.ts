import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductCounter } from './product-counter.model';
import { ProductCounterPopupService } from './product-counter-popup.service';
import { ProductCounterService } from './product-counter.service';

@Component({
    selector: 'jhi-product-counter-delete-dialog',
    templateUrl: './product-counter-delete-dialog.component.html'
})
export class ProductCounterDeleteDialogComponent {

    productCounter: ProductCounter;

    constructor(
        private productCounterService: ProductCounterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.productCounterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productCounterListModification',
                content: 'Deleted an productCounter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-counter-delete-popup',
    template: ''
})
export class ProductCounterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCounterPopupService: ProductCounterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productCounterPopupService
                .open(ProductCounterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
