import { Injectable } from '@angular/core';

const TOKEN="token";
const USER="user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
   saveToken(token:string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
  }
   saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER,JSON.stringify(user));
  }
  getUserId():string{
    const user=this.getUser();
    if(user == null)
      return '';
    
    return user.id;
   }
   

  getToken(){
    
    return window.localStorage.getItem(TOKEN);
  }
  
   getUser(){
   
    const userString = localStorage.getItem(USER);
  if (userString) {
    return JSON.parse(userString);
  } else {
         console.log(" user is null") ;
    return null; 
  }
}

     getUserRole():string{
      const user=this.getUser();
      if(user==null)
        return "";
      
      return user.role;
    }


     isAdminLoggedIn():boolean{
      if(this.getToken()==null)return false;
      const role:string =this.getUserRole();
      return role=="ADMIN";
    }

     isCustomerLoggedIn():boolean{
      if(this.getToken()==null)return false;
      const role:string =this.getUserRole();
      return role=="CUSTOMER";
    }
     logout():void{
      localStorage.removeItem(TOKEN);
      localStorage.removeItem(USER);
    }
}
