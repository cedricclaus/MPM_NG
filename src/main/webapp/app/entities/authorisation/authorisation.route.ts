import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuthorisationComponent } from './authorisation.component';
import { AuthorisationDetailComponent } from './authorisation-detail.component';
import { AuthorisationPopupComponent } from './authorisation-dialog.component';
import { AuthorisationDeletePopupComponent } from './authorisation-delete-dialog.component';

@Injectable()
export class AuthorisationResolvePagingParams implements Resolve<any> {

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

export const authorisationRoute: Routes = [
    {
        path: 'authorisation',
        component: AuthorisationComponent,
        resolve: {
            'pagingParams': AuthorisationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Authorisations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'authorisation/:id',
        component: AuthorisationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Authorisations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const authorisationPopupRoute: Routes = [
    {
        path: 'authorisation-new',
        component: AuthorisationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Authorisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation/:id/edit',
        component: AuthorisationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Authorisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation/:id/delete',
        component: AuthorisationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Authorisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
