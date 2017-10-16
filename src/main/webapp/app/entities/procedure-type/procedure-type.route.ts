import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProcedureTypeComponent } from './procedure-type.component';
import { ProcedureTypeDetailComponent } from './procedure-type-detail.component';
import { ProcedureTypePopupComponent } from './procedure-type-dialog.component';
import { ProcedureTypeDeletePopupComponent } from './procedure-type-delete-dialog.component';

@Injectable()
export class ProcedureTypeResolvePagingParams implements Resolve<any> {

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

export const procedureTypeRoute: Routes = [
    {
        path: 'procedure-type',
        component: ProcedureTypeComponent,
        resolve: {
            'pagingParams': ProcedureTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'procedure-type/:id',
        component: ProcedureTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const procedureTypePopupRoute: Routes = [
    {
        path: 'procedure-type-new',
        component: ProcedureTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure-type/:id/edit',
        component: ProcedureTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'procedure-type/:id/delete',
        component: ProcedureTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProcedureTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
