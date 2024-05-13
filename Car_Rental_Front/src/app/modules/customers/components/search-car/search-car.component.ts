import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.scss'
})
export class SearchCarComponent {
  searchCarForm!:FormGroup;
  listOfOption:Array<{label:string; value:string}>=[];
  listOfBrands = ["BMW", "ROLLS-ROYCE", "TATA","AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "KIA", "LEXUS"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red","Blue", "white", "Black", "Orange", "Silver", "Gray", "Purple"];
  listOfTransmission = ["Manual", "Automatic"];
  isSpinning=false;
  cars:any=[];
  constructor(private fb:FormBuilder,private service:CustomerService){
    this.searchCarForm=this.fb.group({
      brand:[null],
      type:[null],
      transmission:[null],
      color:[null],
    })
  }

  SearchCar(){
    this.isSpinning=true;
    console.log(this.searchCarForm.value);
    this.service.searchCar(this.searchCarForm.value).subscribe((res)=>{

     
      res.carDtoList.forEach((element: { processesImg: string; returnedImage: string; }) => {
        element.processesImg= 'data:image/jpeg; base64,' + element.returnedImage;
        this.cars.push(element);
      }); 
      this.isSpinning=false;
    })
  }
}


