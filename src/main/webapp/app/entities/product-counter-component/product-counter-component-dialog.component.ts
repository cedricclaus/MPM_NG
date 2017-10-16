import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductCounterComponent } from './product-counter-component.model';
import { ProductCounterComponentPopupService } from './product-counter-component-popup.service';
import { ProductCounterComponentService } from './product-counter-component.service';

@Component({
    selector: 'jhi-product-counter-component-dialog',
    templateUrl: './product-counter-component-dialog.component.html'
})
export class ProductCounterComponentDialogComponent implements OnInit {

    productCounterComponent: ProductCounterComponent;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private productCounterComponentService: ProductCounterComponentService,
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
        if (this.productCounterComponent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productCounterComponentService.update(this.productCounterComponent));
        } else {
            this.subscribeToSaveResponse(
                this.productCounterComponentService.create(this.productCounterComponent));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductCounterComponent>) {
        result.subscribe((res: ProductCounterComponent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProductCounterComponent) {
        this.eventManager.broadcast({ name: 'productCounterComponentListModification', content: 'OK'});
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
    selector: 'jhi-product-counter-component-popup',
    template: ''
})
export class ProductCounterComponentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCounterComponentPopupService: ProductCounterComponentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productCounterComponentPopupService
                    .open(ProductCounterComponentDialogComponent as Component, params['id']);
            } else {
                this.productCounterComponentPopupService
                    .open(ProductCounterComponentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
