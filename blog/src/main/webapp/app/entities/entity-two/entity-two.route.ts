import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityTwo, EntityTwo } from 'app/shared/model/entity-two.model';
import { EntityTwoService } from './entity-two.service';
import { EntityTwoComponent } from './entity-two.component';
import { EntityTwoDetailComponent } from './entity-two-detail.component';
import { EntityTwoUpdateComponent } from './entity-two-update.component';

@Injectable({ providedIn: 'root' })
export class EntityTwoResolve implements Resolve<IEntityTwo> {
  constructor(private service: EntityTwoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityTwo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityTwo: HttpResponse<EntityTwo>) => {
          if (entityTwo.body) {
            return of(entityTwo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityTwo());
  }
}

export const entityTwoRoute: Routes = [
  {
    path: '',
    component: EntityTwoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'blogApp.entityTwo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityTwoDetailComponent,
    resolve: {
      entityTwo: EntityTwoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'blogApp.entityTwo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityTwoUpdateComponent,
    resolve: {
      entityTwo: EntityTwoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'blogApp.entityTwo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityTwoUpdateComponent,
    resolve: {
      entityTwo: EntityTwoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'blogApp.entityTwo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
