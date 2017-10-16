import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    AuthorisationStatusService,
    AuthorisationStatusPopupService,
    AuthorisationStatusComponent,
    AuthorisationStatusDetailComponent,
    AuthorisationStatusDialogComponent,
    AuthorisationStatusPopupComponent,
    AuthorisationStatusDeletePopupComponent,
    AuthorisationStatusDeleteDialogComponent,
    authorisationStatusRoute,
    authorisationStatusPopupRoute,
    AuthorisationStatusResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...authorisationStatusRoute,
    ...authorisationStatusPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuthorisationStatusComponent,
        AuthorisationStatusDetailComponent,
        AuthorisationStatusDialogComponent,
        AuthorisationStatusDeleteDialogComponent,
        AuthorisationStatusPopupComponent,
        AuthorisationStatusDeletePopupComponent,
    ],
    entryComponents: [
        AuthorisationStatusComponent,
        AuthorisationStatusDialogComponent,
        AuthorisationStatusPopupComponent,
        AuthorisationStatusDeleteDialogComponent,
        AuthorisationStatusDeletePopupComponent,
    ],
    providers: [
        AuthorisationStatusService,
        AuthorisationStatusPopupService,
        AuthorisationStatusResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgAuthorisationStatusModule {}
