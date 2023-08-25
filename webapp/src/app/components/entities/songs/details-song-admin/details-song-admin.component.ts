import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SongViewDto} from "../../../../models/songViewDto";
import {SongService} from "../../../../services/song.service";

@Component({
  selector: 'app-details-song',
  templateUrl: './details-song-admin.component.html',
  styleUrls: ['./details-song-admin.component.scss']
})
export class DetailsSongAdminComponent {

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
