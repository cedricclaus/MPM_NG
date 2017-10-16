import { BaseEntity } from './../../shared';

export class ProcedureType implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public en?: string,
        public fr?: string,
        public de?: string,
        public nl?: string,
    ) {
    }
}
