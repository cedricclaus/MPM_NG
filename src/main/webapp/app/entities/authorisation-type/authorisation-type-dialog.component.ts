import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuthorisationType } from './authorisation-type.model';
import { AuthorisationTypePopupService } from './authorisation-type-popup.service';
import { AuthorisationTypeService } from './authorisation-type.service';

@Component({
    selector: 'jhi-authorisation-type-dialog',
    templateUrl: './authorisation-type-dialog.component.html'
})
export class AuthorisationTypeDialogComponent implements OnInit {

    authorisationType: AuthorisationType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private authorisationTypeService: AuthorisationTypeService,
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
        if (this.authorisationType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.authorisationTypeService.update(this.authorisationType));
        } else {
            this.subscribeToSaveResponse(
                this.authorisationTypeService.create(this.authorisationType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AuthorisationType>) {
        result.subscribe((res: AuthorisationType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AuthorisationType) {
        this.eventManager.broadcast({ name: 'authorisationTypeListModification', content: 'OK'});
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
    selector: 'jhi-authorisation-type-popup',
    template: ''
})
export class AuthorisationTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorisationTypePopupService: AuthorisationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.authorisationTypePopupService
                    .open(AuthorisationTypeDialogComponent as Component, params['id']);
            } else {
                this.authorisationTypePopupService
                    .open(AuthorisationTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
