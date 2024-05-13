import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomersRoutingModule } from './customers-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { BookCarComponent } from './components/book-car/book-car.component';
import { NgZorroImportModule } from '../../NgZorroImportModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';
import { SearchCarComponent } from './components/search-car/search-car.component';

@NgModule({
  declarations: [
    CustomerDashboardComponent,
    BookCarComponent,
    MyBookingsComponent,
    SearchCarComponent
  ],
  imports: [
    CommonModule,
    CustomersRoutingModule,
    NgZorroImportModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CustomersModule { }
