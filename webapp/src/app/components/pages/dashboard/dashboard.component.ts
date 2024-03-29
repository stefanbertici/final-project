import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {PlaylistService} from "../../../services/playlist.service";
import {SearchViewDto} from "../../../models/searchViewDto";
import {SearchService} from "../../../services/search.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PlaylistViewDto} from "../../../models/playlistViewDto";
import {PlaylistCategorizedViewDto} from "../../../models/playlistCategorizedViewDto";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  form!: FormGroup;
  selectedPlaylistIdForDelete?: number;
  selectedPlaylistIdForUnfollow?: number;
  searchResults?: SearchViewDto;
  ownedPlaylists: PlaylistViewDto[] = [];
  followedPlaylists: PlaylistViewDto[] = [];

  constructor(private authService: AuthService, private playlistService: PlaylistService,
              private searchService: SearchService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.loadPlaylists();
    this.form = this.formBuilder.group({
      'searchTerm': ['', Validators.required]
    })

    let searchTerm = this.getSearchTerm
    if (searchTerm !== undefined && searchTerm !== null && searchTerm.trim().length !== 0) {
      this.form.patchValue({
        searchTerm: searchTerm
      })
    }

    let searchResult = localStorage.getItem("searchResult");
    if (searchResult !== null) {
      this.searchResults = JSON.parse(searchResult);
    }
  }

  get getFormControl() {
    return this.form.controls;
  }

  get getSearchTerm() {
    return localStorage.getItem("searchTerm");
  }

  private loadPlaylists() {
    this.playlistService.getAll().subscribe((data: PlaylistCategorizedViewDto) => {
      this.ownedPlaylists = data.ownedPlaylists;
      this.followedPlaylists = data.followedPlaylists;
    })
  }

  onPost() {
    console.log("clicked search")
    localStorage.setItem("searchTerm", this.form.value.searchTerm);

    this.searchService.search(this.form.value.searchTerm).subscribe({
      next: (res) => {
        this.searchResults = res;
        localStorage.setItem("searchResult", JSON.stringify(this.searchResults));
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
      }
    })
  }

  clearSearch() {
    this.searchResults = undefined;
    this.form.patchValue({
      searchTerm: ''
    })
    localStorage.removeItem("searchTerm");
    localStorage.removeItem("searchResult");
  }

  startDelete(id: number) {
    this.selectedPlaylistIdForDelete = id;
  }

  startUnfollow(id: number) {
    this.selectedPlaylistIdForUnfollow = id;
  }

  confirmDelete() {
    if (this.selectedPlaylistIdForDelete !== undefined) {
      this.playlistService.delete(this.selectedPlaylistIdForDelete).subscribe(() => {
        alert("Playlist deleted!");
        this.selectedPlaylistIdForDelete = undefined;
        this.loadPlaylists();
      });
    }
  }

  confirmUnfollow() {
    if (this.selectedPlaylistIdForUnfollow !== undefined) {
      this.playlistService.unfollow(this.selectedPlaylistIdForUnfollow).subscribe(() => {
        alert("Playlist unfollowed!");
        this.selectedPlaylistIdForUnfollow = undefined;
        this.loadPlaylists();
      });
    }
  }

  cancelDelete() {
    this.selectedPlaylistIdForDelete = undefined;
  }

  cancelUnfollow() {
    this.selectedPlaylistIdForUnfollow = undefined;
  }

  follow(id: number) {
    this.playlistService.follow(id).subscribe(() => {
      this.loadPlaylists();
    });
  }
}
