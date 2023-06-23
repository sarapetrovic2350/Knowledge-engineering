import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RamService {

  apiHost: string = 'http://localhost:8080/api/ram/rams-by-properties'
  constructor(private http: HttpClient) { }
  searchRam(properties: any) {
    console.log(properties.values)
    let queryParams = new HttpParams()
    queryParams = queryParams.append("minCapacity", properties.values.minCapacity)
    queryParams = queryParams.append("maxCapacity", properties.values.maxCapacity)
    queryParams = queryParams.append("minFrequency", properties.values.minFrequency)
    queryParams = queryParams.append("maxFrequency", properties.values.maxFrequency)
    queryParams = queryParams.append("minVoltage", properties.values.minVoltage)
    queryParams = queryParams.append("maxVoltage", properties.values.maxVoltage)
    console.log(queryParams)
    return this.http.get<any>(this.apiHost, {params:queryParams});
  }
}
