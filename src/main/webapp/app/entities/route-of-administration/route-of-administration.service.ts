import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { RouteOfAdministration } from './route-of-administration.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RouteOfAdministrationService {

    private resourceUrl = SERVER_API_URL + 'api/route-of-administrations';

    constructor(private http: Http) { }

    create(routeOfAdministration: RouteOfAdministration): Observable<RouteOfAdministration> {
        const copy = this.convert(routeOfAdministration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(routeOfAdministration: RouteOfAdministration): Observable<RouteOfAdministration> {
        const copy = this.convert(routeOfAdministration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<RouteOfAdministration> {
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
     * Convert a returned JSON object to RouteOfAdministration.
     */
    private convertItemFromServer(json: any): RouteOfAdministration {
        const entity: RouteOfAdministration = Object.assign(new RouteOfAdministration(), json);
        return entity;
    }

    /**
     * Convert a RouteOfAdministration to a JSON which can be sent to the server.
     */
    private convert(routeOfAdministration: RouteOfAdministration): RouteOfAdministration {
        const copy: RouteOfAdministration = Object.assign({}, routeOfAdministration);
        return copy;
    }
}
