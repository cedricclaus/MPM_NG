import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorisationStatus } from './authorisation-status.model';
import { AuthorisationStatusPopupService } from './authorisation-status-popup.service';
import { AuthorisationStatusService } from './authorisation-status.service';

@Component({
    selector: 'jhi-authorisation-status-delete-dialog',
    templateUrl: './authorisation-status-delete-dialog.component.html'
})
export class AuthorisationStatusDeleteDialogComponent {

    authorisationStatus: AuthorisationStatus;

    constructor(
        private authorisationStatusService: AuthorisationStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.authorisationStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'authorisationStatusListModification',
                content: 'Deleted an authorisationStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-authorisation-status-delete-popup',
    template: ''
})
export class AuthorisationStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationStatusPopupService: AuthorisationStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.authorisationStatusPopupService
                .open(AuthorisationStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
