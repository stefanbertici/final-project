import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {PlaylistService} from "../../services/playlist.service";
import {PlaylistViewDto} from "../../models/playlistViewDto";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  ownedPlaylists: PlaylistViewDto[] = [];
  followedPlaylists: PlaylistViewDto[] = [];

  constructor(private authService: AuthService, private playlistService: PlaylistService) {
  }

  ngOnInit(): void {
    this.playlistService.getPlaylists().subscribe((data: PlaylistViewDto[]) => {
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

  }

  startUnfollow(id: number) {

  }
}
