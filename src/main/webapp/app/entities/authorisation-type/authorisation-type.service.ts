import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuthorisationType } from './authorisation-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuthorisationTypeService {

    private resourceUrl = SERVER_API_URL + 'api/authorisation-types';

    constructor(private http: Http) { }

    create(authorisationType: AuthorisationType): Observable<AuthorisationType> {
        const copy = this.convert(authorisationType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(authorisationType: AuthorisationType): Observable<AuthorisationType> {
        const copy = this.convert(authorisationType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<AuthorisationType> {
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
     * Convert a returned JSON object to AuthorisationType.
     */
    private convertItemFromServer(json: any): AuthorisationType {
        const entity: AuthorisationType = Object.assign(new AuthorisationType(), json);
        return entity;
    }

    /**
     * Convert a AuthorisationType to a JSON which can be sent to the server.
     */
    private convert(authorisationType: AuthorisationType): AuthorisationType {
        const copy: AuthorisationType = Object.assign({}, authorisationType);
        return copy;
    }
}
