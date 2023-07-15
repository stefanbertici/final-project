import {Component, OnInit} from '@angular/core';
import {PlaylistViewDto} from "../../../models/playlistViewDto";
import {ActivatedRoute} from "@angular/router";
import {PlaylistService} from "../../../services/playlist.service";

@Component({
  selector: 'app-details-playlist',
  templateUrl: './details-playlist.component.html',
  styleUrls: ['./details-playlist.component.scss']
})
export class DetailsPlaylistComponent implements OnInit {

  playlistId: number = 0;
  playlistViewDto?: PlaylistViewDto;

  constructor(private activatedRoute: ActivatedRoute, private playlistService: PlaylistService) {
    this.activatedRoute.params.subscribe((params) => {
      this.playlistId = params['id'];
    });
  }

  ngOnInit(): void {
    this.playlistService.getById(this.playlistId).subscribe((data: PlaylistViewDto) => {
      this.playlistViewDto = data;
    });
  }
}
