import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Usage } from './usage.model';
import { UsagePopupService } from './usage-popup.service';
import { UsageService } from './usage.service';

@Component({
    selector: 'jhi-usage-dialog',
    templateUrl: './usage-dialog.component.html'
})
export class UsageDialogComponent implements OnInit {

    usage: Usage;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private usageService: UsageService,
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
        if (this.usage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.usageService.update(this.usage));
        } else {
            this.subscribeToSaveResponse(
                this.usageService.create(this.usage));
        }
    }

    private subscribeToSaveResponse(result: Observable<Usage>) {
        result.subscribe((res: Usage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Usage) {
        this.eventManager.broadcast({ name: 'usageListModification', content: 'OK'});
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
    selector: 'jhi-usage-popup',
    template: ''
})
export class UsagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usagePopupService: UsagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.usagePopupService
                    .open(UsageDialogComponent as Component, params['id']);
            } else {
                this.usagePopupService
                    .open(UsageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
