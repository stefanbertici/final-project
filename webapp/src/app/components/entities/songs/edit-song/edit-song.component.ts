import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {ActivatedRoute} from "@angular/router";
import {SongViewDto} from "../../../../models/songViewDto";
import {SongService} from "../../../../services/song.service";

@Component({
  selector: 'app-edit-song',
  templateUrl: './edit-song.component.html',
  styleUrls: ['./edit-song.component.scss']
})
export class EditSongComponent implements OnInit {

  songId: number = 0;
  song!: SongViewDto;
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private songService: SongService,
              private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.songId = params['id'];
    });
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'title': ['', Validators.required],
      'duration': ['', Validators.required],
      'createdDate': ['', Validators.required]
    })

    this.songService.getById(this.songId).subscribe((data: SongViewDto) => {
      this.song = data;
      this.form.patchValue({
        title: data.title,
        duration: data.duration,
        createdDate: data.createdDate,
      })
    });
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked update song")
    this.status = {code: 0, message: "updating..."};

    this.songService.update(this.songId, this.form.value).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Song updated!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
