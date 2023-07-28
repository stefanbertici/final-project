import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PlaylistDto} from "../../../../models/playlistDto";
import {Status} from "../../../../models/status";
import {PlaylistService} from "../../../../services/playlist.service";
import {ActivatedRoute} from "@angular/router";
import {PlaylistViewDto} from "../../../../models/playlistViewDto";
import {AuthService} from "../../../../services/auth.service";

@Component({
  selector: 'app-edit-playlist',
  templateUrl: './edit-playlist.component.html',
  styleUrls: ['./edit-playlist.component.scss']
})
export class EditPlaylistComponent implements OnInit {

  playlistId: number = 0;
  playlist!: PlaylistViewDto;
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private authService: AuthService,
              private playlistService: PlaylistService, private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.playlistId = params['id'];
    });
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'name': ['', Validators.required],
      'type': ['', Validators.required],
    });

    this.playlistService.getById(this.playlistId).subscribe((data: PlaylistViewDto) => {
      this.playlist = data;
      this.form.patchValue({
        name: data.name,
        type: data.type
      })
    });
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked update playlist")
    this.status = {code: 0, message: "updating..."};
    let playlistDto: PlaylistDto = {id: this.playlistId, name: this.form.value.name, type: this.form.value.type,
      createdDate: new Date(), updatedDate: new Date(), ownerUserId: this.authService.getUserId()}


    this.playlistService.update(this.playlistId, playlistDto).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Playlist updated!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
