import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Authorisation } from './authorisation.model';
import { AuthorisationService } from './authorisation.service';

@Injectable()
export class AuthorisationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private authorisationService: AuthorisationService

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
                this.authorisationService.find(id).subscribe((authorisation) => {
                    if (authorisation.authorisationDate) {
                        authorisation.authorisationDate = {
                            year: authorisation.authorisationDate.getFullYear(),
                            month: authorisation.authorisationDate.getMonth() + 1,
                            day: authorisation.authorisationDate.getDate()
                        };
                    }
                    if (authorisation.radiationDate) {
                        authorisation.radiationDate = {
                            year: authorisation.radiationDate.getFullYear(),
                            month: authorisation.radiationDate.getMonth() + 1,
                            day: authorisation.radiationDate.getDate()
                        };
                    }
                    if (authorisation.suspensionDate) {
                        authorisation.suspensionDate = {
                            year: authorisation.suspensionDate.getFullYear(),
                            month: authorisation.suspensionDate.getMonth() + 1,
                            day: authorisation.suspensionDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.authorisationModalRef(component, authorisation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.authorisationModalRef(component, new Authorisation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    authorisationModalRef(component: Component, authorisation: Authorisation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.authorisation = authorisation;
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
