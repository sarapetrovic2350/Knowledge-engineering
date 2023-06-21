import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Cause} from "../../models/cause.model";
import {CauseService} from "../../services/cause.service";

@Component({
  selector: 'app-symptoms-causes',
  templateUrl: './symptoms-causes.component.html',
  styleUrls: ['./symptoms-causes.component.css']
})
export class SymptomsCausesComponent implements OnInit {

  constructor(private causeService: CauseService) { }
  symptoms: string = ''

  public dataSource = new MatTableDataSource<Cause>();
  public causes: Cause[] = [];
  public notFoundCauses: Cause[] = [];
  public displayedColumns = ['Name', 'Probability'];
  isSearched: boolean = false;
  notFound: boolean = false;
  ngOnInit(): void {
  }
  findCauses() {
    console.log(this.symptoms)
    this.isSearched = true;
    this.causeService.findCauses(this.symptoms).subscribe(
      {
        next: (res) => {
          this.isSearched = true;
          this.notFound = false;
          this.causes = res;
          this.dataSource.data = this.causes;
          },
        error: (e) => {
          this.notFound = true;
          this.isSearched = true;
          this.dataSource.data = this.notFoundCauses;
          console.log(e);
        }

        });

  }
  clearSearch() {
    this.symptoms = ''
    this.isSearched = false;
  }

}
