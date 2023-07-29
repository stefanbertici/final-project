import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AlbumDetailViewDto} from "../../../../models/albumDetailViewDto";
import {AlbumService} from "../../../../services/album.service";

@Component({
  selector: 'app-details-album',
  templateUrl: './details-album.component.html',
  styleUrls: ['./details-album.component.scss']
})
export class DetailsAlbumComponent implements OnInit {

  selectedSongIdForRemove?: number;
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

  startRemove(id: number) {
    this.selectedSongIdForRemove = id;
  }

  confirmRemove() {
    if (this.selectedSongIdForRemove !== undefined) {
      this.albumService.removeSong(this.album!.id, this.selectedSongIdForRemove).subscribe(data => {
        alert("Song removed!");
        this.selectedSongIdForRemove = undefined;
        this.loadAlbum();
      });
    }
  }

  cancelRemove() {
    this.selectedSongIdForRemove = undefined;
  }
}
