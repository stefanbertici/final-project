import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {ActivatedRoute} from "@angular/router";
import {ArtistService} from "../../../../services/artist.service";
import {ArtistViewDto} from "../../../../models/artistViewDto";
import {AlbumService} from "../../../../services/album.service";
import {AlbumViewDto} from "../../../../models/albumViewDto";

@Component({
  selector: 'app-add-song-to-album',
  templateUrl: './add-song-to-album.component.html',
  styleUrls: ['./add-song-to-album.component.scss']
})
export class AddSongToAlbumComponent implements OnInit {

  songId: number = 0;
  artists: ArtistViewDto[] = [];
  albums: AlbumViewDto[] = [];
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private artistService: ArtistService,
              private albumService: AlbumService, private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.songId = params['id'];
    });
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'artistId': ['', Validators.required],
      'albumId': ['', Validators.required],
    });

    this.artistService.getAll().subscribe((data: ArtistViewDto[]) => {
      this.artists = data;
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add song to album")
    this.status = {code: 0, message: "updating..."};

    this.albumService.addSong(this.form.value.albumId, this.songId).subscribe({
      next: (res) => {
        this.status.code = 1;
        this.status.message = "Song added to album!";
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "It looks like there was an error :(";
      }
    })
  }

  onChangeEvent(artistId: string) {
    this.loadAlbums(artistId);
  }

  private loadAlbums(artistId: string) {
    this.albumService.getAllByArtistId(parseInt(artistId)).subscribe((data: AlbumViewDto[]) => {
      this.albums = data;
    })
  }
}
