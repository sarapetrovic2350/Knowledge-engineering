import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import { Ssd } from 'src/app/models/ssd.model';
import { SsdService } from 'src/app/services/ssd.service';

@Component({
  selector: 'app-ssd-search',
  templateUrl: './ssd-search.component.html',
  styleUrls: ['./ssd-search.component.css']
})
export class SsdSearchComponent implements OnInit {

  minCapacity: number = 0;
  maxCapacity: number = 0; 
  minReadSpeed: number = 0; 
  maxReadSpeed: number = 0; 
  minWriteSpeed: number = 0; 
  maxWriteSpeed: number = 0; 

  public searchResults: any[] = [];
  public notFoundPurposes: Ssd[] = [];
  public ssd: Ssd | undefined = undefined;
  displayedColumns = ['name', 'capacityInGB', 'readSpeedInMBs', 'writeSpeedInMBs', 'interfaceSSD', 'format'];


  isSearched: boolean = false;
  notFound: boolean = false;
  dataSource: any;

  constructor(private ssdService: SsdService) { }

  ngOnInit(): void {
  }

  searchSsd(){
    let properties = {
      values: {
        minCapacity: this.minCapacity,
        maxCapacity: this.maxCapacity,
        minReadSpeed: this.minReadSpeed,
        maxReadSpeed: this.maxReadSpeed,
        minWriteSpeed: this.minWriteSpeed,
        maxWriteSpeed: this.maxWriteSpeed

      }
    }

    this.isSearched = true;

    this.ssdService.ssdSearch(properties).subscribe(
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
    this.minCapacity = 0
    this.maxCapacity = 0
    this.minReadSpeed = 0
    this.maxReadSpeed = 0
    this.minWriteSpeed = 0
    this.maxWriteSpeed = 0
    this.isSearched = false;
  }

}
