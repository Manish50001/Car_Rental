import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const Base_Url=["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient,private storageService:StorageService) { }

  getAllCars():Observable<any>{
    return this.http.get(Base_Url + "/api/customer/cars",{
      headers:this.createAuthorizationHeader()
    })

  }
  getCarById(carId:number):Observable<any>{
    return this.http.get(Base_Url + "/api/customer/car/" + carId,{
      headers:this.createAuthorizationHeader()
    })
  }

  bookACar(carId:any,bookCarDto:any):Observable<any>{
    return this.http.post<[]>(Base_Url + `/api/customer/car/book/$(carId)`, bookCarDto, {
      headers: this.createAuthorizationHeader()
    })
  }
  searchCar(searchCarDto:any):Observable<any>{
    return this.http.post(Base_Url + "/api/customer/car/search", searchCarDto,{

      headers:this.createAuthorizationHeader()

    });       
  }

  getBookingsByUserId():Observable<any>{
    return this.http.get(Base_Url + "/api/customer/car/bookings/" + this.storageService.getUserId(), {
      headers:this.createAuthorizationHeader()
    })
  }
  
  createAuthorizationHeader():HttpHeaders{
    let authHeader:HttpHeaders=new HttpHeaders();
    return authHeader.set(
      'Authorization',
      'Bearer ' + this.storageService.getToken()
    );

  }
}
