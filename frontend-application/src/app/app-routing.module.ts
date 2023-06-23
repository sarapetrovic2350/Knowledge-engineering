import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SymptomsCausesComponent} from "./components/symptoms-causes/symptoms-causes.component";
import {PurposesComponent} from "./components/purposes/purposes.component";
import { SearchRamComponent } from './components/search-ram/search-ram.component';

const routes: Routes = [
  {
    path: 'symptoms',
    component: SymptomsCausesComponent
  },
  {
    path:'purposes',
    component: PurposesComponent
  },
  {
    path:'search-ram',
    component: SearchRamComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
