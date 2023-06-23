import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import { Cpu } from 'src/app/models/cpu.model';
import { CpuService } from 'src/app/services/cpu.service';

@Component({
  selector: 'app-cpu-search',
  templateUrl: './cpu-search.component.html',
  styleUrls: ['./cpu-search.component.css']
})
export class CpuSearchComponent implements OnInit {

  minCores: number = 0;
  maxCores: number = 0; 
  minThreads: number = 0; 
  maxThreads: number = 0; 
  minClockRate: number = 0; 
  maxClockRate: number = 0; 

  public searchResults: any[] = [];
  public notFoundPurposes: Cpu[] = [];
  public cpu: Cpu | undefined = undefined;
  displayedColumns = ['cpuNames', 'cpuCores', 'cpuClockRate', 'cpuGaming', 'cpuTDP', 'cpuThreads', 'cpuType', 'cpuIntegratedGPU'];


  isSearched: boolean = false;
  notFound: boolean = false;
  dataSource: any;
  constructor(private cpuService: CpuService) { }

  ngOnInit(): void {
  }

  searchCpu(){
    let properties = {
      values: {
        minCores: this.minCores,
        maxCores: this.maxCores,
        minThreads: this.minThreads,
        maxThreads: this.maxThreads,
        minClockRate: this.minClockRate,
        maxClockRate: this.maxClockRate

      }
    }

    this.isSearched = true;

    this.cpuService.cpuSearch(properties).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          this.notFound = false;
          this.searchResults = res;
        // const TABLE_VALUES: any[] = [
        //     {position: 1, name: 'App development', poor: res.appDevelopment.poor.toFixed(5)*100, good: res.appDevelopment.good.toFixed(5)*100, excellent: res.appDevelopment.excellent.toFixed(5)*100},
        //     {position: 2, name: 'Gaming', poor: res.gaming.poor.toFixed(5)*100, good: res.gaming.good.toFixed(5)*100, excellent: res.gaming.excellent.toFixed(5)*100},
        //     {position: 3, name: 'Business', poor: res.gaming.poor.toFixed(5)*100, good: res.gaming.good.toFixed(5)*100, excellent: res.gaming.excellent.toFixed(5)*100},
        //     {position: 4, name: 'Crypto mining', poor: res.cryptoMining.poor.toFixed(3)*100, good: res.cryptoMining.good.toFixed(5)*100, excellent: res.cryptoMining.excellent.toFixed(5)*100},
        //     {position: 5, name: 'Home', poor: res.home.poor.toFixed(5)*100, good: res.home.good.toFixed(5)*100, excellent: res.home.excellent.toFixed(5)*100},
        //     {position: 6, name: 'Hosting', poor: res.hosting.poor.toFixed(5)*100, good: res.hosting.good.toFixed(5)*100, excellent: res.hosting.excellent.toFixed(5)*100},
        // ];

        //   this.dataSource = TABLE_VALUES;
        this.dataSource = res; 
          //console.log(res.appDevelopment)
        },
        error: (e) => {
          this.notFound = true;
          this.isSearched = true;
          //this.dataSource.data = this.notFoundCauses;
          console.log(e);
        }

      });
  }

  clearSearch() {
    this.minCores = 0
    this.maxCores = 0
    this.minThreads = 0
    this.maxThreads = 0
    this.minClockRate = 0
    this.maxClockRate = 0
    this.isSearched = false;
  }

}
