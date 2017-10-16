import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Usage } from './usage.model';
import { UsageService } from './usage.service';

@Component({
    selector: 'jhi-usage-detail',
    templateUrl: './usage-detail.component.html'
})
export class UsageDetailComponent implements OnInit, OnDestroy {

    usage: Usage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private usageService: UsageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUsages();
    }

    load(id) {
        this.usageService.find(id).subscribe((usage) => {
            this.usage = usage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUsages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'usageListModification',
            (response) => this.load(this.usage.id)
        );
    }
}
