import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEntityTwo, EntityTwo } from 'app/shared/model/entity-two.model';
import { EntityTwoService } from './entity-two.service';

@Component({
  selector: 'jhi-entity-two-update',
  templateUrl: './entity-two-update.component.html'
})
export class EntityTwoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    phone: []
  });

  constructor(protected entityTwoService: EntityTwoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityTwo }) => {
      this.updateForm(entityTwo);
    });
  }

  updateForm(entityTwo: IEntityTwo): void {
    this.editForm.patchValue({
      id: entityTwo.id,
      name: entityTwo.name,
      email: entityTwo.email,
      phone: entityTwo.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityTwo = this.createFromForm();
    if (entityTwo.id !== undefined) {
      this.subscribeToSaveResponse(this.entityTwoService.update(entityTwo));
    } else {
      this.subscribeToSaveResponse(this.entityTwoService.create(entityTwo));
    }
  }

  private createFromForm(): IEntityTwo {
    return {
      ...new EntityTwo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityTwo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
