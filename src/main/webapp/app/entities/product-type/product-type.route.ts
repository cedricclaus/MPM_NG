import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductTypeComponent } from './product-type.component';
import { ProductTypeDetailComponent } from './product-type-detail.component';
import { ProductTypePopupComponent } from './product-type-dialog.component';
import { ProductTypeDeletePopupComponent } from './product-type-delete-dialog.component';

@Injectable()
export class ProductTypeResolvePagingParams implements Resolve<any> {

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

export const productTypeRoute: Routes = [
    {
        path: 'product-type',
        component: ProductTypeComponent,
        resolve: {
            'pagingParams': ProductTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-type/:id',
        component: ProductTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productTypePopupRoute: Routes = [
    {
        path: 'product-type-new',
        component: ProductTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-type/:id/edit',
        component: ProductTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-type/:id/delete',
        component: ProductTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
