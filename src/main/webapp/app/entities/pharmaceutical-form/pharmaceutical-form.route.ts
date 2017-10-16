import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PharmaceuticalFormComponent } from './pharmaceutical-form.component';
import { PharmaceuticalFormDetailComponent } from './pharmaceutical-form-detail.component';
import { PharmaceuticalFormPopupComponent } from './pharmaceutical-form-dialog.component';
import { PharmaceuticalFormDeletePopupComponent } from './pharmaceutical-form-delete-dialog.component';

@Injectable()
export class PharmaceuticalFormResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const pharmaceuticalFormRoute: Routes = [
    {
        path: 'pharmaceutical-form',
        component: PharmaceuticalFormComponent,
        resolve: {
            'pagingParams': PharmaceuticalFormResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PharmaceuticalForms'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pharmaceutical-form/:id',
        component: PharmaceuticalFormDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PharmaceuticalForms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pharmaceuticalFormPopupRoute: Routes = [
    {
        path: 'pharmaceutical-form-new',
        component: PharmaceuticalFormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PharmaceuticalForms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pharmaceutical-form/:id/edit',
        component: PharmaceuticalFormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PharmaceuticalForms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pharmaceutical-form/:id/delete',
        component: PharmaceuticalFormDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PharmaceuticalForms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
