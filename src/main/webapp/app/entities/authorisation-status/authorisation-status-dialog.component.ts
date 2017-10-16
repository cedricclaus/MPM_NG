import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuthorisationStatus } from './authorisation-status.model';
import { AuthorisationStatusPopupService } from './authorisation-status-popup.service';
import { AuthorisationStatusService } from './authorisation-status.service';

@Component({
    selector: 'jhi-authorisation-status-dialog',
    templateUrl: './authorisation-status-dialog.component.html'
})
export class AuthorisationStatusDialogComponent implements OnInit {

    authorisationStatus: AuthorisationStatus;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private authorisationStatusService: AuthorisationStatusService,
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
        if (this.authorisationStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.authorisationStatusService.update(this.authorisationStatus));
        } else {
            this.subscribeToSaveResponse(
                this.authorisationStatusService.create(this.authorisationStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuthorisationStatus>) {
        result.subscribe((res: AuthorisationStatus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuthorisationStatus) {
        this.eventManager.broadcast({ name: 'authorisationStatusListModification', content: 'OK'});
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
    selector: 'jhi-authorisation-status-popup',
    template: ''
})
export class AuthorisationStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationStatusPopupService: AuthorisationStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.authorisationStatusPopupService
                    .open(AuthorisationStatusDialogComponent as Component, params['id']);
            } else {
                this.authorisationStatusPopupService
                    .open(AuthorisationStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
