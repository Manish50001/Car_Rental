import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const Base_Url=["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor( private http:HttpClient, private storageService:StorageService) { }

  postCar(carDto:any):Observable<any>{
    return this.http.post(Base_Url + "/api/admin/car", carDto,{

      headers:this.createAuthorizationHeader()

    });
         
  }

  getAllCars():Observable<any>{
    return this.http.get(Base_Url + "/api/admin/cars",{
      headers:this.createAuthorizationHeader()
    })

  }
    
  deleteCar(id:number):Observable<any>{
     return this.http.delete(Base_Url + "/api/admin/car/"+ id,{
      headers:this.createAuthorizationHeader()
     });
  }

  updateCar(carId:number,carDto:any):Observable<any>{
    return this.http.put(Base_Url + "/api/admin/car/" + carId ,carDto,{
      
      headers:this.createAuthorizationHeader()
    });

  }
  getCarBookings():Observable<any>{
    return this.http.get(Base_Url + "/api/admin/car/bookings",{
      headers:this.createAuthorizationHeader()
    })

  }
  changeBookingStatus(bookingId:number,status:string):Observable<any>{
    return this.http.get(Base_Url + `/api/admin/car/booking/${bookingId}/${status}`,{
      headers:this.createAuthorizationHeader()
    })

  }
 
  getCarById(id:number):Observable<any>{
    return this.http.get(Base_Url + "/api/admin/car/" + id, {
      headers:this.createAuthorizationHeader()
    });

  }
  searchCar(searchCarDto:any):Observable<any>{
    return this.http.post(Base_Url + "/api/admin/car/search", searchCarDto,{

      headers:this.createAuthorizationHeader()

    });
         
  }
  createAuthorizationHeader():HttpHeaders{
    let authHeader:HttpHeaders=new HttpHeaders();
    return authHeader.set(
      'Authorization',
      'Bearer ' + this.storageService.getToken()
    );

  }
  
}
