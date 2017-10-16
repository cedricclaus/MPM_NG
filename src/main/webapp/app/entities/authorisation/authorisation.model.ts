import { BaseEntity } from './../../shared';

export class Authorisation implements BaseEntity {
    constructor(
        public id?: string,
        public cti?: string,
        public authorisationNbr?: string,
        public authorisationDate?: any,
        public radiationDate?: any,
        public suspensionDate?: any,
        public radiationNote?: string,
        public suspensionNote?: string,
        public strength?: string,
        public pcName?: string,
        public pcId?: string,
        public pcNbr?: string,
        public name?: string,
        public status?: string,
        public statusId?: string,
        public pgUsage?: string,
        public pgProcedureType?: string,
        public pgAuthorisationType?: string,
    ) {
    }
}
