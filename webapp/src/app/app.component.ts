import {Component} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {SignupService} from "./services/signup.service";
import {Router} from "@angular/router";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  isLoggedIn!: boolean;

  constructor(
    private modalService: NgbModal,
    private signupService: SignupService,
    private router: Router,
    private authService: AuthService) {
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  checkLoggedInUser() {
    this.isLoggedIn = this.authService.isLoggedIn();
  }

  logout() {
    this.signupService.logout().subscribe({
      next: (res) => {
        console.log(res);
        this.authService.removeAllSavedData();
        this.router.navigate(['./login'])
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
      }
    })
  }
}
