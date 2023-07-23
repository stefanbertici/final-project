import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {SongDto} from "../../../../models/songDto";
import {SongService} from "../../../../services/song.service";

@Component({
  selector: 'app-add-song',
  templateUrl: './add-song.component.html',
  styleUrls: ['./add-song.component.scss']
})
export class AddSongComponent implements OnInit {

  song!: SongDto;
  form!: FormGroup;
  status!: Status;

  constructor(private songService: SongService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'title': ['', Validators.required],
      'duration': ['', Validators.required],
      'createdDate': ['', Validators.required]
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add song")
    this.status = {code: 0, message: "adding"};

    this.songService.create(this.form.value).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Song added!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
