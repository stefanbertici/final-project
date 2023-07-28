import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ArtistDto} from "../../../../models/artistDto";
import {Status} from "../../../../models/status";
import {ActivatedRoute} from "@angular/router";
import {ArtistService} from "../../../../services/artist.service";

@Component({
  selector: 'app-edit-artist',
  templateUrl: './edit-artist.component.html',
  styleUrls: ['./edit-artist.component.scss']
})
export class EditArtistComponent implements OnInit {

  artistId: number = 0;
  artistDto!: ArtistDto;
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private artistService: ArtistService,
              private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.artistId = params['id'];
    });
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

    this.artistService.getById(this.artistId).subscribe((data: ArtistDto) => {
      this.artistDto = data;
      this.form.patchValue({
        firstName: data.firstName,
        lastName: data.lastName,
        stageName: data.stageName,
        birthday: data.birthday,
        activityStartDate: data.activityStartDate,
        activityEndDate: data.activityEndDate
      })
    });
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked update artist")
    this.status = {code: 0, message: "updating..."};

    this.artistService.update(this.artistId, this.form.value).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Artist updated!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
