import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityOne } from 'app/shared/model/entity-one.model';
import { EntityOneService } from './entity-one.service';

@Component({
  templateUrl: './entity-one-delete-dialog.component.html'
})
export class EntityOneDeleteDialogComponent {
  entityOne?: IEntityOne;

  constructor(protected entityOneService: EntityOneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entityOneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityOneListModification');
      this.activeModal.close();
    });
  }
}
