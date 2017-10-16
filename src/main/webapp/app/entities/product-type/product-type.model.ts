import { BaseEntity } from './../../shared';

export class ProductType implements BaseEntity {
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
