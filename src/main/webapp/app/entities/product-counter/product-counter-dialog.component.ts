import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductCounter } from './product-counter.model';
import { ProductCounterPopupService } from './product-counter-popup.service';
import { ProductCounterService } from './product-counter.service';

@Component({
    selector: 'jhi-product-counter-dialog',
    templateUrl: './product-counter-dialog.component.html'
})
export class ProductCounterDialogComponent implements OnInit {

    productCounter: ProductCounter;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private productCounterService: ProductCounterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productCounter.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productCounterService.update(this.productCounter));
        } else {
            this.subscribeToSaveResponse(
                this.productCounterService.create(this.productCounter));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductCounter>) {
        result.subscribe((res: ProductCounter) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProductCounter) {
        this.eventManager.broadcast({ name: 'productCounterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-product-counter-popup',
    template: ''
})
export class ProductCounterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCounterPopupService: ProductCounterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productCounterPopupService
                    .open(ProductCounterDialogComponent as Component, params['id']);
            } else {
                this.productCounterPopupService
                    .open(ProductCounterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
