import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CauseService {
  apiHost: string = 'http://localhost:8080/api/bayes/computerSymptoms'
  constructor(private http: HttpClient) { }
  findCauses(allSymptoms: any) {
    let queryParams = new HttpParams()
    queryParams = queryParams.append("computerSymptoms", allSymptoms)
    return this.http.get<any[]>(this.apiHost, {params:queryParams});
  }

}
