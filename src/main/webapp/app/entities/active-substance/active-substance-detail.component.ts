import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ActiveSubstance } from './active-substance.model';
import { ActiveSubstanceService } from './active-substance.service';

@Component({
    selector: 'jhi-active-substance-detail',
    templateUrl: './active-substance-detail.component.html'
})
export class ActiveSubstanceDetailComponent implements OnInit, OnDestroy {

    activeSubstance: ActiveSubstance;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private activeSubstanceService: ActiveSubstanceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActiveSubstances();
    }

    load(id) {
        this.activeSubstanceService.find(id).subscribe((activeSubstance) => {
            this.activeSubstance = activeSubstance;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActiveSubstances() {
        this.eventSubscriber = this.eventManager.subscribe(
            'activeSubstanceListModification',
            (response) => this.load(this.activeSubstance.id)
        );
    }
}
