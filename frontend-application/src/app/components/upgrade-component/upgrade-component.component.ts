import { Component, OnInit } from '@angular/core';
import { UpgradeService } from 'src/app/services/upgrade.service';

@Component({
  selector: 'app-upgrade-component',
  templateUrl: './upgrade-component.component.html',
  styleUrls: ['./upgrade-component.component.css']
})
export class UpgradeComponentComponent implements OnInit {

  constructor(private upgradeComponenService: UpgradeService) { }

  motherboard: string = ""
  CPU: string = ""
  SSD: string = ""
  RAM: string = ""
  chosedComponent: number = 0

  isSearched: boolean = false;
  notFound: boolean = false;
  isRAM: boolean = false;
  isCPU: boolean = false;
  isSSD: boolean = false;
  component: string = ""
  public searchResults: any[] = [];
  public notFounfSearchResults: any[] = [];
  dataSource: any;
  public displayedColumnsRAM = ['name', 'capacity', 'maxFrequency', 'voltage', 'latency', 'type', 'rgb'];
  public displayedColumnsCPU = ['cpuNames', 'cpuCores', 'cpuClockRate', 'cpuGaming', 'cpuTDP', 'cpuThreads', 'cpuType', 'cpuIntegratedGPU'];
  public displayedColumnsSSD = ['name', 'capacityInGB', 'readSpeedInMBs', 'writeSpeedInMBs', 'interfaceSSD', 'format'];

  ngOnInit(): void {
  }

  searchForUpgrade(){
    if (this.chosedComponent == 0){
      this.isCPU = true
      this.isRAM = false
      this.isSSD = false
      this.component = "cpu"
    }if (this.chosedComponent == 1) {
      this.isRAM = true
      this.isCPU = false
      this.isSSD = false
      this.component = "ram"
    } if(this.chosedComponent == 2) {
      this.isSSD = true
      this.isRAM = false
      this.isCPU = false
      this.component = "ssd"
    }
    let properties = {
      values: {
        motherboard: this.motherboard,
        CPU: this.CPU,
        RAM: this.RAM,
        SDD: this.SSD,
        component: this.component,
      }
    }
    console.log(this.CPU)
    console.log(this.chosedComponent)
    console.log(properties);
    this.isSearched = true;

    this.upgradeComponenService.searchForUpgradeComponent(properties).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          this.notFound = false;
          this.searchResults = res;
          console.log(res)
          this.dataSource = this.searchResults
          if(this.searchResults.length == 0) {
            this.notFound = true;
            this.dataSource = this.notFounfSearchResults;
          }
          console.log(this.searchResults)

        },
        error: (e) => {
          this.isSearched = true;
          this.notFound = true;
          this.dataSource = this.notFounfSearchResults;
          console.log(e);
        }

      });


  }

  clearSearch(){
    this.motherboard = ""
    this.CPU = ""
    this.SSD = ""
    this.RAM = ""
    this.chosedComponent = 0
    this.isSearched = false
    this.notFound = false
    this.isRAM = false
    this.isCPU = false
    this.isSSD = false

  }

}
