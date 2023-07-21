import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../models/status";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  status!: Status;

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'email': ['', Validators.required],
      'password': ['', Validators.required],
    })

    if (this.authService.isLoggedIn()) {
      this.router.navigate(['./dashboard']);
    }
  }

  onPost() {
    this.status = {code: 0, message: "logging in..."};

    this.authService.login(this.form.value).subscribe({
      next: (res) => {
        this.authService.addFullName(res.fullName);
        this.authService.addAccessToken(res.accessToken);
        this.status.code = 1;
        this.status.message = "logged in!";
        this.router.navigate(['./dashboard']);
        },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }

  get getFormControl() {
    return this.form.controls;
  }
}
