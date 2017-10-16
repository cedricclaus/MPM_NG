import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RouteOfAdministration } from './route-of-administration.model';
import { RouteOfAdministrationService } from './route-of-administration.service';

@Component({
    selector: 'jhi-route-of-administration-detail',
    templateUrl: './route-of-administration-detail.component.html'
})
export class RouteOfAdministrationDetailComponent implements OnInit, OnDestroy {

    routeOfAdministration: RouteOfAdministration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private routeOfAdministrationService: RouteOfAdministrationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRouteOfAdministrations();
    }

    load(id) {
        this.routeOfAdministrationService.find(id).subscribe((routeOfAdministration) => {
            this.routeOfAdministration = routeOfAdministration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRouteOfAdministrations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'routeOfAdministrationListModification',
            (response) => this.load(this.routeOfAdministration.id)
        );
    }
}
