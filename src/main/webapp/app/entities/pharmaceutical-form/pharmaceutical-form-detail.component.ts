import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PharmaceuticalForm } from './pharmaceutical-form.model';
import { PharmaceuticalFormService } from './pharmaceutical-form.service';

@Component({
    selector: 'jhi-pharmaceutical-form-detail',
    templateUrl: './pharmaceutical-form-detail.component.html'
})
export class PharmaceuticalFormDetailComponent implements OnInit, OnDestroy {

    pharmaceuticalForm: PharmaceuticalForm;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pharmaceuticalFormService: PharmaceuticalFormService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPharmaceuticalForms();
    }

    load(id) {
        this.pharmaceuticalFormService.find(id).subscribe((pharmaceuticalForm) => {
            this.pharmaceuticalForm = pharmaceuticalForm;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPharmaceuticalForms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pharmaceuticalFormListModification',
            (response) => this.load(this.pharmaceuticalForm.id)
        );
    }
}
