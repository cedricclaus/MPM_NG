import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    UsageService,
    UsagePopupService,
    UsageComponent,
    UsageDetailComponent,
    UsageDialogComponent,
    UsagePopupComponent,
    UsageDeletePopupComponent,
    UsageDeleteDialogComponent,
    usageRoute,
    usagePopupRoute,
    UsageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...usageRoute,
    ...usagePopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UsageComponent,
        UsageDetailComponent,
        UsageDialogComponent,
        UsageDeleteDialogComponent,
        UsagePopupComponent,
        UsageDeletePopupComponent,
    ],
    entryComponents: [
        UsageComponent,
        UsageDialogComponent,
        UsagePopupComponent,
        UsageDeleteDialogComponent,
        UsageDeletePopupComponent,
    ],
    providers: [
        UsageService,
        UsagePopupService,
        UsageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgUsageModule {}
