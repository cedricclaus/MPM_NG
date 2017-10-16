import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ActiveSubstanceService,
    ActiveSubstancePopupService,
    ActiveSubstanceComponent,
    ActiveSubstanceDetailComponent,
    ActiveSubstanceDialogComponent,
    ActiveSubstancePopupComponent,
    ActiveSubstanceDeletePopupComponent,
    ActiveSubstanceDeleteDialogComponent,
    activeSubstanceRoute,
    activeSubstancePopupRoute,
    ActiveSubstanceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...activeSubstanceRoute,
    ...activeSubstancePopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActiveSubstanceComponent,
        ActiveSubstanceDetailComponent,
        ActiveSubstanceDialogComponent,
        ActiveSubstanceDeleteDialogComponent,
        ActiveSubstancePopupComponent,
        ActiveSubstanceDeletePopupComponent,
    ],
    entryComponents: [
        ActiveSubstanceComponent,
        ActiveSubstanceDialogComponent,
        ActiveSubstancePopupComponent,
        ActiveSubstanceDeleteDialogComponent,
        ActiveSubstanceDeletePopupComponent,
    ],
    providers: [
        ActiveSubstanceService,
        ActiveSubstancePopupService,
        ActiveSubstanceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgActiveSubstanceModule {}
