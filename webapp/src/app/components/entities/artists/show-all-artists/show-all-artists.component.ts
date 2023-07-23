import {Component, OnInit} from '@angular/core';
import {ArtistViewDto} from "../../../../models/artistViewDto";
import {ArtistService} from "../../../../services/artist.service";

@Component({
  selector: 'app-show-all-artists',
  templateUrl: './show-all-artists.component.html',
  styleUrls: ['./show-all-artists.component.scss']
})
export class ShowAllArtistsComponent implements OnInit {

  artists: ArtistViewDto[] = [];
  selectedArtistId: number | undefined;
  constructor(private artistService: ArtistService) {
  }

  ngOnInit(): void {
    this.loadArtists();
  }

  private loadArtists() {
    this.artistService.getAll().subscribe((data: ArtistViewDto[]) => this.artists = data);
  }

  startDelete(id: number | undefined) {
    this.selectedArtistId = id;
  }

  confirmDelete() {
    if (this.selectedArtistId !== undefined) {
      this.artistService.delete(this.selectedArtistId).subscribe(data => {
        alert("Artist deleted!");
        this.selectedArtistId = undefined;
        this.loadArtists();
      });
    }
  }

  cancelDelete() {
    this.selectedArtistId = undefined;
  }
}
