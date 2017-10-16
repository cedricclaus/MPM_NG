import { BaseEntity } from './../../shared';

export class Excipient implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public notes?: string,
        public additionalInfo?: string,
        public amount?: number,
        public nonNumericalAmount?: string,
        public unitId?: string,
        public unit?: string,
        public pcId?: string,
        public pccId?: string,
        public pccEn?: string,
        public ingredientId?: string,
        public belongsTo?: string,
        public knownEffect?: boolean,
    ) {
        this.knownEffect = false;
    }
}
