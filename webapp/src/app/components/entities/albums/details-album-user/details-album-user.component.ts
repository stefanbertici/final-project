import {Component, OnInit} from '@angular/core';
import {AlbumDetailViewDto} from "../../../../models/albumDetailViewDto";
import {ActivatedRoute} from "@angular/router";
import {AlbumService} from "../../../../services/album.service";

@Component({
  selector: 'app-details-album-user',
  templateUrl: './details-album-user.component.html',
  styleUrls: ['./details-album-user.component.scss']
})
export class DetailsAlbumUserComponent implements OnInit {

  albumId: number = 0;
  album?: AlbumDetailViewDto;

  constructor(private activatedRoute: ActivatedRoute, private albumService: AlbumService) {
    this.activatedRoute.params.subscribe((params) => {
      this.albumId = params['id'];
    });
  }

  ngOnInit(): void {
    this.loadAlbum();
  }

  private loadAlbum() {
    this.albumService.getById(this.albumId).subscribe((data: AlbumDetailViewDto) => {
      this.album = data;
    });
  }
}
