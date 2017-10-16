import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PharmaceuticalForm } from './pharmaceutical-form.model';
import { PharmaceuticalFormPopupService } from './pharmaceutical-form-popup.service';
import { PharmaceuticalFormService } from './pharmaceutical-form.service';

@Component({
    selector: 'jhi-pharmaceutical-form-dialog',
    templateUrl: './pharmaceutical-form-dialog.component.html'
})
export class PharmaceuticalFormDialogComponent implements OnInit {

    pharmaceuticalForm: PharmaceuticalForm;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pharmaceuticalFormService: PharmaceuticalFormService,
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
        if (this.pharmaceuticalForm.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pharmaceuticalFormService.update(this.pharmaceuticalForm));
        } else {
            this.subscribeToSaveResponse(
                this.pharmaceuticalFormService.create(this.pharmaceuticalForm));
        }
    }

    private subscribeToSaveResponse(result: Observable<PharmaceuticalForm>) {
        result.subscribe((res: PharmaceuticalForm) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PharmaceuticalForm) {
        this.eventManager.broadcast({ name: 'pharmaceuticalFormListModification', content: 'OK'});
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
    selector: 'jhi-pharmaceutical-form-popup',
    template: ''
})
export class PharmaceuticalFormPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pharmaceuticalFormPopupService: PharmaceuticalFormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pharmaceuticalFormPopupService
                    .open(PharmaceuticalFormDialogComponent as Component, params['id']);
            } else {
                this.pharmaceuticalFormPopupService
                    .open(PharmaceuticalFormDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
