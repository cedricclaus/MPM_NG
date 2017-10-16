import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ActiveSubstance } from './active-substance.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActiveSubstanceService {

    private resourceUrl = SERVER_API_URL + 'api/active-substances';

    constructor(private http: Http) { }

    create(activeSubstance: ActiveSubstance): Observable<ActiveSubstance> {
        const copy = this.convert(activeSubstance);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(activeSubstance: ActiveSubstance): Observable<ActiveSubstance> {
        const copy = this.convert(activeSubstance);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<ActiveSubstance> {
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
     * Convert a returned JSON object to ActiveSubstance.
     */
    private convertItemFromServer(json: any): ActiveSubstance {
        const entity: ActiveSubstance = Object.assign(new ActiveSubstance(), json);
        return entity;
    }

    /**
     * Convert a ActiveSubstance to a JSON which can be sent to the server.
     */
    private convert(activeSubstance: ActiveSubstance): ActiveSubstance {
        const copy: ActiveSubstance = Object.assign({}, activeSubstance);
        return copy;
    }
}
