import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEntityOne, EntityOne } from 'app/shared/model/entity-one.model';
import { EntityOneService } from './entity-one.service';

@Component({
  selector: 'jhi-entity-one-update',
  templateUrl: './entity-one-update.component.html'
})
export class EntityOneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected entityOneService: EntityOneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityOne }) => {
      this.updateForm(entityOne);
    });
  }

  updateForm(entityOne: IEntityOne): void {
    this.editForm.patchValue({
      id: entityOne.id,
      name: entityOne.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityOne = this.createFromForm();
    if (entityOne.id !== undefined) {
      this.subscribeToSaveResponse(this.entityOneService.update(entityOne));
    } else {
      this.subscribeToSaveResponse(this.entityOneService.create(entityOne));
    }
  }

  private createFromForm(): IEntityOne {
    return {
      ...new EntityOne(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityOne>>): void {
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
