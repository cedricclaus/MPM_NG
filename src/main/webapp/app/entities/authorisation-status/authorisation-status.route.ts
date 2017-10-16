import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuthorisationStatusComponent } from './authorisation-status.component';
import { AuthorisationStatusDetailComponent } from './authorisation-status-detail.component';
import { AuthorisationStatusPopupComponent } from './authorisation-status-dialog.component';
import { AuthorisationStatusDeletePopupComponent } from './authorisation-status-delete-dialog.component';

@Injectable()
export class AuthorisationStatusResolvePagingParams implements Resolve<any> {

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

export const authorisationStatusRoute: Routes = [
    {
        path: 'authorisation-status',
        component: AuthorisationStatusComponent,
        resolve: {
            'pagingParams': AuthorisationStatusResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'authorisation-status/:id',
        component: AuthorisationStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const authorisationStatusPopupRoute: Routes = [
    {
        path: 'authorisation-status-new',
        component: AuthorisationStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation-status/:id/edit',
        component: AuthorisationStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authorisation-status/:id/delete',
        component: AuthorisationStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorisationStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
