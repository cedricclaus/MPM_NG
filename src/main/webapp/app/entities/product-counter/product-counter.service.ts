import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ProductCounter } from './product-counter.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductCounterService {

    private resourceUrl = SERVER_API_URL + 'api/product-counters';

    constructor(private http: Http) { }

    create(productCounter: ProductCounter): Observable<ProductCounter> {
        const copy = this.convert(productCounter);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(productCounter: ProductCounter): Observable<ProductCounter> {
        const copy = this.convert(productCounter);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<ProductCounter> {
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
     * Convert a returned JSON object to ProductCounter.
     */
    private convertItemFromServer(json: any): ProductCounter {
        const entity: ProductCounter = Object.assign(new ProductCounter(), json);
        return entity;
    }

    /**
     * Convert a ProductCounter to a JSON which can be sent to the server.
     */
    private convert(productCounter: ProductCounter): ProductCounter {
        const copy: ProductCounter = Object.assign({}, productCounter);
        return copy;
    }
}
