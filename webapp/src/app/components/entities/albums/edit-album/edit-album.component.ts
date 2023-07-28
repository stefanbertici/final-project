import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlbumDto} from "../../../../models/albumDto";
import {ArtistViewDto} from "../../../../models/artistViewDto";
import {Status} from "../../../../models/status";
import {ArtistService} from "../../../../services/artist.service";
import {AlbumService} from "../../../../services/album.service";
import {ActivatedRoute} from "@angular/router";
import {AlbumDetailViewDto} from "../../../../models/albumDetailViewDto";

@Component({
  selector: 'app-edit-album',
  templateUrl: './edit-album.component.html',
  styleUrls: ['./edit-album.component.scss']
})
export class EditAlbumComponent implements OnInit {

  albumId: number = 0;
  album!: AlbumDto;
  albumView!: AlbumDetailViewDto;
  artists?: ArtistViewDto[];
  form!: FormGroup;
  status!: Status;

  constructor(private activatedRoute: ActivatedRoute, private artistService: ArtistService,
              private albumService: AlbumService, private formBuilder: FormBuilder) {
    this.activatedRoute.params.subscribe((params) => {
      this.albumId = params['id'];
    });
  }

  ngOnInit(): void {
    this.artistService.getAll().subscribe((data: ArtistViewDto[]) => {
      this.artists = data;
    });

    this.form = this.formBuilder.group({
      'artistId': ['', Validators.required],
      'title': ['', Validators.required],
      'description': ['', Validators.required],
      'genre': ['', Validators.required],
      'releaseDate': ['', Validators.required],
      'label': ['', Validators.required]
    })

    this.albumService.getById(this.albumId).subscribe((data: AlbumDetailViewDto) => {
      this.albumView = data;
      this.form.patchValue({
        artistId: data.artistId,
        title: data.title,
        description: data.description,
        genre: data.genre,
        releaseDate: data.releaseDate,
        label: data.label
      })
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked update album")
    this.status = {code: 0, message: "updating..."};

    this.albumService.update(this.albumId, this.form.value).subscribe({
      next: (res) => {
        this.album = res;
        this.status.code = 1;
        this.status.message = "Album updated!";
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
