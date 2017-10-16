import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PharmaceuticalForm } from './pharmaceutical-form.model';
import { PharmaceuticalFormService } from './pharmaceutical-form.service';

@Injectable()
export class PharmaceuticalFormPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pharmaceuticalFormService: PharmaceuticalFormService

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
                this.pharmaceuticalFormService.find(id).subscribe((pharmaceuticalForm) => {
                    this.ngbModalRef = this.pharmaceuticalFormModalRef(component, pharmaceuticalForm);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pharmaceuticalFormModalRef(component, new PharmaceuticalForm());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pharmaceuticalFormModalRef(component: Component, pharmaceuticalForm: PharmaceuticalForm): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pharmaceuticalForm = pharmaceuticalForm;
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
