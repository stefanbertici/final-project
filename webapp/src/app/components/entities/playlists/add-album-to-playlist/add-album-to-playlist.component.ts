import {Component, OnInit} from '@angular/core';
import {PlaylistDto} from "../../../../models/playlistDto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../../services/auth.service";
import {PlaylistService} from "../../../../services/playlist.service";

@Component({
  selector: 'app-add-album-to-playlist',
  templateUrl: './add-album-to-playlist.component.html',
  styleUrls: ['./add-album-to-playlist.component.scss']
})
export class AddAlbumToPlaylistComponent implements OnInit {

  albumId: number = 0;
  ownedPlaylists: PlaylistDto[] = [];
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private authService: AuthService,
              private playlistService: PlaylistService, private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.albumId = params['id'];
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
    console.log("clicked add album to playlist")
    this.status = {code: 0, message: "adding..."};

    this.playlistService.addAlbum(this.form.value.playlistId, this.albumId).subscribe({
      next: () => {
        this.status.code = 1;
        this.status.message = "Songs added to playlist!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }
}
