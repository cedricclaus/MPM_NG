import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProcedureType } from './procedure-type.model';
import { ProcedureTypePopupService } from './procedure-type-popup.service';
import { ProcedureTypeService } from './procedure-type.service';

@Component({
    selector: 'jhi-procedure-type-dialog',
    templateUrl: './procedure-type-dialog.component.html'
})
export class ProcedureTypeDialogComponent implements OnInit {

    procedureType: ProcedureType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private procedureTypeService: ProcedureTypeService,
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
        if (this.procedureType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.procedureTypeService.update(this.procedureType));
        } else {
            this.subscribeToSaveResponse(
                this.procedureTypeService.create(this.procedureType));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProcedureType>) {
        result.subscribe((res: ProcedureType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProcedureType) {
        this.eventManager.broadcast({ name: 'procedureTypeListModification', content: 'OK'});
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
    selector: 'jhi-procedure-type-popup',
    template: ''
})
export class ProcedureTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procedureTypePopupService: ProcedureTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.procedureTypePopupService
                    .open(ProcedureTypeDialogComponent as Component, params['id']);
            } else {
                this.procedureTypePopupService
                    .open(ProcedureTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
