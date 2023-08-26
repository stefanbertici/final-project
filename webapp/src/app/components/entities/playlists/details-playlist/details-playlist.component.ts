import {Component, OnInit} from '@angular/core';
import {PlaylistViewDto} from "../../../../models/playlistViewDto";
import {ActivatedRoute} from "@angular/router";
import {PlaylistService} from "../../../../services/playlist.service";
import {AuthService} from "../../../../services/auth.service";

@Component({
  selector: 'app-details-playlist',
  templateUrl: './details-playlist.component.html',
  styleUrls: ['./details-playlist.component.scss']
})
export class DetailsPlaylistComponent implements OnInit {

  selectedSongIdForRemove?: number;
  startedUnfollow: boolean = false;
  playlistId: number = 0;
  playlist?: PlaylistViewDto;

  constructor(private activatedRoute: ActivatedRoute, private authService: AuthService, private playlistService: PlaylistService) {
    this.activatedRoute.params.subscribe((params) => {
      this.playlistId = params['id'];
    });
  }

  private loadPlaylist() {
    this.playlistService.getById(this.playlistId).subscribe((data: PlaylistViewDto) => {
      this.playlist = data;
    });
  }

  ngOnInit(): void {
    this.loadPlaylist();
  }

  startRemove(id: number) {
    this.selectedSongIdForRemove = id;
  }

  confirmRemove() {
    if (this.selectedSongIdForRemove !== undefined) {
      this.playlistService.removeSong(this.playlist!.id, this.selectedSongIdForRemove).subscribe(data => {
        alert("Song removed!");
        this.selectedSongIdForRemove = undefined;
        this.loadPlaylist();
      });
    }
  }

  cancelRemove() {
    this.selectedSongIdForRemove = undefined;
  }

  isOwner() {
    return this.playlist?.ownerUserId === this.authService.getUserId();
  }

  canMoveDown(i: number) {
    return i < this.playlist!.songs.length - 1 && this.isOwner();
  }

  canMoveUp(i: number) {
    return i >= 1 && this.isOwner();
  }

  moveDown(i: number, songId: number) {
    let oldPosition: number = i + 1;
    let newPosition: number = oldPosition + 1;

    this.playlistService.changeOrder(this.playlistId, songId, oldPosition, newPosition).subscribe((data: PlaylistViewDto) => {
      this.playlist = data;
    });
  }

  moveUp(i: number, songId: number) {
    let oldPosition: number = i + 1;
    let newPosition: number = oldPosition - 1;

    this.playlistService.changeOrder(this.playlistId, songId, oldPosition, newPosition).subscribe((data: PlaylistViewDto) => {
      this.playlist = data;
    });
  }

  follow(id: number) {
    this.playlistService.follow(id).subscribe(() => {
      this.loadPlaylist();
    });
  }

  startUnfollow(id: number) {
    this.startedUnfollow = true;
  }

  cancelUnfollow() {
    this.startedUnfollow = false;
  }

  confirmUnfollow() {
    if (this.startedUnfollow) {
      this.playlistService.unfollow(this.playlistId).subscribe(() => {
        alert("Playlist unfollowed!");
        this.startedUnfollow = false;
        this.loadPlaylist();
      });
    }
  }
}
