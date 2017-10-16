import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Excipient } from './excipient.model';
import { ExcipientPopupService } from './excipient-popup.service';
import { ExcipientService } from './excipient.service';

@Component({
    selector: 'jhi-excipient-dialog',
    templateUrl: './excipient-dialog.component.html'
})
export class ExcipientDialogComponent implements OnInit {

    excipient: Excipient;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private excipientService: ExcipientService,
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
        if (this.excipient.id !== undefined) {
            this.subscribeToSaveResponse(
                this.excipientService.update(this.excipient));
        } else {
            this.subscribeToSaveResponse(
                this.excipientService.create(this.excipient));
        }
    }

    private subscribeToSaveResponse(result: Observable<Excipient>) {
        result.subscribe((res: Excipient) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Excipient) {
        this.eventManager.broadcast({ name: 'excipientListModification', content: 'OK'});
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
    selector: 'jhi-excipient-popup',
    template: ''
})
export class ExcipientPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private excipientPopupService: ExcipientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.excipientPopupService
                    .open(ExcipientDialogComponent as Component, params['id']);
            } else {
                this.excipientPopupService
                    .open(ExcipientDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
