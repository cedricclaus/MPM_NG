import { BaseEntity } from './../../shared';

export class ProductCounter implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public number?: string,
        public strength?: string,
        public pharmaceuticalForm?: string,
        public status?: string,
        public statusId?: string,
        public pcId?: string,
    ) {
    }
}
