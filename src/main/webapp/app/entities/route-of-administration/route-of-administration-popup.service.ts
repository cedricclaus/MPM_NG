import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RouteOfAdministration } from './route-of-administration.model';
import { RouteOfAdministrationService } from './route-of-administration.service';

@Injectable()
export class RouteOfAdministrationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private routeOfAdministrationService: RouteOfAdministrationService

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
                this.routeOfAdministrationService.find(id).subscribe((routeOfAdministration) => {
                    this.ngbModalRef = this.routeOfAdministrationModalRef(component, routeOfAdministration);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.routeOfAdministrationModalRef(component, new RouteOfAdministration());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    routeOfAdministrationModalRef(component: Component, routeOfAdministration: RouteOfAdministration): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.routeOfAdministration = routeOfAdministration;
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
