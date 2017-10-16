import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductCounterComponent } from './product-counter-component.model';
import { ProductCounterComponentPopupService } from './product-counter-component-popup.service';
import { ProductCounterComponentService } from './product-counter-component.service';

@Component({
    selector: 'jhi-product-counter-component-delete-dialog',
    templateUrl: './product-counter-component-delete-dialog.component.html'
})
export class ProductCounterComponentDeleteDialogComponent {

    productCounterComponent: ProductCounterComponent;

    constructor(
        private productCounterComponentService: ProductCounterComponentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.productCounterComponentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productCounterComponentListModification',
                content: 'Deleted an productCounterComponent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-counter-component-delete-popup',
    template: ''
})
export class ProductCounterComponentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCounterComponentPopupService: ProductCounterComponentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productCounterComponentPopupService
                .open(ProductCounterComponentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
