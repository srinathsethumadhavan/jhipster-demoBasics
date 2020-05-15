import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { EntityTwoUpdateComponent } from 'app/entities/entity-two/entity-two-update.component';
import { EntityTwoService } from 'app/entities/entity-two/entity-two.service';
import { EntityTwo } from 'app/shared/model/entity-two.model';

describe('Component Tests', () => {
  describe('EntityTwo Management Update Component', () => {
    let comp: EntityTwoUpdateComponent;
    let fixture: ComponentFixture<EntityTwoUpdateComponent>;
    let service: EntityTwoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [EntityTwoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntityTwoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityTwoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityTwoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntityTwo(123);
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
        const entity = new EntityTwo();
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
