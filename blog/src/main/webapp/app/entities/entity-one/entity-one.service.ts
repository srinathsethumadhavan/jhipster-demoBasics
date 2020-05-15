import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityOne } from 'app/shared/model/entity-one.model';

type EntityResponseType = HttpResponse<IEntityOne>;
type EntityArrayResponseType = HttpResponse<IEntityOne[]>;

@Injectable({ providedIn: 'root' })
export class EntityOneService {
  public resourceUrl = SERVER_API_URL + 'api/entity-ones';

  constructor(protected http: HttpClient) {}

  create(entityOne: IEntityOne): Observable<EntityResponseType> {
    return this.http.post<IEntityOne>(this.resourceUrl, entityOne, { observe: 'response' });
  }

  update(entityOne: IEntityOne): Observable<EntityResponseType> {
    return this.http.put<IEntityOne>(this.resourceUrl, entityOne, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntityOne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityOne[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
