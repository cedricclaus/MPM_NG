import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ingredient } from './ingredient.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IngredientService {

    private resourceUrl = SERVER_API_URL + 'api/ingredients';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ingredient: Ingredient): Observable<Ingredient> {
        const copy = this.convert(ingredient);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ingredient: Ingredient): Observable<Ingredient> {
        const copy = this.convert(ingredient);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Ingredient> {
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
     * Convert a returned JSON object to Ingredient.
     */
    private convertItemFromServer(json: any): Ingredient {
        const entity: Ingredient = Object.assign(new Ingredient(), json);
        entity.creationDateUsageHum = this.dateUtils
            .convertLocalDateFromServer(json.creationDateUsageHum);
        entity.creationDateUsageVet = this.dateUtils
            .convertLocalDateFromServer(json.creationDateUsageVet);
        return entity;
    }

    /**
     * Convert a Ingredient to a JSON which can be sent to the server.
     */
    private convert(ingredient: Ingredient): Ingredient {
        const copy: Ingredient = Object.assign({}, ingredient);
        copy.creationDateUsageHum = this.dateUtils
            .convertLocalDateToServer(ingredient.creationDateUsageHum);
        copy.creationDateUsageVet = this.dateUtils
            .convertLocalDateToServer(ingredient.creationDateUsageVet);
        return copy;
    }
}
