import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Unit } from './unit.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UnitService {

    private resourceUrl = SERVER_API_URL + 'api/units';

    constructor(private http: Http) { }

    create(unit: Unit): Observable<Unit> {
        const copy = this.convert(unit);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(unit: Unit): Observable<Unit> {
        const copy = this.convert(unit);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Unit> {
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
     * Convert a returned JSON object to Unit.
     */
    private convertItemFromServer(json: any): Unit {
        const entity: Unit = Object.assign(new Unit(), json);
        return entity;
    }

    /**
     * Convert a Unit to a JSON which can be sent to the server.
     */
    private convert(unit: Unit): Unit {
        const copy: Unit = Object.assign({}, unit);
        return copy;
    }
}
