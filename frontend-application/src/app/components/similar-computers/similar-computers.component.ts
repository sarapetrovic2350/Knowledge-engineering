import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Ram} from "../../models/ram.model";
import {SimilarityService} from "../../services/similarity.service";
import {SimilarityEvaluation} from "../../models/similarity-evaluation.model";

@Component({
  selector: 'app-similar-computers',
  templateUrl: './similar-computers.component.html',
  styleUrls: ['./similar-computers.component.css']
})
export class SimilarComputersComponent implements OnInit {
  cpuName: string = ""
  cpuCores: number = 0
  motherboardName: string = ""
  ramCapacity: number = 0
  ramType: string = ""
  graphicCardMemory: number = 0
  hddCapacity: number = 0
  psuOutputPower: number = 0
  isSearched: boolean = false;
  public similarComputers : SimilarityEvaluation[] = new Array
  public dataSource = new MatTableDataSource<SimilarityEvaluation>();
  public displayedColumns = ['evaluation', 'cpuName', 'cpuCores', 'motherboardName', 'ramCapacity', 'ramType', 'graphicCardMemory', 'hddCapacity','psuOutputPower'];
  constructor(private similarityService: SimilarityService) { }

  ngOnInit(): void {
  }
  findSimilarComputers() {
    let properties = {
      values: {
        cpuName: this.cpuName,
        cpuCores: this.cpuCores,
        motherboardName: this.motherboardName,
        ramCapacity: this.ramCapacity,
        ramType: this.ramType,
        graphicCardMemory: this.graphicCardMemory,
        hddCapacity: this.hddCapacity,
        psuOutputPower: this.psuOutputPower
      }
    }
    this.isSearched = true;
    this.similarityService.getSimilarComputers(properties).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          console.log(res)
          this.similarComputers = res;
          this.dataSource.data = this.similarComputers;
          console.log(this.similarComputers)

        },
        error: (e) => {
          this.isSearched = true;
          console.log(e);
        }

      });

  }
  clearSearch() {
    this.cpuCores = 0
    this.ramCapacity = 0
    this.graphicCardMemory = 0
    this.hddCapacity = 0
    this.psuOutputPower = 0
    this.motherboardName = ""
    this.cpuName = ""
    this.ramType = ""
    this.isSearched = false;
  }
}
