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
import { CpuSearchComponent } from './components/cpu-search/cpu-search.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SymptomsCausesComponent,
    PurposesComponent,
    CpuSearchComponent
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
