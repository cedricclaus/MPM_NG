import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuthorisationStatus } from './authorisation-status.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuthorisationStatusService {

    private resourceUrl = SERVER_API_URL + 'api/authorisation-statuses';

    constructor(private http: Http) { }

    create(authorisationStatus: AuthorisationStatus): Observable<AuthorisationStatus> {
        const copy = this.convert(authorisationStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(authorisationStatus: AuthorisationStatus): Observable<AuthorisationStatus> {
        const copy = this.convert(authorisationStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<AuthorisationStatus> {
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
     * Convert a returned JSON object to AuthorisationStatus.
     */
    private convertItemFromServer(json: any): AuthorisationStatus {
        const entity: AuthorisationStatus = Object.assign(new AuthorisationStatus(), json);
        return entity;
    }

    /**
     * Convert a AuthorisationStatus to a JSON which can be sent to the server.
     */
    private convert(authorisationStatus: AuthorisationStatus): AuthorisationStatus {
        const copy: AuthorisationStatus = Object.assign({}, authorisationStatus);
        return copy;
    }
}
