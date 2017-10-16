import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RouteOfAdministrationComponent } from './route-of-administration.component';
import { RouteOfAdministrationDetailComponent } from './route-of-administration-detail.component';
import { RouteOfAdministrationPopupComponent } from './route-of-administration-dialog.component';
import { RouteOfAdministrationDeletePopupComponent } from './route-of-administration-delete-dialog.component';

@Injectable()
export class RouteOfAdministrationResolvePagingParams implements Resolve<any> {

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

export const routeOfAdministrationRoute: Routes = [
    {
        path: 'route-of-administration',
        component: RouteOfAdministrationComponent,
        resolve: {
            'pagingParams': RouteOfAdministrationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOfAdministrations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'route-of-administration/:id',
        component: RouteOfAdministrationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOfAdministrations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const routeOfAdministrationPopupRoute: Routes = [
    {
        path: 'route-of-administration-new',
        component: RouteOfAdministrationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOfAdministrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'route-of-administration/:id/edit',
        component: RouteOfAdministrationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOfAdministrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'route-of-administration/:id/delete',
        component: RouteOfAdministrationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOfAdministrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
