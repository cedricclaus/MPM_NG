import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorisationType } from './authorisation-type.model';
import { AuthorisationTypePopupService } from './authorisation-type-popup.service';
import { AuthorisationTypeService } from './authorisation-type.service';

@Component({
    selector: 'jhi-authorisation-type-delete-dialog',
    templateUrl: './authorisation-type-delete-dialog.component.html'
})
export class AuthorisationTypeDeleteDialogComponent {

    authorisationType: AuthorisationType;

    constructor(
        private authorisationTypeService: AuthorisationTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.authorisationTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'authorisationTypeListModification',
                content: 'Deleted an authorisationType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-authorisation-type-delete-popup',
    template: ''
})
export class AuthorisationTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationTypePopupService: AuthorisationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.authorisationTypePopupService
                .open(AuthorisationTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
