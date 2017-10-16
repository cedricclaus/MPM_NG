import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Authorisation } from './authorisation.model';
import { AuthorisationService } from './authorisation.service';

@Component({
    selector: 'jhi-authorisation-detail',
    templateUrl: './authorisation-detail.component.html'
})
export class AuthorisationDetailComponent implements OnInit, OnDestroy {

    authorisation: Authorisation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private authorisationService: AuthorisationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuthorisations();
    }

    load(id) {
        this.authorisationService.find(id).subscribe((authorisation) => {
            this.authorisation = authorisation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuthorisations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'authorisationListModification',
            (response) => this.load(this.authorisation.id)
        );
    }
}
