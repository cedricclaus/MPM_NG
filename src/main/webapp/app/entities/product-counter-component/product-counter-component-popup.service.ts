import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductCounterComponent } from './product-counter-component.model';
import { ProductCounterComponentService } from './product-counter-component.service';

@Injectable()
export class ProductCounterComponentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productCounterComponentService: ProductCounterComponentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.productCounterComponentService.find(id).subscribe((productCounterComponent) => {
                    this.ngbModalRef = this.productCounterComponentModalRef(component, productCounterComponent);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.productCounterComponentModalRef(component, new ProductCounterComponent());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    productCounterComponentModalRef(component: Component, productCounterComponent: ProductCounterComponent): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productCounterComponent = productCounterComponent;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
