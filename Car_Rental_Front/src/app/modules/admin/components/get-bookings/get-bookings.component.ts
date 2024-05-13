import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { error } from 'console';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.scss'
})
export class GetBookingsComponent {
bookings:any;
  isSpinning=false;
  
  constructor(private adminService:AdminService,private message:NzMessageService){
    this.getBookings();
  }

  getBookings(){
    this.isSpinning=true;
    this.adminService.getCarBookings().subscribe((res)=>{
      this.isSpinning=false;
   console.log(res);
this.bookings=res;
    })
  }
  changeBookingStatus(bookingsId:number,bookingCarStatus:string){
    this.isSpinning=true;
    console.log(bookingsId,bookingCarStatus);

    this.adminService.changeBookingStatus(bookingsId,bookingCarStatus).subscribe((res)=>{
      this.isSpinning=false;
      console.log(res);
      this.getBookings();
      this.message.success("Booking Status Changed SuccessFully",{nzDuration:5000});

    },error=>{
      this.message.error("Something Went Wrong",{nzDuration:5000});
    })
  }
}
