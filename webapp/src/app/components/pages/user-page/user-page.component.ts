import {Component, OnInit} from '@angular/core';
import {UserViewDto} from "../../../models/userViewDto";
import {UserService} from "../../../services/user.service";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {passwordsMatch} from "../../../validators/passwordsMatch.validator";
import {Status} from "../../../models/status";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-user',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent implements OnInit {

  form!: FormGroup;
  userId: number = 0;
  status!: Status;

  constructor(private authService: AuthService, private userService: UserService, private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder) {
    this.userId = this.authService.getUserId();
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

    this.userService.getById(this.userId).subscribe((data: UserViewDto) => {
      this.form.patchValue({
        id: this.userId,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        country: data.country
      })
    });
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked save changes")
    this.status = {code: 0, message: "updating..."};

    this.userService.update(this.userId, this.form.value).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "User info updated!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
