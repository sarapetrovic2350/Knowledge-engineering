import { Component, OnInit } from '@angular/core';
import { Ram } from 'src/app/models/ram.model';
import { RamService } from 'src/app/services/ram.service';

@Component({
  selector: 'app-search-ram',
  templateUrl: './search-ram.component.html',
  styleUrls: ['./search-ram.component.css']
})
export class SearchRamComponent implements OnInit {
  minFrequency: number = 0
  maxFrequency: number = 0
  minCapacity: number = 0
  maxCapacity: number = 0
  minVoltage: number = 0
  maxVoltage: number = 0

  constructor(private ramService: RamService ) { }

  isSearched: boolean = false;
  public searchResults: any[] = [];
  dataSource: any;
  displayedColumns = ['name', 'capacity', 'maxFrequency', 'voltage', 'latency', 'type', 'rgb'];
  public ram: Ram = new Ram();

  ngOnInit(): void {
  }

  searchRam(){
    let properties = {
      values: {
        minFrequency: this.minFrequency,
        maxFrequency: this.maxFrequency,
        minVoltage: this.minVoltage,
        maxVoltage: this.maxVoltage,
        minCapacity: this.minCapacity,
        maxCapacity: this.maxCapacity

      }
    }
    console.log(this.minFrequency)
    console.log(this.maxFrequency)
    console.log(properties);
    this.isSearched = true;

    this.ramService.searchRam(properties).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          this.searchResults = res;
          this.dataSource = this.searchResults
          console.log(this.searchResults)

        },
        error: (e) => {
          this.isSearched = true;
          console.log(e);
        }

      });
    
  }

  clearSearch(){
    this.minCapacity = 0
    this.maxCapacity = 0
    this.minFrequency = 0
    this.maxFrequency = 0
    this.minVoltage = 0
    this.maxVoltage = 0
    this.isSearched = false;
  }


}
