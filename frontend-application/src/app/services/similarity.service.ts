import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SimilarityService {
  apiHost: string = 'http://localhost:8080/api/similarity/getSimilarComputers'
  constructor(private http: HttpClient) { }
  getSimilarComputers(properties: any) {
    console.log(properties.values)
    let queryParams = new HttpParams()
    queryParams = queryParams.append("cpuName", properties.values.cpuName)
    queryParams = queryParams.append("cpuCores", properties.values.cpuCores)
    queryParams = queryParams.append("motherboardName", properties.values.motherboardName)
    queryParams = queryParams.append("ramCapacity", properties.values.ramCapacity)
    queryParams = queryParams.append("ramType", properties.values.ramType)
    queryParams = queryParams.append("graphicCardMemory", properties.values.graphicCardMemory)
    queryParams = queryParams.append("hddCapacity", properties.values.hddCapacity)
    queryParams = queryParams.append("psuOutputPower", properties.values.psuOutputPower)
    console.log(queryParams)
    return this.http.get<any>(this.apiHost, {params:queryParams});
  }
}
