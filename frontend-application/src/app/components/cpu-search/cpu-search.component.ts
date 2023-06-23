import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import { Cpu } from 'src/app/models/cpu.model';
import { CpuService } from 'src/app/services/cpu.service';
import {Ram} from "../../models/ram.model";

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

  public cpus: Cpu[] = [];
  public notFoundCpus: Cpu[] = [];
  displayedColumns = ['cpuNames', 'cpuCores', 'cpuClockRate', 'cpuGaming', 'cpuTDP', 'cpuThreads', 'cpuType', 'cpuIntegratedGPU'];


  isSearched: boolean = false;
  notFound: boolean = false;
  public dataSource = new MatTableDataSource<Cpu>();
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
          this.cpus = res;
          this.dataSource.data = this.cpus;

          if(this.cpus.length == 0) {
            this.notFound = true;
            this.dataSource.data = this.notFoundCpus;
          }
        },
        error: (e) => {
          this.isSearched = true;
          this.notFound = true;
          this.dataSource.data = this.notFoundCpus;
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
