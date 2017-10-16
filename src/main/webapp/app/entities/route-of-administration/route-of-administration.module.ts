import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    RouteOfAdministrationService,
    RouteOfAdministrationPopupService,
    RouteOfAdministrationComponent,
    RouteOfAdministrationDetailComponent,
    RouteOfAdministrationDialogComponent,
    RouteOfAdministrationPopupComponent,
    RouteOfAdministrationDeletePopupComponent,
    RouteOfAdministrationDeleteDialogComponent,
    routeOfAdministrationRoute,
    routeOfAdministrationPopupRoute,
    RouteOfAdministrationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...routeOfAdministrationRoute,
    ...routeOfAdministrationPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RouteOfAdministrationComponent,
        RouteOfAdministrationDetailComponent,
        RouteOfAdministrationDialogComponent,
        RouteOfAdministrationDeleteDialogComponent,
        RouteOfAdministrationPopupComponent,
        RouteOfAdministrationDeletePopupComponent,
    ],
    entryComponents: [
        RouteOfAdministrationComponent,
        RouteOfAdministrationDialogComponent,
        RouteOfAdministrationPopupComponent,
        RouteOfAdministrationDeleteDialogComponent,
        RouteOfAdministrationDeletePopupComponent,
    ],
    providers: [
        RouteOfAdministrationService,
        RouteOfAdministrationPopupService,
        RouteOfAdministrationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgRouteOfAdministrationModule {}
