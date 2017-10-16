import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductCounterComponent } from './product-counter.component';
import { ProductCounterDetailComponent } from './product-counter-detail.component';
import { ProductCounterPopupComponent } from './product-counter-dialog.component';
import { ProductCounterDeletePopupComponent } from './product-counter-delete-dialog.component';

@Injectable()
export class ProductCounterResolvePagingParams implements Resolve<any> {

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

export const productCounterRoute: Routes = [
    {
        path: 'product-counter',
        component: ProductCounterComponent,
        resolve: {
            'pagingParams': ProductCounterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounters'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-counter/:id',
        component: ProductCounterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productCounterPopupRoute: Routes = [
    {
        path: 'product-counter-new',
        component: ProductCounterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-counter/:id/edit',
        component: ProductCounterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-counter/:id/delete',
        component: ProductCounterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
