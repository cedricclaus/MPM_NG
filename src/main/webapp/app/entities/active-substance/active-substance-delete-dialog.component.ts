import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ActiveSubstance } from './active-substance.model';
import { ActiveSubstancePopupService } from './active-substance-popup.service';
import { ActiveSubstanceService } from './active-substance.service';

@Component({
    selector: 'jhi-active-substance-delete-dialog',
    templateUrl: './active-substance-delete-dialog.component.html'
})
export class ActiveSubstanceDeleteDialogComponent {

    activeSubstance: ActiveSubstance;

    constructor(
        private activeSubstanceService: ActiveSubstanceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.activeSubstanceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'activeSubstanceListModification',
                content: 'Deleted an activeSubstance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-active-substance-delete-popup',
    template: ''
})
export class ActiveSubstanceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activeSubstancePopupService: ActiveSubstancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.activeSubstancePopupService
                .open(ActiveSubstanceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
