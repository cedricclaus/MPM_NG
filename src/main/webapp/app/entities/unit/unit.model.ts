import { BaseEntity } from './../../shared';

export class Unit implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public en?: string,
        public fr?: string,
        public de?: string,
        public nl?: string,
        public ucum?: string,
        public description?: string,
        public deprecated?: boolean,
    ) {
        this.deprecated = false;
    }
}
