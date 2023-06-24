import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UpgradeService {

  
  apiHost: string = 'http://localhost:8080/api/motherboard/upgrades'
  constructor(private http: HttpClient) { }
  searchForUpgradeComponent(properties: any) {
    console.log(properties.values)
    let queryParams = new HttpParams()
    queryParams = queryParams.append("component", properties.values.component)
    queryParams = queryParams.append("motherboard", properties.values.motherboard)
    queryParams = queryParams.append("ram", properties.values.RAM)
    queryParams = queryParams.append("cpu", properties.values.CPU)
    queryParams = queryParams.append("ssd", properties.values.SDD)
    console.log(queryParams)
    return this.http.get<any>(this.apiHost, {params:queryParams});
  }
}
