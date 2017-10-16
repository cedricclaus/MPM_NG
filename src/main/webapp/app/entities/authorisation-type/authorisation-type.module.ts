import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    AuthorisationTypeService,
    AuthorisationTypePopupService,
    AuthorisationTypeComponent,
    AuthorisationTypeDetailComponent,
    AuthorisationTypeDialogComponent,
    AuthorisationTypePopupComponent,
    AuthorisationTypeDeletePopupComponent,
    AuthorisationTypeDeleteDialogComponent,
    authorisationTypeRoute,
    authorisationTypePopupRoute,
    AuthorisationTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...authorisationTypeRoute,
    ...authorisationTypePopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuthorisationTypeComponent,
        AuthorisationTypeDetailComponent,
        AuthorisationTypeDialogComponent,
        AuthorisationTypeDeleteDialogComponent,
        AuthorisationTypePopupComponent,
        AuthorisationTypeDeletePopupComponent,
    ],
    entryComponents: [
        AuthorisationTypeComponent,
        AuthorisationTypeDialogComponent,
        AuthorisationTypePopupComponent,
        AuthorisationTypeDeleteDialogComponent,
        AuthorisationTypeDeletePopupComponent,
    ],
    providers: [
        AuthorisationTypeService,
        AuthorisationTypePopupService,
        AuthorisationTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgAuthorisationTypeModule {}
