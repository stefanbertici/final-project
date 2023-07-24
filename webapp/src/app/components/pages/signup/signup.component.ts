import {Component, OnInit} from '@angular/core';
import {SignupService} from "../../../services/signup.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {passwordsMatch} from "../../../validators/passwordsMatch.validator";
import {Status} from "../../../models/status";
import {UserViewDto} from "../../../models/userViewDto";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  form!: FormGroup;
  user!: UserViewDto;
  status!: Status;

  constructor(private signupService: SignupService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'email': ['', Validators.required],
      'country': ['', Validators.required],
      'password': ['', Validators.required],
      'confirmPassword': ['', Validators.required],
    }, {
      validator: passwordsMatch('password', 'confirmPassword')
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked signup")
    this.status = {code: 0, message: "registering..."};

    this.signupService.signup(this.form.value).subscribe({
      next: (res) => {
        this.user = res;
        this.status.code = 1;
        this.status.message = "Registered!";
        this.form.reset();
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
