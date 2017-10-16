import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Excipient } from './excipient.model';
import { ExcipientService } from './excipient.service';

@Component({
    selector: 'jhi-excipient-detail',
    templateUrl: './excipient-detail.component.html'
})
export class ExcipientDetailComponent implements OnInit, OnDestroy {

    excipient: Excipient;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private excipientService: ExcipientService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExcipients();
    }

    load(id) {
        this.excipientService.find(id).subscribe((excipient) => {
            this.excipient = excipient;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExcipients() {
        this.eventSubscriber = this.eventManager.subscribe(
            'excipientListModification',
            (response) => this.load(this.excipient.id)
        );
    }
}
