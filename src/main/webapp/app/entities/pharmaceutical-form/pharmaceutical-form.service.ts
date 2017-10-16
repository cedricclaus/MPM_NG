import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { PharmaceuticalForm } from './pharmaceutical-form.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PharmaceuticalFormService {

    private resourceUrl = SERVER_API_URL + 'api/pharmaceutical-forms';

    constructor(private http: Http) { }

    create(pharmaceuticalForm: PharmaceuticalForm): Observable<PharmaceuticalForm> {
        const copy = this.convert(pharmaceuticalForm);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pharmaceuticalForm: PharmaceuticalForm): Observable<PharmaceuticalForm> {
        const copy = this.convert(pharmaceuticalForm);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<PharmaceuticalForm> {
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
     * Convert a returned JSON object to PharmaceuticalForm.
     */
    private convertItemFromServer(json: any): PharmaceuticalForm {
        const entity: PharmaceuticalForm = Object.assign(new PharmaceuticalForm(), json);
        return entity;
    }

    /**
     * Convert a PharmaceuticalForm to a JSON which can be sent to the server.
     */
    private convert(pharmaceuticalForm: PharmaceuticalForm): PharmaceuticalForm {
        const copy: PharmaceuticalForm = Object.assign({}, pharmaceuticalForm);
        return copy;
    }
}
