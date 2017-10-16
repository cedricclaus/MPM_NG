import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public en?: string,
        public fr?: string,
        public de?: string,
        public nl?: string,
        public from?: number,
        public till?: number,
    ) {
    }
}
