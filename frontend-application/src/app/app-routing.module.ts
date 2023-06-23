import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SymptomsCausesComponent} from "./components/symptoms-causes/symptoms-causes.component";
import {PurposesComponent} from "./components/purposes/purposes.component";
import { CpuSearchComponent } from './components/cpu-search/cpu-search.component';

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
    path:'cpuSearch',
    component: CpuSearchComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
