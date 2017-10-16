import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuthorisationTypeComponent } from './authorisation-type.component';
import { AuthorisationTypeDetailComponent } from './authorisation-type-detail.component';
import { AuthorisationTypePopupComponent } from './authorisation-type-dialog.component';
import { AuthorisationTypeDeletePopupComponent } from './authorisation-type-delete-dialog.component';

@Injectable()
export class AuthorisationTypeResolvePagingParams implements Resolve<any> {

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

export const authorisationTypeRoute: Routes = [
    {
        path: 'authorisation-type',
        component: AuthorisationTypeComponent,
        resolve: {
            'pagingParams': AuthorisationTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'authorisation-type/:id',
        component: AuthorisationTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const authorisationTypePopupRoute: Routes = [
    {
        path: 'authorisation-type-new',
        component: AuthorisationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation-type/:id/edit',
        component: AuthorisationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation-type/:id/delete',
        component: AuthorisationTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
