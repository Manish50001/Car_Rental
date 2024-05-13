import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
const Base_Url=["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private  http:HttpClient) { }

  register(signupRequest:any):Observable<any>{
    
    return this.http.post(Base_Url+ "/api/auth/signup", signupRequest);
     
  }
  login(loginRequest:any):Observable<any>{
    
    return this.http.post(Base_Url+"/api/auth/login",loginRequest);

  }
}
