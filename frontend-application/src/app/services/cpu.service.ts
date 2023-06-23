import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CpuService {

  apiHost: string = 'http://localhost:8080/api/cpu'

  constructor(private http: HttpClient) { }

  cpuSearch(properties: any) {

    let queryParams = new HttpParams()
    queryParams = queryParams.append("minCores", properties.values.minCores)
    queryParams = queryParams.append("maxCores", properties.values.maxCores)
    queryParams = queryParams.append("minThreads", properties.values.minThreads)
    queryParams = queryParams.append("maxThreads", properties.values.maxThreads)
    queryParams = queryParams.append("minClockRate", properties.values.minClockRate)
    queryParams = queryParams.append("maxClockRate", properties.values.maxClockRate)
    
    console.log(queryParams)

    return this.http.get<any>(this.apiHost + "/cpus-by-properties", {params:queryParams});
  }
}
