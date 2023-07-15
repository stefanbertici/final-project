import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {PlaylistService} from "../../services/playlist.service";
import {PlaylistDto} from "../../models/playlistDto";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  selectedPlaylistId?: number;
  ownedPlaylists: PlaylistDto[] = [];
  followedPlaylists: PlaylistDto[] = [];

  constructor(private authService: AuthService, private playlistService: PlaylistService) {
  }

  ngOnInit(): void {
    this.loadPlaylists();
  }

  private loadPlaylists() {
    this.playlistService.getAll().subscribe((data: PlaylistDto[]) => {
      this.ownedPlaylists = [];
      this.followedPlaylists = [];

      for (const playlist of data) {
        if (playlist.ownerUserId === this.authService.getUserId()) {
          this.ownedPlaylists.push(playlist);
        } else {
          this.followedPlaylists.push(playlist);
        }
      }
    })
  }

  startDelete(id: number) {
    this.selectedPlaylistId = id;
  }

  startUnfollow(id: number) {

  }

  confirmDelete() {
    if (this.selectedPlaylistId !== undefined) {
      this.playlistService.delete(this.selectedPlaylistId).subscribe(data => {
        alert("Playlist deleted successfully");
        this.selectedPlaylistId = undefined;
        this.loadPlaylists();
      });
    }
  }

  cancelDelete() {
    this.selectedPlaylistId = undefined;
  }
}
