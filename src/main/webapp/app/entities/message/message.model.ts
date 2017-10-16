import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: string,
        public messageId?: string,
        public timestamp?: any,
        public user?: string,
        public type?: string,
        public routingKey?: string,
        public queueName?: string,
    ) {
    }
}
