import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Excipient } from './excipient.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExcipientService {

    private resourceUrl = SERVER_API_URL + 'api/excipients';

    constructor(private http: Http) { }

    create(excipient: Excipient): Observable<Excipient> {
        const copy = this.convert(excipient);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(excipient: Excipient): Observable<Excipient> {
        const copy = this.convert(excipient);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Excipient> {
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
     * Convert a returned JSON object to Excipient.
     */
    private convertItemFromServer(json: any): Excipient {
        const entity: Excipient = Object.assign(new Excipient(), json);
        return entity;
    }

    /**
     * Convert a Excipient to a JSON which can be sent to the server.
     */
    private convert(excipient: Excipient): Excipient {
        const copy: Excipient = Object.assign({}, excipient);
        return copy;
    }
}
