import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SsdService {

  apiHost: string = 'http://localhost:8080/api/ssd'

  constructor(private http: HttpClient) { }

  ssdSearch(properties: any) {

    let queryParams = new HttpParams()
    queryParams = queryParams.append("minCapacity", properties.values.minCapacity)
    queryParams = queryParams.append("maxCapacity", properties.values.maxCapacity)
    queryParams = queryParams.append("minReadSpeed", properties.values.minReadSpeed)
    queryParams = queryParams.append("maxReadSpeed", properties.values.maxReadSpeed)
    queryParams = queryParams.append("minWriteSpeed", properties.values.minWriteSpeed)
    queryParams = queryParams.append("maxWriteSpeed", properties.values.maxWriteSpeed)
    
    console.log(queryParams)

    return this.http.get<any>(this.apiHost + "/ssds-by-properties", {params:queryParams});
  }
}
