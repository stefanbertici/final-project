import {Component, OnInit} from '@angular/core';
import {SignupService} from "../../services/signup.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {patternMatch} from "../../validators/patternMatch.validator";
import {passwordsMatch} from "../../validators/passwordsMatch.validator";
import {Status} from "../../models/status";
import {UserViewDTO} from "../../models/userViewDTO";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  form!: FormGroup;
  userViewDTO!: UserViewDTO;
  status!: Status;

  constructor(private signupService: SignupService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    // must be at least 6 character long,must contain 1 uppercase, 1 lowercase, 1 digit and 1 special character
    const pattern = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*[#$^+=!*()@%&]).{6,}$');

    this.form = this.formBuilder.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'email': ['', Validators.required],
      'country': ['', Validators.required],
      'password': ['', Validators.required],  //patternMatch(pattern) -> also add this if needed
      'confirmPassword': ['', Validators.required],
    }, {
      validator: passwordsMatch('password', 'confirmPassword')
    })
  }

  onPost() {
    console.log("clicked signup")
    this.status = {code: 0, message: "registering..."};

    this.signupService.signup(this.form.value).subscribe({
      next: (res) => {
        this.userViewDTO = res;
        this.status.code = 1;
        this.status.message = "registered successfully!";
        this.form.reset();
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "it looks like there was an error :(";
      }
    })
  }

  get getFormControl() {
    return this.form.controls;
  }
}
