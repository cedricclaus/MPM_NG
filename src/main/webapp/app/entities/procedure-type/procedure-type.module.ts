import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MpmNgSharedModule } from '../../shared';
import {
    ProcedureTypeService,
    ProcedureTypePopupService,
    ProcedureTypeComponent,
    ProcedureTypeDetailComponent,
    ProcedureTypeDialogComponent,
    ProcedureTypePopupComponent,
    ProcedureTypeDeletePopupComponent,
    ProcedureTypeDeleteDialogComponent,
    procedureTypeRoute,
    procedureTypePopupRoute,
    ProcedureTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...procedureTypeRoute,
    ...procedureTypePopupRoute,
];

@NgModule({
    imports: [
        MpmNgSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProcedureTypeComponent,
        ProcedureTypeDetailComponent,
        ProcedureTypeDialogComponent,
        ProcedureTypeDeleteDialogComponent,
        ProcedureTypePopupComponent,
        ProcedureTypeDeletePopupComponent,
    ],
    entryComponents: [
        ProcedureTypeComponent,
        ProcedureTypeDialogComponent,
        ProcedureTypePopupComponent,
        ProcedureTypeDeleteDialogComponent,
        ProcedureTypeDeletePopupComponent,
    ],
    providers: [
        ProcedureTypeService,
        ProcedureTypePopupService,
        ProcedureTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgProcedureTypeModule {}
