import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../../models/status";
import {ArtistService} from "../../../../services/artist.service";
import {AlbumDto} from "../../../../models/albumDto";
import {AlbumService} from "../../../../services/album.service";
import {ArtistViewDto} from "../../../../models/artistViewDto";

@Component({
  selector: 'app-add-album',
  templateUrl: './add-album.component.html',
  styleUrls: ['./add-album.component.scss']
})
export class AddAlbumComponent implements OnInit {

  form!: FormGroup;
  album!: AlbumDto;
  artists?: ArtistViewDto[];
  status!: Status;

  constructor(private artistService: ArtistService, private albumService: AlbumService,
              private formBuilder: FormBuilder) {
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
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add album")
    this.status = {code: 0, message: "adding..."};

    this.albumService.create(this.form.value).subscribe({
      next: (res) => {
        this.album = res;
        this.status.code = 1;
        this.status.message = "Album created!";
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
