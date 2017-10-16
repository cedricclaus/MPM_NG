import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProcedureType } from './procedure-type.model';
import { ProcedureTypePopupService } from './procedure-type-popup.service';
import { ProcedureTypeService } from './procedure-type.service';

@Component({
    selector: 'jhi-procedure-type-delete-dialog',
    templateUrl: './procedure-type-delete-dialog.component.html'
})
export class ProcedureTypeDeleteDialogComponent {

    procedureType: ProcedureType;

    constructor(
        private procedureTypeService: ProcedureTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.procedureTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'procedureTypeListModification',
                content: 'Deleted an procedureType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-procedure-type-delete-popup',
    template: ''
})
export class ProcedureTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procedureTypePopupService: ProcedureTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.procedureTypePopupService
                .open(ProcedureTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
