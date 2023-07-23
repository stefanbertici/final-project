import {Component, OnInit} from '@angular/core';
import {SongViewDto} from "../../../../models/songViewDto";
import {AuthService} from "../../../../services/auth.service";
import {SongService} from "../../../../services/song.service";

@Component({
  selector: 'app-show-all-songs',
  templateUrl: './show-all-songs.component.html',
  styleUrls: ['./show-all-songs.component.scss']
})
export class ShowAllSongsComponent implements OnInit {

  songs: SongViewDto[] = [];

  constructor(private authService: AuthService, private songService: SongService) {
  }

  ngOnInit(): void {
    this.loadSongs();
  }

  private loadSongs() {
    this.songService.getAll().subscribe((data: SongViewDto[]) => this.songs = data);
  }
}
