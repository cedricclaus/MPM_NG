import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Usage } from './usage.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UsageService {

    private resourceUrl = SERVER_API_URL + 'api/usages';

    constructor(private http: Http) { }

    create(usage: Usage): Observable<Usage> {
        const copy = this.convert(usage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(usage: Usage): Observable<Usage> {
        const copy = this.convert(usage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Usage> {
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
     * Convert a returned JSON object to Usage.
     */
    private convertItemFromServer(json: any): Usage {
        const entity: Usage = Object.assign(new Usage(), json);
        return entity;
    }

    /**
     * Convert a Usage to a JSON which can be sent to the server.
     */
    private convert(usage: Usage): Usage {
        const copy: Usage = Object.assign({}, usage);
        return copy;
    }
}