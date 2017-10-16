import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ExcipientService,
    ExcipientPopupService,
    ExcipientComponent,
    ExcipientDetailComponent,
    ExcipientDialogComponent,
    ExcipientPopupComponent,
    ExcipientDeletePopupComponent,
    ExcipientDeleteDialogComponent,
    excipientRoute,
    excipientPopupRoute,
    ExcipientResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...excipientRoute,
    ...excipientPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExcipientComponent,
        ExcipientDetailComponent,
        ExcipientDialogComponent,
        ExcipientDeleteDialogComponent,
        ExcipientPopupComponent,
        ExcipientDeletePopupComponent,
    ],
    entryComponents: [
        ExcipientComponent,
        ExcipientDialogComponent,
        ExcipientPopupComponent,
        ExcipientDeleteDialogComponent,
        ExcipientDeletePopupComponent,
    ],
    providers: [
        ExcipientService,
        ExcipientPopupService,
        ExcipientResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgExcipientModule {}
