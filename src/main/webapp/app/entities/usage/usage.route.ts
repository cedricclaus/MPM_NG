import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UsageComponent } from './usage.component';
import { UsageDetailComponent } from './usage-detail.component';
import { UsagePopupComponent } from './usage-dialog.component';
import { UsageDeletePopupComponent } from './usage-delete-dialog.component';

@Injectable()
export class UsageResolvePagingParams implements Resolve<any> {

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

export const usageRoute: Routes = [
    {
        path: 'usage',
        component: UsageComponent,
        resolve: {
            'pagingParams': UsageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'usage/:id',
        component: UsageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usagePopupRoute: Routes = [
    {
        path: 'usage-new',
        component: UsagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usage/:id/edit',
        component: UsagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usage/:id/delete',
        component: UsageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
