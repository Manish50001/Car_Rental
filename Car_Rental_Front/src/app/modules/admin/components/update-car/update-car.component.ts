import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  isSpinning=false;
  carId: number=this.activatedRouter.snapshot.params["id"];
  imgChanged: boolean=false;
  selectedFile:any;
  imagePreview:string|ArrayBuffer  |null=null;
  existingImage: string| null=null;
  updateForm!:FormGroup;
  listOfOptions:Array<{label:string; value:string}>=[];
  listOfBrands = ["BMW", "ROLLS-ROYCE","TATA", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "KIA", "LEXUS"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red","Blue", "white", "Black", "Orange", "Silver", "Gray", "Purple"];
  listOfTransmission = ["Manual", "Automatic"];

  constructor(private adminService:AdminService,private activatedRouter:ActivatedRoute,
    private fb:FormBuilder,private message:NzMessageService,
    private router:Router){

  }
  ngOnInit(){
   
    this.updateForm=this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required],
    })
    this.getCarById();
  }
  getCarById(){
    this.isSpinning=true;
    this.adminService.getCarById(this.carId).subscribe((res)=>{
     // console.log(res);
      this.isSpinning=false;
      const  carDto=res;
      this.existingImage= 'data:image/jpeg;base64,'+ res.returnedImage;
      console.log(carDto);
      console.log(this.existingImage);
      this.updateForm.patchValue(carDto);
    })
  }
  
  updateCar(){
    this.isSpinning = true;
    const formData = new FormData();
    // Append image data if a file is selected
    if (this.imgChanged && this.selectedFile) {
      formData.append('image', this.selectedFile as File);
    }
    formData.append('brand', this.updateForm?.get('brand')?.value);
    formData.append('name', this.updateForm?.get('name')?.value);
    formData.append('type', this.updateForm?.get('type')?.value);
    formData.append('color', this.updateForm?.get('color')?.value);
     formData.append('year', this.updateForm?.get('year')?.value);
    formData.append('transmission', this.updateForm?.get('transmission')?.value);
    formData.append('description', this.updateForm?.get('description')?.value);
    formData.append('price', this.updateForm?.get('price')?.value);
   console.log(formData);
    this.adminService.updateCar(this.carId, formData).subscribe((res) => {
    
       
        this.isSpinning = false;
        this.message.success('Car Updated Successfully!', { nzDuration: 5000 });
        this.router.navigateByUrl('/admin/dashboard');
        console.log(res);
      },
      error => {
        console.log("Inside Update Car Error ");
        this.message.error('Error while Updating Car!', { nzDuration: 5000 });
      }
    );
  }




  onFileSelected(event:any){
    this.selectedFile=event.target.files[0];
    this.imgChanged=true;
this.existingImage=null;
this.previewImage();
  }

  previewImage(){
    const reader=new FileReader();
    reader.onload=()=>{
      this.imagePreview=reader.result;
    }
    reader.readAsDataURL(this.selectedFile);

  }
}
