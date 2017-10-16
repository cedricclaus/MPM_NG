import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExcipientComponent } from './excipient.component';
import { ExcipientDetailComponent } from './excipient-detail.component';
import { ExcipientPopupComponent } from './excipient-dialog.component';
import { ExcipientDeletePopupComponent } from './excipient-delete-dialog.component';

@Injectable()
export class ExcipientResolvePagingParams implements Resolve<any> {

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

export const excipientRoute: Routes = [
    {
        path: 'excipient',
        component: ExcipientComponent,
        resolve: {
            'pagingParams': ExcipientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Excipients'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'excipient/:id',
        component: ExcipientDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Excipients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const excipientPopupRoute: Routes = [
    {
        path: 'excipient-new',
        component: ExcipientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Excipients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'excipient/:id/edit',
        component: ExcipientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Excipients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'excipient/:id/delete',
        component: ExcipientDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Excipients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
