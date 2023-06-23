import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import { Ssd } from 'src/app/models/ssd.model';
import { SsdService } from 'src/app/services/ssd.service';
import {Cpu} from "../../models/cpu.model";

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

  public SSDs: Ssd[] = [];
  public notFoundSSDs: Ssd[] = [];
  public ssd: Ssd | undefined = undefined;
  displayedColumns = ['name', 'capacityInGB', 'readSpeedInMBs', 'writeSpeedInMBs', 'interfaceSSD', 'format'];


  isSearched: boolean = false;
  notFound: boolean = false;
  public dataSource = new MatTableDataSource<Ssd>();

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
          this.SSDs = res;
          this.dataSource.data = this.SSDs;

          if(this.SSDs.length == 0) {
            this.notFound = true;
            this.dataSource.data = this.notFoundSSDs;
          }
        },
        error: (e) => {
          this.notFound = true;
          this.isSearched = true;
          this.dataSource.data = this.notFoundSSDs;
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
