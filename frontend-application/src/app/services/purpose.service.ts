import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PurposeService {
  apiHost: string = 'http://localhost:8080/api/fuzzy/purpose'
  constructor(private http: HttpClient) { }
  findPurposes(properties: any) {
    console.log(properties.values.cpu_cores)
    let queryParams = new HttpParams()
    queryParams = queryParams.append("cpu_cores", properties.values.cpu_cores)
    queryParams = queryParams.append("cpu_threads", properties.values.cpu_threads)
    queryParams = queryParams.append("cpu_tdp", properties.values.cpu_tdp)
    queryParams = queryParams.append("ram_capacity_in_gb", properties.values.ram_capacity_in_gb)
    queryParams = queryParams.append("ram_speed", properties.values.ram_speed)
    queryParams = queryParams.append("ram_voltage", properties.values.ram_voltage)
    queryParams = queryParams.append("gpu_memory_in_gb", properties.values.gpu_memory_in_gb)
    queryParams = queryParams.append("gpu_clock_in_mhz", properties.values.gpu_clock_in_mhz)
    queryParams = queryParams.append("psu_output_power", properties.values.psu_output_power)
    queryParams = queryParams.append("hdd_capacity_in_gb", properties.values.hdd_capacity_in_gb)
    console.log(queryParams)
    return this.http.get<any>(this.apiHost, {params:queryParams});
  }
}
