import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProcedureType } from './procedure-type.model';
import { ProcedureTypeService } from './procedure-type.service';

@Component({
    selector: 'jhi-procedure-type-detail',
    templateUrl: './procedure-type-detail.component.html'
})
export class ProcedureTypeDetailComponent implements OnInit, OnDestroy {

    procedureType: ProcedureType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private procedureTypeService: ProcedureTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcedureTypes();
    }

    load(id) {
        this.procedureTypeService.find(id).subscribe((procedureType) => {
            this.procedureType = procedureType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcedureTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'procedureTypeListModification',
            (response) => this.load(this.procedureType.id)
        );
    }
}
