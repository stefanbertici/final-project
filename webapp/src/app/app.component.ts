import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  role?: string;
  isLoggedIn!: boolean;

  constructor(private router: Router, private authService: AuthService) {
  }

  checkLoggedInUser() {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.role = this.authService.getUserRole();
  }

  getUserFullName() {
    return this.authService.getFullName();
  }

  getUserId() {
    return this.authService.getUserId();
  }

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.authService.removeAllSavedData();
        this.router.navigate(['./login'])
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
      }
    })
  }
}
