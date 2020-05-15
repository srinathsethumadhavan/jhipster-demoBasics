import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityTwo } from 'app/shared/model/entity-two.model';

type EntityResponseType = HttpResponse<IEntityTwo>;
type EntityArrayResponseType = HttpResponse<IEntityTwo[]>;

@Injectable({ providedIn: 'root' })
export class EntityTwoService {
  public resourceUrl = SERVER_API_URL + 'api/entity-twos';

  constructor(protected http: HttpClient) {}

  create(entityTwo: IEntityTwo): Observable<EntityResponseType> {
    return this.http.post<IEntityTwo>(this.resourceUrl, entityTwo, { observe: 'response' });
  }

  update(entityTwo: IEntityTwo): Observable<EntityResponseType> {
    return this.http.put<IEntityTwo>(this.resourceUrl, entityTwo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntityTwo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntityTwo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
