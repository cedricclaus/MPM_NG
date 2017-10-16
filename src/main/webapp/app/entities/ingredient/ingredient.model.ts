import { BaseEntity } from './../../shared';

export const enum IngredientType {
    'ACTIVE_SUBSTANCE',
    'EXCIPIENT'
}

export class Ingredient implements BaseEntity {
    constructor(
        public id?: string,
        public en?: string,
        public fr?: string,
        public de?: string,
        public nl?: string,
        public category?: string,
        public notes?: string,
        public snomedCTId?: string,
        public synonyms?: string,
        public casCode?: string,
        public deprecated?: boolean,
        public innovation?: boolean,
        public creationDateUsageHum?: any,
        public creationDateUsageVet?: any,
        public residualProduct?: boolean,
        public type?: IngredientType,
    ) {
        this.deprecated = false;
        this.innovation = false;
        this.residualProduct = false;
    }
}
