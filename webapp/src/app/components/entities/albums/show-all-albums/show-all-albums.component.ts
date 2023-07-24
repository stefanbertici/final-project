import {Component, OnInit} from '@angular/core';
import {AlbumViewDto} from "../../../../models/albumViewDto";
import {AlbumService} from "../../../../services/album.service";

@Component({
  selector: 'app-show-all-albums',
  templateUrl: './show-all-albums.component.html',
  styleUrls: ['./show-all-albums.component.scss']
})
export class ShowAllAlbumsComponent implements OnInit {

  albums: AlbumViewDto[] = [];
  constructor(private albumService: AlbumService) {
  }

  ngOnInit(): void {
    this.loadArtists();
  }

  private loadArtists() {
    this.albumService.getAll().subscribe((data: AlbumViewDto[]) => this.albums = data);
  }
}
