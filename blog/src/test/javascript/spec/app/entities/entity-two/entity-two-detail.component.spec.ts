import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { EntityTwoDetailComponent } from 'app/entities/entity-two/entity-two-detail.component';
import { EntityTwo } from 'app/shared/model/entity-two.model';

describe('Component Tests', () => {
  describe('EntityTwo Management Detail Component', () => {
    let comp: EntityTwoDetailComponent;
    let fixture: ComponentFixture<EntityTwoDetailComponent>;
    const route = ({ data: of({ entityTwo: new EntityTwo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [EntityTwoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityTwoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityTwoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityTwo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityTwo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
