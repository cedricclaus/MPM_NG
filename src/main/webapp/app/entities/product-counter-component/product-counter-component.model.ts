import { BaseEntity } from './../../shared';

export class ProductCounterComponent implements BaseEntity {
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
