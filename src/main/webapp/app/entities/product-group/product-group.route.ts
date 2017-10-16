import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductGroupComponent } from './product-group.component';
import { ProductGroupDetailComponent } from './product-group-detail.component';
import { ProductGroupPopupComponent } from './product-group-dialog.component';
import { ProductGroupDeletePopupComponent } from './product-group-delete-dialog.component';

@Injectable()
export class ProductGroupResolvePagingParams implements Resolve<any> {

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

export const productGroupRoute: Routes = [
    {
        path: 'product-group',
        component: ProductGroupComponent,
        resolve: {
            'pagingParams': ProductGroupResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductGroups'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-group/:id',
        component: ProductGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productGroupPopupRoute: Routes = [
    {
        path: 'product-group-new',
        component: ProductGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-group/:id/edit',
        component: ProductGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-group/:id/delete',
        component: ProductGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
