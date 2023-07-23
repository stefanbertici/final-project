import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {UserService} from "../../../../services/user.service";
import {ActivatedRoute} from "@angular/router";
import {passwordsMatch} from "../../../../validators/passwordsMatch.validator";
import {UserViewDto} from "../../../../models/userViewDto";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent {

  form!: FormGroup;
  userId: number = 0;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
              private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.userId = params['id'];
    });
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
    console.log("clicked update user")
    this.status = {code: 0, message: "updating"};

    this.userService.update(this.userId, this.form.value).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "User updated!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
