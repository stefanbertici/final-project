import {Component, OnInit} from '@angular/core';
import {ArtistDto} from "../../../../models/artistDto";
import {ActivatedRoute} from "@angular/router";
import {ArtistService} from "../../../../services/artist.service";

@Component({
  selector: 'app-details-artist',
  templateUrl: './details-artist.component.html',
  styleUrls: ['./details-artist.component.scss']
})
export class DetailsArtistComponent implements OnInit {

  artistId: number = 0;
  artistDto?: ArtistDto;

  constructor(private activatedRoute: ActivatedRoute, private artistService: ArtistService) {
    this.activatedRoute.params.subscribe((params) => {
      this.artistId = params['id'];
    });
  }

  ngOnInit(): void {
    this.artistService.getById(this.artistId).subscribe((data: ArtistDto) => {
      this.artistDto = data;
    });
  }
}
