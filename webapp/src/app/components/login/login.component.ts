import {Component, OnInit} from '@angular/core';
import {SignupService} from "../../services/signup.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../models/status";
import {patternMatch} from "../../validators/patternMatch.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  status!: Status;
  userEmail!: string;
  token!: string;

  constructor(private signupService: SignupService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    // must be at least 6 character long, must contain 1 uppercase, 1 lowercase, 1 digit and 1 special character
    const pattern = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*[#$^+=!*()@%&]).{6,}$');

    this.form = this.formBuilder.group({
      'email': ['', Validators.required],
      'password': ['', [Validators.required, patternMatch(pattern)]],
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    this.status = {code: 0, message: "logging in..."};

    this.signupService.login(this.form.value).subscribe({
      next: (res) => {
        this.userEmail = this.form.controls["email"].value;
        this.token = res;
        this.status.code = 1;
        this.status.message = "logged in successfully!";
        this.form.reset();

        console.log(this.userEmail);
        console.log(this.token);
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "it looks like there was an error :(";
      }
    })
  }
}
