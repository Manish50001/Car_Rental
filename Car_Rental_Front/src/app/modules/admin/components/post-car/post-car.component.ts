import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrl: './post-car.component.scss'
})
export class PostCarComponent {
  postCarForm!: FormGroup;
  isSpinning: boolean = false;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  listOfOptions:Array<{label:string; value:string}>=[];
  listOfBrands = ["BMW", "ROLLS-ROYCE", "TATA","AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "KIA", "LEXUS"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red","Blue", "white", "Black", "Orange", "Silver", "Gray", "Purple"];
  listOfTransmission = ["Manual", "Automatic"];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    private message: NzMessageService,
    private router: Router,
    private authService: StorageService // Inject AuthService
  ) {}

  ngOnInit() {
    this.postCarForm = this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required],
    });

    // Check if user is logged in before allowing car posting (optional)
    if (!this.authService.isAdminLoggedIn()) {
      this.message.error('You need to be logged in to post a car!');
      this.router.navigate(['/login']); // Redirect to login page if not logged in
    }
  }

  postCar() {
   console.log(this.postCarForm.value);
    this.isSpinning = true;
    const formData = new FormData();
    // Append image data if a file is selected
    if (this.selectedFile) {
      formData.append('image', this.selectedFile as File);
    }
    formData.append('brand', this.postCarForm?.get('brand')?.value);
    formData.append('name', this.postCarForm?.get('name')?.value);
    formData.append('type', this.postCarForm?.get('type')?.value);
    formData.append('color', this.postCarForm?.get('color')?.value);
     formData.append('year', this.postCarForm?.get('year')?.value);
    formData.append('transmission', this.postCarForm?.get('transmission')?.value);
    formData.append('description', this.postCarForm?.get('description')?.value);
    formData.append('price', this.postCarForm?.get('price')?.value);

    this.adminService.postCar(formData).subscribe(
      
      (res) => {
        console.log("Inside Post Car method");
        this.isSpinning = false;
        this.message.success('Car Posted Successfully!', { nzDuration: 5000 });
        this.router.navigateByUrl('/admin/dashboard');
        console.log(res);
      },
      error => {
        console.log("Inside Post Car Error ");
        this.message.error('Error while Posting!', { nzDuration: 5000 });
      }
    );
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage() {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }
}
 //imagePreview: string | ArrayBuffer | null = null;

  // if (this.selectedFile) {
    //   formData.append('img', this.selectedFile as File);
    // }


  // onFileSelected(event: any) {
  //   this.selectedFile = event.target.files[0];
  //   this.previewImage();
  // }

  // previewImage() {
  //   if (this.selectedFile) {
  //     const reader = new FileReader();
  //     reader.onload = () => {
  //       this.imagePreview = reader.result;
  //     };

  //     reader.readAsDataURL(this.selectedFile);
  //   } else {
  //     console.warn('No file selected');
  //   }
  // }

//   <!-- <div nz-row>
//   <div class="profile-badge">
//     <label for="upload_profile_image" class="upload-label" *ngIf="selectedFile">
//     <img class="profile" [src]="imagePreview" alt="profile" />
//     </label>
//     <input type="file" nz-button nzType="primary" id="upload_profile_image"
//     accept="image/x-png,image/gif,image/jpeg" (change)="onFileSelected($event)" />
//    <button nz-button nzShape="circle"></button>
//   </div>
// </div> -->