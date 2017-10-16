import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorisationType } from './authorisation-type.model';
import { AuthorisationTypeService } from './authorisation-type.service';

@Component({
    selector: 'jhi-authorisation-type-detail',
    templateUrl: './authorisation-type-detail.component.html'
})
export class AuthorisationTypeDetailComponent implements OnInit, OnDestroy {

    authorisationType: AuthorisationType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private authorisationTypeService: AuthorisationTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuthorisationTypes();
    }

    load(id) {
        this.authorisationTypeService.find(id).subscribe((authorisationType) => {
            this.authorisationType = authorisationType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuthorisationTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'authorisationTypeListModification',
            (response) => this.load(this.authorisationType.id)
        );
    }
}
