import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Authorisation } from './authorisation.model';
import { AuthorisationPopupService } from './authorisation-popup.service';
import { AuthorisationService } from './authorisation.service';

@Component({
    selector: 'jhi-authorisation-delete-dialog',
    templateUrl: './authorisation-delete-dialog.component.html'
})
export class AuthorisationDeleteDialogComponent {

    authorisation: Authorisation;

    constructor(
        private authorisationService: AuthorisationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.authorisationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'authorisationListModification',
                content: 'Deleted an authorisation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-authorisation-delete-popup',
    template: ''
})
export class AuthorisationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationPopupService: AuthorisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.authorisationPopupService
                .open(AuthorisationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
