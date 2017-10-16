import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ProductCounterComponentService,
    ProductCounterComponentPopupService,
    ProductCounterComponentComponent,
    ProductCounterComponentDetailComponent,
    ProductCounterComponentDialogComponent,
    ProductCounterComponentPopupComponent,
    ProductCounterComponentDeletePopupComponent,
    ProductCounterComponentDeleteDialogComponent,
    productCounterComponentRoute,
    productCounterComponentPopupRoute,
    ProductCounterComponentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productCounterComponentRoute,
    ...productCounterComponentPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductCounterComponentComponent,
        ProductCounterComponentDetailComponent,
        ProductCounterComponentDialogComponent,
        ProductCounterComponentDeleteDialogComponent,
        ProductCounterComponentPopupComponent,
        ProductCounterComponentDeletePopupComponent,
    ],
    entryComponents: [
        ProductCounterComponentComponent,
        ProductCounterComponentDialogComponent,
        ProductCounterComponentPopupComponent,
        ProductCounterComponentDeleteDialogComponent,
        ProductCounterComponentDeletePopupComponent,
    ],
    providers: [
        ProductCounterComponentService,
        ProductCounterComponentPopupService,
        ProductCounterComponentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgProductCounterComponentModule {}
