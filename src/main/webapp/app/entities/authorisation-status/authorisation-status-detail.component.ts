import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorisationStatus } from './authorisation-status.model';
import { AuthorisationStatusService } from './authorisation-status.service';

@Component({
    selector: 'jhi-authorisation-status-detail',
    templateUrl: './authorisation-status-detail.component.html'
})
export class AuthorisationStatusDetailComponent implements OnInit, OnDestroy {

    authorisationStatus: AuthorisationStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private authorisationStatusService: AuthorisationStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuthorisationStatuses();
    }

    load(id) {
        this.authorisationStatusService.find(id).subscribe((authorisationStatus) => {
            this.authorisationStatus = authorisationStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuthorisationStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'authorisationStatusListModification',
            (response) => this.load(this.authorisationStatus.id)
        );
    }
}
