import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from 'app/shared/shared.module';
import { EntityOneComponent } from './entity-one.component';
import { EntityOneDetailComponent } from './entity-one-detail.component';
import { EntityOneUpdateComponent } from './entity-one-update.component';
import { EntityOneDeleteDialogComponent } from './entity-one-delete-dialog.component';
import { entityOneRoute } from './entity-one.route';

@NgModule({
  imports: [BlogSharedModule, RouterModule.forChild(entityOneRoute)],
  declarations: [EntityOneComponent, EntityOneDetailComponent, EntityOneUpdateComponent, EntityOneDeleteDialogComponent],
  entryComponents: [EntityOneDeleteDialogComponent]
})
export class BlogEntityOneModule {}
