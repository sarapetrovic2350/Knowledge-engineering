import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HeaderComponent } from './components/header/header.component';
import {AngularMaterialModule} from "./angular-material/angular-material.module";
import { SymptomsCausesComponent } from './components/symptoms-causes/symptoms-causes.component';
import {HttpClientModule} from "@angular/common/http";
import { PurposesComponent } from './components/purposes/purposes.component';
import { SearchRamComponent } from './components/search-ram/search-ram.component';
import { CpuSearchComponent } from './components/cpu-search/cpu-search.component';
import { SsdSearchComponent } from './components/ssd-search/ssd-search.component';
import { UpgradeComponentComponent } from './components/upgrade-component/upgrade-component.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SymptomsCausesComponent,
    PurposesComponent,
    SearchRamComponent,
    CpuSearchComponent,
    SsdSearchComponent,
    UpgradeComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
