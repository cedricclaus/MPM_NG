import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductCounterComponentComponent } from './product-counter-component.component';
import { ProductCounterComponentDetailComponent } from './product-counter-component-detail.component';
import { ProductCounterComponentPopupComponent } from './product-counter-component-dialog.component';
import { ProductCounterComponentDeletePopupComponent } from './product-counter-component-delete-dialog.component';

@Injectable()
export class ProductCounterComponentResolvePagingParams implements Resolve<any> {

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

export const productCounterComponentRoute: Routes = [
    {
        path: 'product-counter-component',
        component: ProductCounterComponentComponent,
        resolve: {
            'pagingParams': ProductCounterComponentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounterComponents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-counter-component/:id',
        component: ProductCounterComponentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounterComponents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productCounterComponentPopupRoute: Routes = [
    {
        path: 'product-counter-component-new',
        component: ProductCounterComponentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounterComponents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-counter-component/:id/edit',
        component: ProductCounterComponentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounterComponents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-counter-component/:id/delete',
        component: ProductCounterComponentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCounterComponents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
