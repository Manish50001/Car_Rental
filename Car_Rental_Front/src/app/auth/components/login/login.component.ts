import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { HttpClient } from '@angular/common/http';
import { StorageService } from '../../services/storage/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  isSpinning:boolean=false;
  loginForm!:FormGroup;
  constructor(private fb:FormBuilder
    ,private authService:AuthService,private message:NzMessageService, private router:Router,
    private storageService:StorageService){}
     ngOnInit(){
        this.loginForm=this.fb.group({
          email:[null,[Validators.email,Validators.required]],
          password:[null,[Validators.required]]
        })
     }   

login(){

  console.log(this.loginForm.value);

  this.authService.login(this.loginForm.value).subscribe((res)=>{
    console.log(res);
  if(res.userId!=null){
    const user={
      id:res.userId,
      role:res.userRole

    }
    this.storageService.saveUser(user);
    this.storageService.saveToken(res.jwt);
    if(this.storageService.isAdminLoggedIn()){
      this.router.navigateByUrl("/admin/dashboard");
    }else if(this.storageService.isCustomerLoggedIn()){
      this.router.navigateByUrl("/customer/dashboard");
    }else{
      this.message.error("Bad Credentials",{nzDuration:50000});
    }
  }

  })
 
}
}