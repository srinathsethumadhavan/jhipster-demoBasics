import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { EntityOneUpdateComponent } from 'app/entities/entity-one/entity-one-update.component';
import { EntityOneService } from 'app/entities/entity-one/entity-one.service';
import { EntityOne } from 'app/shared/model/entity-one.model';

describe('Component Tests', () => {
  describe('EntityOne Management Update Component', () => {
    let comp: EntityOneUpdateComponent;
    let fixture: ComponentFixture<EntityOneUpdateComponent>;
    let service: EntityOneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [EntityOneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityOneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityOneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityOneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityOne(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityOne();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
