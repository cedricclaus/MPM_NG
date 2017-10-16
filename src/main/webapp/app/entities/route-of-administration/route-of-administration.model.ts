import { BaseEntity } from './../../shared';

export class RouteOfAdministration implements BaseEntity {
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
