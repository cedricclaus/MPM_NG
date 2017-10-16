import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ProductGroupService,
    ProductGroupPopupService,
    ProductGroupComponent,
    ProductGroupDetailComponent,
    ProductGroupDialogComponent,
    ProductGroupPopupComponent,
    ProductGroupDeletePopupComponent,
    ProductGroupDeleteDialogComponent,
    productGroupRoute,
    productGroupPopupRoute,
    ProductGroupResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productGroupRoute,
    ...productGroupPopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductGroupComponent,
        ProductGroupDetailComponent,
        ProductGroupDialogComponent,
        ProductGroupDeleteDialogComponent,
        ProductGroupPopupComponent,
        ProductGroupDeletePopupComponent,
    ],
    entryComponents: [
        ProductGroupComponent,
        ProductGroupDialogComponent,
        ProductGroupPopupComponent,
        ProductGroupDeleteDialogComponent,
        ProductGroupDeletePopupComponent,
    ],
    providers: [
        ProductGroupService,
        ProductGroupPopupService,
        ProductGroupResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgProductGroupModule {}
