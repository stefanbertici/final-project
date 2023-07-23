import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserViewDto} from "../../../../models/userViewDto";
import {Status} from "../../../../models/status";
import {SignupService} from "../../../../services/signup.service";
import {passwordsMatch} from "../../../../validators/passwordsMatch.validator";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  form!: FormGroup;
  userViewDto!: UserViewDto;
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
    console.log("clicked add user")
    this.status = {code: 0, message: "adding..."};

    this.signupService.signup(this.form.value).subscribe({
      next: (res) => {
        this.userViewDto = res;
        this.status.code = 1;
        this.status.message = "User created!";
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
