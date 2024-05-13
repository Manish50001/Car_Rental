import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrl: './book-car.component.scss'
})
export class BookCarComponent {
  carId:number=this.activateRoute.snapshot.params["id"];
  car:any;
  processedImage:any;
  validateForm!:FormGroup;
  isSpinning=false;
  dateFormat!:"DD-MM-YYYY";
  constructor(private service:CustomerService, 
    private activateRoute:ActivatedRoute ,private fb:FormBuilder
    ,private storage:StorageService,private message:NzMessageService,
    private router:Router){}

    ngOnInit(){
      this.validateForm=this.fb.group({
        toDate:[null,Validators.required],
        fromDate:[null,Validators.required],
      })
     this.getCarById();
    }





    
    getCarById(){
         this.service.getCarById(this.carId).subscribe((res)=>{
          console.log(res);
          this.processedImage= 'data:image/jpeg; base64,' + res.returnedImage;
        this.car=res;
         })
    }
    bookACar(data:any){
      console.log(data);
      this.isSpinning=true;
      let bookACarDto={
        toDate: data.toDate,
        fromDate:data.fromDate,
        userId:this.storage.getUserId(),
        carId:this.carId
      }
      this.service.bookACar(this.carId,bookACarDto).subscribe((res)=>{
        console.log(res);
        this.message.success("Booking Request Sended Successfully",{nzDuration:5000});
        this.router.navigateByUrl("/customer/dashboard")
      }, error=>{
        this.message.error("Booking is Failed",{nzDuration:5000});
      }
      )
    }
  }
