import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RouteOfAdministration } from './route-of-administration.model';
import { RouteOfAdministrationPopupService } from './route-of-administration-popup.service';
import { RouteOfAdministrationService } from './route-of-administration.service';

@Component({
    selector: 'jhi-route-of-administration-delete-dialog',
    templateUrl: './route-of-administration-delete-dialog.component.html'
})
export class RouteOfAdministrationDeleteDialogComponent {

    routeOfAdministration: RouteOfAdministration;

    constructor(
        private routeOfAdministrationService: RouteOfAdministrationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.routeOfAdministrationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'routeOfAdministrationListModification',
                content: 'Deleted an routeOfAdministration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-route-of-administration-delete-popup',
    template: ''
})
export class RouteOfAdministrationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routeOfAdministrationPopupService: RouteOfAdministrationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.routeOfAdministrationPopupService
                .open(RouteOfAdministrationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
