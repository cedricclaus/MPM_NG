import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RouteOfAdministration } from './route-of-administration.model';
import { RouteOfAdministrationPopupService } from './route-of-administration-popup.service';
import { RouteOfAdministrationService } from './route-of-administration.service';

@Component({
    selector: 'jhi-route-of-administration-dialog',
    templateUrl: './route-of-administration-dialog.component.html'
})
export class RouteOfAdministrationDialogComponent implements OnInit {

    routeOfAdministration: RouteOfAdministration;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private routeOfAdministrationService: RouteOfAdministrationService,
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
        if (this.routeOfAdministration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.routeOfAdministrationService.update(this.routeOfAdministration));
        } else {
            this.subscribeToSaveResponse(
                this.routeOfAdministrationService.create(this.routeOfAdministration));
        }
    }

    private subscribeToSaveResponse(result: Observable<RouteOfAdministration>) {
        result.subscribe((res: RouteOfAdministration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RouteOfAdministration) {
        this.eventManager.broadcast({ name: 'routeOfAdministrationListModification', content: 'OK'});
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
    selector: 'jhi-route-of-administration-popup',
    template: ''
})
export class RouteOfAdministrationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routeOfAdministrationPopupService: RouteOfAdministrationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.routeOfAdministrationPopupService
                    .open(RouteOfAdministrationDialogComponent as Component, params['id']);
            } else {
                this.routeOfAdministrationPopupService
                    .open(RouteOfAdministrationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
