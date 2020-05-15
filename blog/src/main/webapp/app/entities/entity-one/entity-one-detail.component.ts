import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityOne } from 'app/shared/model/entity-one.model';

@Component({
  selector: 'jhi-entity-one-detail',
  templateUrl: './entity-one-detail.component.html'
})
export class EntityOneDetailComponent implements OnInit {
  entityOne: IEntityOne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityOne }) => (this.entityOne = entityOne));
  }

  previousState(): void {
    window.history.back();
  }
}
