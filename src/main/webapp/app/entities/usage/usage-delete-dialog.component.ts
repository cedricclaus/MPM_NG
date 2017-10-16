import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Usage } from './usage.model';
import { UsagePopupService } from './usage-popup.service';
import { UsageService } from './usage.service';

@Component({
    selector: 'jhi-usage-delete-dialog',
    templateUrl: './usage-delete-dialog.component.html'
})
export class UsageDeleteDialogComponent {

    usage: Usage;

    constructor(
        private usageService: UsageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.usageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'usageListModification',
                content: 'Deleted an usage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-usage-delete-popup',
    template: ''
})
export class UsageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usagePopupService: UsagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.usagePopupService
                .open(UsageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
