import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActiveSubstanceComponent } from './active-substance.component';
import { ActiveSubstanceDetailComponent } from './active-substance-detail.component';
import { ActiveSubstancePopupComponent } from './active-substance-dialog.component';
import { ActiveSubstanceDeletePopupComponent } from './active-substance-delete-dialog.component';

@Injectable()
export class ActiveSubstanceResolvePagingParams implements Resolve<any> {

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

export const activeSubstanceRoute: Routes = [
    {
        path: 'active-substance',
        component: ActiveSubstanceComponent,
        resolve: {
            'pagingParams': ActiveSubstanceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActiveSubstances'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'active-substance/:id',
        component: ActiveSubstanceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActiveSubstances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const activeSubstancePopupRoute: Routes = [
    {
        path: 'active-substance-new',
        component: ActiveSubstancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActiveSubstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'active-substance/:id/edit',
        component: ActiveSubstancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActiveSubstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'active-substance/:id/delete',
        component: ActiveSubstanceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActiveSubstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
