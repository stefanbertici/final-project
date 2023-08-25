import {Component} from '@angular/core';
import {SongViewDto} from "../../../../models/songViewDto";
import {ActivatedRoute} from "@angular/router";
import {SongService} from "../../../../services/song.service";

@Component({
  selector: 'app-details-song-user',
  templateUrl: './details-song-user.component.html',
  styleUrls: ['./details-song-user.component.scss']
})
export class DetailsSongUserComponent {

  songId: number = 0;
  song?: SongViewDto;

  constructor(private activatedRoute: ActivatedRoute, private songService: SongService) {
    this.activatedRoute.params.subscribe((params) => {
      this.songId = params['id'];
    });
  }

  ngOnInit(): void {
    this.songService.getById(this.songId).subscribe((data: SongViewDto) => {
      this.song = data;
    });
  }
}
