import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';

import { NavigationEnd, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { StorageService } from './auth/services/storage/storage.service';




@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'car_rental';

 
  isCustomerLoggedIn: boolean = false; // Initialize to a default value
  isAdminLoggedIn: boolean = false;

  constructor(private router: Router, private storageService: StorageService,
    @Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) { // Check for browser environment
      this.isCustomerLoggedIn = this.storageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = this.storageService.isAdminLoggedIn();
    }

    // Subscribe to navigation events for potential updates (optional)
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && isPlatformBrowser(this.platformId)) {
        this.isCustomerLoggedIn = this.storageService.isCustomerLoggedIn();
        this.isAdminLoggedIn = this.storageService.isAdminLoggedIn();
      }
    });
  }

  // logout implementation (assuming you have a logout method in StorageService)
  logout() {
    this.storageService.logout();
    this.isCustomerLoggedIn = false;
    this.isAdminLoggedIn = false;
    this.router.navigateByUrl('/login');
  }
}
