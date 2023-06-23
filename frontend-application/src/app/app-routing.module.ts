import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SymptomsCausesComponent} from "./components/symptoms-causes/symptoms-causes.component";
import {PurposesComponent} from "./components/purposes/purposes.component";
import { SearchRamComponent } from './components/search-ram/search-ram.component';
import { CpuSearchComponent } from './components/cpu-search/cpu-search.component';
import { SsdSearchComponent } from './components/ssd-search/ssd-search.component';

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
  }, 
  {
    path:'cpuSearch',
    component: CpuSearchComponent
  }, 
  {
    path:'ssdSearch',
    component: SsdSearchComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
