import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ProductCounterService,
    ProductCounterPopupService,
    ProductCounterComponent,
    ProductCounterDetailComponent,
    ProductCounterDialogComponent,
    ProductCounterPopupComponent,
    ProductCounterDeletePopupComponent,
    ProductCounterDeleteDialogComponent,
    productCounterRoute,
    productCounterPopupRoute,
    ProductCounterResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productCounterRoute,
    ...productCounterPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductCounterComponent,
        ProductCounterDetailComponent,
        ProductCounterDialogComponent,
        ProductCounterDeleteDialogComponent,
        ProductCounterPopupComponent,
        ProductCounterDeletePopupComponent,
    ],
    entryComponents: [
        ProductCounterComponent,
        ProductCounterDialogComponent,
        ProductCounterPopupComponent,
        ProductCounterDeleteDialogComponent,
        ProductCounterDeletePopupComponent,
    ],
    providers: [
        ProductCounterService,
        ProductCounterPopupService,
        ProductCounterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgProductCounterModule {}
