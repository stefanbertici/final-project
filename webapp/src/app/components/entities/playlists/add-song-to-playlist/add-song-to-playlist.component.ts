import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PlaylistService} from "../../../../services/playlist.service";
import {PlaylistDto} from "../../../../models/playlistDto";
import {AuthService} from "../../../../services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";

@Component({
  selector: 'app-add-song-to-playlist',
  templateUrl: './add-song-to-playlist.component.html',
  styleUrls: ['./add-song-to-playlist.component.scss']
})
export class AddSongToPlaylistComponent implements OnInit {

  songId: number = 0;
  ownedPlaylists: PlaylistDto[] = [];
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private authService: AuthService,
              private playlistService: PlaylistService, private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.songId = params['id'];
    });
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'playlistId': ['', Validators.required],
    });

    this.playlistService.getAll().subscribe((data: PlaylistDto[]) => {
      this.ownedPlaylists = [];

      for (const playlist of data) {
        if (playlist.ownerUserId === this.authService.getUserId()) {
          this.ownedPlaylists.push(playlist);
        }
      }
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add song to playlist")
    this.status = {code: 0, message: "updating"};

    this.playlistService.addSong(this.form.value.playlistId, this.songId).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Song added to playlist!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
