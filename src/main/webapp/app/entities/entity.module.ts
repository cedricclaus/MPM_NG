import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MpmNgActiveSubstanceModule } from './active-substance/active-substance.module';
import { MpmNgUsageModule } from './usage/usage.module';
import { MpmNgAuthorisationStatusModule } from './authorisation-status/authorisation-status.module';
import { MpmNgAuthorisationTypeModule } from './authorisation-type/authorisation-type.module';
import { MpmNgCountryModule } from './country/country.module';
import { MpmNgExcipientModule } from './excipient/excipient.module';
import { MpmNgIngredientModule } from './ingredient/ingredient.module';
import { MpmNgMessageModule } from './message/message.module';
import { MpmNgAuthorisationModule } from './authorisation/authorisation.module';
import { MpmNgProcedureTypeModule } from './procedure-type/procedure-type.module';
import { MpmNgProductCounterModule } from './product-counter/product-counter.module';
import { MpmNgProductCounterComponentModule } from './product-counter-component/product-counter-component.module';
import { MpmNgProductGroupModule } from './product-group/product-group.module';
import { MpmNgProductTypeModule } from './product-type/product-type.module';
import { MpmNgRouteOfAdministrationModule } from './route-of-administration/route-of-administration.module';
import { MpmNgUnitModule } from './unit/unit.module';
import { MpmNgPharmaceuticalFormModule } from './pharmaceutical-form/pharmaceutical-form.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MpmNgActiveSubstanceModule,
        MpmNgUsageModule,
        MpmNgAuthorisationStatusModule,
        MpmNgAuthorisationTypeModule,
        MpmNgCountryModule,
        MpmNgExcipientModule,
        MpmNgIngredientModule,
        MpmNgMessageModule,
        MpmNgAuthorisationModule,
        MpmNgProcedureTypeModule,
        MpmNgProductCounterModule,
        MpmNgProductCounterComponentModule,
        MpmNgProductGroupModule,
        MpmNgProductTypeModule,
        MpmNgRouteOfAdministrationModule,
        MpmNgUnitModule,
        MpmNgPharmaceuticalFormModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MpmNgEntityModule {}
