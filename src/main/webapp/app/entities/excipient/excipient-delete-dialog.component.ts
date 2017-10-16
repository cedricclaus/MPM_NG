import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Excipient } from './excipient.model';
import { ExcipientPopupService } from './excipient-popup.service';
import { ExcipientService } from './excipient.service';

@Component({
    selector: 'jhi-excipient-delete-dialog',
    templateUrl: './excipient-delete-dialog.component.html'
})
export class ExcipientDeleteDialogComponent {

    excipient: Excipient;

    constructor(
        private excipientService: ExcipientService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.excipientService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'excipientListModification',
                content: 'Deleted an excipient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-excipient-delete-popup',
    template: ''
})
export class ExcipientDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private excipientPopupService: ExcipientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.excipientPopupService
                .open(ExcipientDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
