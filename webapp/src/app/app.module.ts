import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DashboardComponent} from './components/pages/dashboard/dashboard.component';
import {AdminPageComponent} from './components/pages/admin-page/admin-page.component';
import {UserPageComponent} from './components/pages/user-page/user-page.component';
import {LoginComponent} from './components/pages/login/login.component';
import {SignupComponent} from './components/pages/signup/signup.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RequestInterceptor} from "./interceptors/request.interceptor";
import {AddPlaylistComponent} from './components/entities/playlists/add-playlist/add-playlist.component';
import {EditPlaylistComponent} from './components/entities/playlists/edit-playlist/edit-playlist.component';
import {DetailsPlaylistComponent} from './components/entities/playlists/details-playlist/details-playlist.component';
import {
  AddSongToPlaylistComponent
} from './components/entities/playlists/add-song-to-playlist/add-song-to-playlist.component';
import {ShowAllArtistsComponent} from './components/entities/artists/show-all-artists/show-all-artists.component';
import {AddArtistComponent} from './components/entities/artists/add-artist/add-artist.component';
import {DetailsArtistComponent} from './components/entities/artists/details-artist/details-artist.component';
import {EditArtistComponent} from './components/entities/artists/edit-artist/edit-artist.component';
import {ShowAllUsersComponent} from './components/entities/users/show-all-users/show-all-users.component';
import {DetailsUserComponent} from './components/entities/users/details-user/details-user.component';
import {AddUserComponent} from './components/entities/users/add-user/add-user.component';
import {EditUserComponent} from './components/entities/users/edit-user/edit-user.component';
import {ShowAllSongsComponent} from './components/entities/songs/show-all-songs/show-all-songs.component';
import {DetailsSongComponent} from './components/entities/songs/details-song/details-song.component';
import {EditSongComponent} from './components/entities/songs/edit-song/edit-song.component';
import {AddSongComponent} from './components/entities/songs/add-song/add-song.component';
import {ShowAllAlbumsComponent} from './components/entities/albums/show-all-albums/show-all-albums.component';
import {DetailsAlbumComponent} from './components/entities/albums/details-album/details-album.component';
import {AddAlbumComponent} from './components/entities/albums/add-album/add-album.component';
import {EditAlbumComponent} from './components/entities/albums/edit-album/edit-album.component';
import {AddSongToAlbumComponent} from './components/entities/albums/add-song-to-album/add-song-to-album.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AdminPageComponent,
    UserPageComponent,
    LoginComponent,
    SignupComponent,
    AddPlaylistComponent,
    EditPlaylistComponent,
    DetailsPlaylistComponent,
    AddSongToPlaylistComponent,
    ShowAllArtistsComponent,
    AddArtistComponent,
    DetailsArtistComponent,
    EditArtistComponent,
    ShowAllUsersComponent,
    DetailsUserComponent,
    AddUserComponent,
    EditUserComponent,
    ShowAllSongsComponent,
    DetailsSongComponent,
    EditSongComponent,
    AddSongComponent,
    ShowAllAlbumsComponent,
    DetailsAlbumComponent,
    AddAlbumComponent,
    EditAlbumComponent,
    AddSongToAlbumComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: RequestInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
