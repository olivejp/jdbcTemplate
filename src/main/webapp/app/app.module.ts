import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import './vendor';
import {JdbcTemplateSharedModule} from 'app/shared/shared.module';
import {JdbcTemplateCoreModule} from 'app/core/core.module';
import {JdbcTemplateAppRoutingModule} from './app-routing.module';
import {JdbcTemplateHomeModule} from './home/home.module';
import {JdbcTemplateEntityModule} from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {JhiMainComponent} from './layouts/main/main.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {FooterComponent} from './layouts/footer/footer.component';
import {PageRibbonComponent} from './layouts/profiles/page-ribbon.component';
import {ActiveMenuDirective} from './layouts/navbar/active-menu.directive';
import {ErrorComponent} from './layouts/error/error.component';
import {ChatComponent} from './chat/chat.component';
import {JhiMessageService} from "app/core/tracker/message.service";
import { VialComponent } from './vial/vial.component';

@NgModule({
  imports: [
    BrowserModule,
    JdbcTemplateSharedModule,
    JdbcTemplateCoreModule,
    JdbcTemplateHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JdbcTemplateEntityModule,
    JdbcTemplateAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent, ChatComponent, VialComponent],
  bootstrap: [JhiMainComponent],
  providers: [JhiMessageService]
})
export class JdbcTemplateAppModule {
}
