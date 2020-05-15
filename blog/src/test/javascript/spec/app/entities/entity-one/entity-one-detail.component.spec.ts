import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { EntityOneDetailComponent } from 'app/entities/entity-one/entity-one-detail.component';
import { EntityOne } from 'app/shared/model/entity-one.model';

describe('Component Tests', () => {
  describe('EntityOne Management Detail Component', () => {
    let comp: EntityOneDetailComponent;
    let fixture: ComponentFixture<EntityOneDetailComponent>;
    const route = ({ data: of({ entityOne: new EntityOne(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [EntityOneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityOneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityOneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityOne on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityOne).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
