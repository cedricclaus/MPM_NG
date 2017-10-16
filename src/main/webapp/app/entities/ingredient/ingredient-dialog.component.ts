import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ingredient } from './ingredient.model';
import { IngredientPopupService } from './ingredient-popup.service';
import { IngredientService } from './ingredient.service';

@Component({
    selector: 'jhi-ingredient-dialog',
    templateUrl: './ingredient-dialog.component.html'
})
export class IngredientDialogComponent implements OnInit {

    ingredient: Ingredient;
    isSaving: boolean;
    creationDateUsageHumDp: any;
    creationDateUsageVetDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ingredientService: IngredientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ingredient.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ingredientService.update(this.ingredient));
        } else {
            this.subscribeToSaveResponse(
                this.ingredientService.create(this.ingredient));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ingredient>) {
        result.subscribe((res: Ingredient) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ingredient) {
        this.eventManager.broadcast({ name: 'ingredientListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-ingredient-popup',
    template: ''
})
export class IngredientPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ingredientPopupService: IngredientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ingredientPopupService
                    .open(IngredientDialogComponent as Component, params['id']);
            } else {
                this.ingredientPopupService
                    .open(IngredientDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
