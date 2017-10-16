import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ActiveSubstance } from './active-substance.model';
import { ActiveSubstancePopupService } from './active-substance-popup.service';
import { ActiveSubstanceService } from './active-substance.service';

@Component({
    selector: 'jhi-active-substance-dialog',
    templateUrl: './active-substance-dialog.component.html'
})
export class ActiveSubstanceDialogComponent implements OnInit {

    activeSubstance: ActiveSubstance;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private activeSubstanceService: ActiveSubstanceService,
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
        if (this.activeSubstance.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activeSubstanceService.update(this.activeSubstance));
        } else {
            this.subscribeToSaveResponse(
                this.activeSubstanceService.create(this.activeSubstance));
        }
    }

    private subscribeToSaveResponse(result: Observable<ActiveSubstance>) {
        result.subscribe((res: ActiveSubstance) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ActiveSubstance) {
        this.eventManager.broadcast({ name: 'activeSubstanceListModification', content: 'OK'});
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
    selector: 'jhi-active-substance-popup',
    template: ''
})
export class ActiveSubstancePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activeSubstancePopupService: ActiveSubstancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activeSubstancePopupService
                    .open(ActiveSubstanceDialogComponent as Component, params['id']);
            } else {
                this.activeSubstancePopupService
                    .open(ActiveSubstanceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
