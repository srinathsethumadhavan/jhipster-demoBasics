import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'entity-one',
        loadChildren: () => import('./entity-one/entity-one.module').then(m => m.BlogEntityOneModule)
      },
      {
        path: 'entity-two',
        loadChildren: () => import('./entity-two/entity-two.module').then(m => m.BlogEntityTwoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BlogEntityModule {}
