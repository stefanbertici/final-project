import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {ArtistDto} from "../../../../models/artistDto";
import {AuthService} from "../../../../services/auth.service";
import {ArtistService} from "../../../../services/artist.service";

@Component({
  selector: 'app-add-artist',
  templateUrl: './add-artist.component.html',
  styleUrls: ['./add-artist.component.scss']
})
export class AddArtistComponent implements OnInit {

  form!: FormGroup;
  artistDto!: ArtistDto;
  status!: Status;

  constructor(private authService: AuthService, private artistService: ArtistService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'stageName': ['', Validators.required],
      'birthday': ['', Validators.required],
      'activityStartDate': ['', Validators.required],
      'activityEndDate': ['', Validators.required],
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add artist")
    this.status = {code: 0, message: "adding..."};

    this.artistService.add(this.form.value).subscribe({
      next: (res) => {
        this.artistDto = res;
        this.status.code = 1;
        this.status.message = "Artist created!";
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
