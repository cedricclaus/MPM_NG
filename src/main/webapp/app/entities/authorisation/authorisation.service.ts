import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Authorisation } from './authorisation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuthorisationService {

    private resourceUrl = SERVER_API_URL + 'api/authorisations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(authorisation: Authorisation): Observable<Authorisation> {
        const copy = this.convert(authorisation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(authorisation: Authorisation): Observable<Authorisation> {
        const copy = this.convert(authorisation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Authorisation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Authorisation.
     */
    private convertItemFromServer(json: any): Authorisation {
        const entity: Authorisation = Object.assign(new Authorisation(), json);
        entity.authorisationDate = this.dateUtils
            .convertLocalDateFromServer(json.authorisationDate);
        entity.radiationDate = this.dateUtils
            .convertLocalDateFromServer(json.radiationDate);
        entity.suspensionDate = this.dateUtils
            .convertLocalDateFromServer(json.suspensionDate);
        return entity;
    }

    /**
     * Convert a Authorisation to a JSON which can be sent to the server.
     */
    private convert(authorisation: Authorisation): Authorisation {
        const copy: Authorisation = Object.assign({}, authorisation);
        copy.authorisationDate = this.dateUtils
            .convertLocalDateToServer(authorisation.authorisationDate);
        copy.radiationDate = this.dateUtils
            .convertLocalDateToServer(authorisation.radiationDate);
        copy.suspensionDate = this.dateUtils
            .convertLocalDateToServer(authorisation.suspensionDate);
        return copy;
    }
}
