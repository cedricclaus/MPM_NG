import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PharmaceuticalForm } from './pharmaceutical-form.model';
import { PharmaceuticalFormPopupService } from './pharmaceutical-form-popup.service';
import { PharmaceuticalFormService } from './pharmaceutical-form.service';

@Component({
    selector: 'jhi-pharmaceutical-form-delete-dialog',
    templateUrl: './pharmaceutical-form-delete-dialog.component.html'
})
export class PharmaceuticalFormDeleteDialogComponent {

    pharmaceuticalForm: PharmaceuticalForm;

    constructor(
        private pharmaceuticalFormService: PharmaceuticalFormService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.pharmaceuticalFormService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pharmaceuticalFormListModification',
                content: 'Deleted an pharmaceuticalForm'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pharmaceutical-form-delete-popup',
    template: ''
})
export class PharmaceuticalFormDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pharmaceuticalFormPopupService: PharmaceuticalFormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pharmaceuticalFormPopupService
                .open(PharmaceuticalFormDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
