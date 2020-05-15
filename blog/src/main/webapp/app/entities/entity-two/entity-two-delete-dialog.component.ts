import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityTwo } from 'app/shared/model/entity-two.model';
import { EntityTwoService } from './entity-two.service';

@Component({
  templateUrl: './entity-two-delete-dialog.component.html'
})
export class EntityTwoDeleteDialogComponent {
  entityTwo?: IEntityTwo;

  constructor(protected entityTwoService: EntityTwoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entityTwoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityTwoListModification');
      this.activeModal.close();
    });
  }
}
