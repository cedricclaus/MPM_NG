import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ingredient } from './ingredient.model';
import { IngredientService } from './ingredient.service';

@Injectable()
export class IngredientPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ingredientService: IngredientService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.ingredientService.find(id).subscribe((ingredient) => {
                    if (ingredient.creationDateUsageHum) {
                        ingredient.creationDateUsageHum = {
                            year: ingredient.creationDateUsageHum.getFullYear(),
                            month: ingredient.creationDateUsageHum.getMonth() + 1,
                            day: ingredient.creationDateUsageHum.getDate()
                        };
                    }
                    if (ingredient.creationDateUsageVet) {
                        ingredient.creationDateUsageVet = {
                            year: ingredient.creationDateUsageVet.getFullYear(),
                            month: ingredient.creationDateUsageVet.getMonth() + 1,
                            day: ingredient.creationDateUsageVet.getDate()
                        };
                    }
                    this.ngbModalRef = this.ingredientModalRef(component, ingredient);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ingredientModalRef(component, new Ingredient());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ingredientModalRef(component: Component, ingredient: Ingredient): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ingredient = ingredient;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
