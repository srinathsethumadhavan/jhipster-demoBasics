import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from 'app/shared/shared.module';
import { EntityTwoComponent } from './entity-two.component';
import { EntityTwoDetailComponent } from './entity-two-detail.component';
import { EntityTwoUpdateComponent } from './entity-two-update.component';
import { EntityTwoDeleteDialogComponent } from './entity-two-delete-dialog.component';
import { entityTwoRoute } from './entity-two.route';

@NgModule({
  imports: [BlogSharedModule, RouterModule.forChild(entityTwoRoute)],
  declarations: [EntityTwoComponent, EntityTwoDetailComponent, EntityTwoUpdateComponent, EntityTwoDeleteDialogComponent],
  entryComponents: [EntityTwoDeleteDialogComponent]
})
export class BlogEntityTwoModule {}
