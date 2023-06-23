import { Component, OnInit } from '@angular/core';
import {PurposeService} from "../../services/purpose.service";
import {Purpose} from "../../models/purpose.model";

@Component({
  selector: 'app-purposes',
  templateUrl: './purposes.component.html',
  styleUrls: ['./purposes.component.css']
})
export class PurposesComponent {
  cpu_cores: number = 2
  cpu_threads: number = 2
  cpu_tdp: number = 5
  ram_capacity: number = 1
  ram_speed: number = 400
  ram_voltage: number = 1.2
  gpu_memory: number = 1
  gpu_clock: number = 100
  psu_power: number = 200
  hdd_capacity: number = 16

  public searchResults: any[] = [];
  public notFoundPurposes: Purpose[] = [];
  displayedColumns = ['position', 'name', 'poor', 'good', 'excellent'];


  isSearched: boolean = false;
  notFound: boolean = false;
  dataSource: any;
  constructor(private purposeService: PurposeService) { }


  findPurposes() {
    let properties = {
      values: {
        cpu_cores: this.cpu_cores,
        cpu_threads: this.cpu_threads,
        cpu_tdp: this.cpu_tdp,
        ram_capacity_in_gb: this.ram_capacity,
        ram_speed: this.ram_speed,
        ram_voltage: this.ram_voltage,
        gpu_memory_in_gb: this.gpu_memory,
        gpu_clock_in_mhz: this.gpu_clock,
        psu_output_power: this.psu_power,
        hdd_capacity_in_gb: this.hdd_capacity

      }
    }
    console.log(this.hdd_capacity)
    console.log(this.cpu_cores)
    this.isSearched = true;
    this.purposeService.findPurposes(properties).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          this.notFound = false;
          this.searchResults = res;
        const TABLE_VALUES: any[] = [
            {position: 1, name: 'App development', poor: res.appDevelopment.poor.toFixed(5)*100, good: res.appDevelopment.good.toFixed(5)*100, excellent: res.appDevelopment.excellent.toFixed(5)*100},
            {position: 2, name: 'Gaming', poor: res.gaming.poor.toFixed(5)*100, good: res.gaming.good.toFixed(5)*100, excellent: res.gaming.excellent.toFixed(5)*100},
            {position: 3, name: 'Business', poor: res.gaming.poor.toFixed(5)*100, good: res.gaming.good.toFixed(5)*100, excellent: res.gaming.excellent.toFixed(5)*100},
            {position: 4, name: 'Crypto mining', poor: res.cryptoMining.poor.toFixed(3)*100, good: res.cryptoMining.good.toFixed(5)*100, excellent: res.cryptoMining.excellent.toFixed(5)*100},
            {position: 5, name: 'Home', poor: res.home.poor.toFixed(5)*100, good: res.home.good.toFixed(5)*100, excellent: res.home.excellent.toFixed(5)*100},
            {position: 6, name: 'Hosting', poor: res.hosting.poor.toFixed(5)*100, good: res.hosting.good.toFixed(5)*100, excellent: res.hosting.excellent.toFixed(5)*100},
        ];

          this.dataSource = TABLE_VALUES;
          console.log(res.appDevelopment)
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
    this.cpu_cores = 2
    this.cpu_threads = 2
    this.cpu_tdp = 5
    this.ram_voltage = 1.2
    this.ram_capacity = 1
    this.ram_speed = 400
    this.hdd_capacity = 16
    this.psu_power = 200
    this.gpu_clock = 100
    this.gpu_memory = 1
    this.isSearched = false;
  }

}
