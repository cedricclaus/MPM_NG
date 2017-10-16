import { BaseEntity } from './../../shared';

export class ActiveSubstance implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public ingredientId?: string,
        public notes?: string,
        public asEquivalent?: string,
        public additionalInfo?: string,
        public amount?: number,
        public amountEq?: number,
        public nonNumericalAmount?: string,
        public nonNumericalAmountEq?: string,
        public unitId?: string,
        public unit?: string,
        public unitEq?: string,
        public unitEqId?: string,
        public pcId?: string,
        public pccId?: string,
        public pccEn?: string,
        public ingredientEqId?: string,
    ) {
    }
}
