import { BaseEntity } from './../../shared';

export const enum AlloHomeoType {
    'ALLOPATHIC',
    'HOMEOPATHIC'
}

export class ProductGroup implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public number?: string,
        public deprecated?: boolean,
        public usageCode?: string,
        public usageId?: string,
        public procedureTypeCode?: string,
        public procedureTypeId?: string,
        public autorizationTypeCode?: string,
        public autorizationTypeId?: string,
        public productTypeCode?: string,
        public productTypeId?: string,
        public alloHomeo?: AlloHomeoType,
    ) {
        this.deprecated = false;
    }
}
