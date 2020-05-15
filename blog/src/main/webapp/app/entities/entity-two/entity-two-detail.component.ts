import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityTwo } from 'app/shared/model/entity-two.model';

@Component({
  selector: 'jhi-entity-two-detail',
  templateUrl: './entity-two-detail.component.html'
})
export class EntityTwoDetailComponent implements OnInit {
  entityTwo: IEntityTwo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityTwo }) => (this.entityTwo = entityTwo));
  }

  previousState(): void {
    window.history.back();
  }
}
