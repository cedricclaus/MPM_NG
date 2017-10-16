import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Authorisation } from './authorisation.model';
import { AuthorisationPopupService } from './authorisation-popup.service';
import { AuthorisationService } from './authorisation.service';

@Component({
    selector: 'jhi-authorisation-dialog',
    templateUrl: './authorisation-dialog.component.html'
})
export class AuthorisationDialogComponent implements OnInit {

    authorisation: Authorisation;
    isSaving: boolean;
    authorisationDateDp: any;
    radiationDateDp: any;
    suspensionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private authorisationService: AuthorisationService,
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
        if (this.authorisation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.authorisationService.update(this.authorisation));
        } else {
            this.subscribeToSaveResponse(
                this.authorisationService.create(this.authorisation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Authorisation>) {
        result.subscribe((res: Authorisation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Authorisation) {
        this.eventManager.broadcast({ name: 'authorisationListModification', content: 'OK'});
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
    selector: 'jhi-authorisation-popup',
    template: ''
})
export class AuthorisationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationPopupService: AuthorisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.authorisationPopupService
                    .open(AuthorisationDialogComponent as Component, params['id']);
            } else {
                this.authorisationPopupService
                    .open(AuthorisationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
