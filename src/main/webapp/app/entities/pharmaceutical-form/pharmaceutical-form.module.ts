import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    PharmaceuticalFormService,
    PharmaceuticalFormPopupService,
    PharmaceuticalFormComponent,
    PharmaceuticalFormDetailComponent,
    PharmaceuticalFormDialogComponent,
    PharmaceuticalFormPopupComponent,
    PharmaceuticalFormDeletePopupComponent,
    PharmaceuticalFormDeleteDialogComponent,
    pharmaceuticalFormRoute,
    pharmaceuticalFormPopupRoute,
    PharmaceuticalFormResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pharmaceuticalFormRoute,
    ...pharmaceuticalFormPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PharmaceuticalFormComponent,
        PharmaceuticalFormDetailComponent,
        PharmaceuticalFormDialogComponent,
        PharmaceuticalFormDeleteDialogComponent,
        PharmaceuticalFormPopupComponent,
        PharmaceuticalFormDeletePopupComponent,
    ],
    entryComponents: [
        PharmaceuticalFormComponent,
        PharmaceuticalFormDialogComponent,
        PharmaceuticalFormPopupComponent,
        PharmaceuticalFormDeleteDialogComponent,
        PharmaceuticalFormDeletePopupComponent,
    ],
    providers: [
        PharmaceuticalFormService,
        PharmaceuticalFormPopupService,
        PharmaceuticalFormResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgPharmaceuticalFormModule {}
