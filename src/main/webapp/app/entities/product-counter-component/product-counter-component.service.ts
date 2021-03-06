import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ProductCounterComponent } from './product-counter-component.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductCounterComponentService {

    private resourceUrl = SERVER_API_URL + 'api/product-counter-components';

    constructor(private http: Http) { }

    create(productCounterComponent: ProductCounterComponent): Observable<ProductCounterComponent> {
        const copy = this.convert(productCounterComponent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(productCounterComponent: ProductCounterComponent): Observable<ProductCounterComponent> {
        const copy = this.convert(productCounterComponent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<ProductCounterComponent> {
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
     * Convert a returned JSON object to ProductCounterComponent.
     */
    private convertItemFromServer(json: any): ProductCounterComponent {
        const entity: ProductCounterComponent = Object.assign(new ProductCounterComponent(), json);
        return entity;
    }

    /**
     * Convert a ProductCounterComponent to a JSON which can be sent to the server.
     */
    private convert(productCounterComponent: ProductCounterComponent): ProductCounterComponent {
        const copy: ProductCounterComponent = Object.assign({}, productCounterComponent);
        return copy;
    }
}
