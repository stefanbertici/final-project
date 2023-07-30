import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AlbumDetailViewDto} from "../../../../models/albumDetailViewDto";
import {AlbumService} from "../../../../services/album.service";

@Component({
  selector: 'app-details-album',
  templateUrl: './details-album-admin.component.html',
  styleUrls: ['./details-album-admin.component.scss']
})
export class DetailsAlbumAdminComponent implements OnInit {

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
      this.albumService.removeSong(this.album!.id, this.selectedSongIdForRemove).subscribe(() => {
        alert("Song removed!");
        this.selectedSongIdForRemove = undefined;
        this.loadAlbum();
      });
    }
  }

  cancelRemove() {
    this.selectedSongIdForRemove = undefined;
  }

  canMoveDown(i: number) {
    return i < this.album!.songs.length - 1;
  }

  canMoveUp(i: number) {
    return i >= 1;
  }

  moveDown(i: number, songId: number) {
    let oldPosition: number = i + 1;
    let newPosition: number = oldPosition + 1;

    this.albumService.changeOrder(this.albumId, songId, oldPosition, newPosition).subscribe((data: AlbumDetailViewDto) => {
      this.album = data;
    });
  }

  moveUp(i: number, songId: number) {
    let oldPosition: number = i + 1;
    let newPosition: number = oldPosition - 1;

    this.albumService.changeOrder(this.albumId, songId, oldPosition, newPosition).subscribe((data: AlbumDetailViewDto) => {
      this.album = data;
    });
  }
}
