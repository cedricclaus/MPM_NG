import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    AuthorisationService,
    AuthorisationPopupService,
    AuthorisationComponent,
    AuthorisationDetailComponent,
    AuthorisationDialogComponent,
    AuthorisationPopupComponent,
    AuthorisationDeletePopupComponent,
    AuthorisationDeleteDialogComponent,
    authorisationRoute,
    authorisationPopupRoute,
    AuthorisationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...authorisationRoute,
    ...authorisationPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuthorisationComponent,
        AuthorisationDetailComponent,
        AuthorisationDialogComponent,
        AuthorisationDeleteDialogComponent,
        AuthorisationPopupComponent,
        AuthorisationDeletePopupComponent,
    ],
    entryComponents: [
        AuthorisationComponent,
        AuthorisationDialogComponent,
        AuthorisationPopupComponent,
        AuthorisationDeleteDialogComponent,
        AuthorisationDeletePopupComponent,
    ],
    providers: [
        AuthorisationService,
        AuthorisationPopupService,
        AuthorisationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgAuthorisationModule {}
