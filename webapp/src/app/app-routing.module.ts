import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/pages/login/login.component";
import {SignupComponent} from "./components/pages/signup/signup.component";
import {DashboardComponent} from "./components/pages/dashboard/dashboard.component";
import {UserPageComponent} from "./components/pages/user-page/user-page.component";
import {AdminPageComponent} from "./components/pages/admin-page/admin-page.component";
import {authGuard} from "./guards/auth.guard";
import {AddPlaylistComponent} from "./components/entities/playlists/add-playlist/add-playlist.component";
import {EditPlaylistComponent} from "./components/entities/playlists/edit-playlist/edit-playlist.component";
import {DetailsPlaylistComponent} from "./components/entities/playlists/details-playlist/details-playlist.component";
import {
  AddSongToPlaylistComponent
} from "./components/entities/playlists/add-song-to-playlist/add-song-to-playlist.component";
import {adminGuard} from "./guards/admin.guard";
import {ShowAllArtistsComponent} from "./components/entities/artists/show-all-artists/show-all-artists.component";
import {AddArtistComponent} from "./components/entities/artists/add-artist/add-artist.component";
import {DetailsArtistComponent} from "./components/entities/artists/details-artist/details-artist.component";
import {EditArtistComponent} from "./components/entities/artists/edit-artist/edit-artist.component";
import {ShowAllUsersComponent} from "./components/entities/users/show-all-users/show-all-users.component";
import {DetailsUserComponent} from "./components/entities/users/details-user/details-user.component";
import {AddUserComponent} from "./components/entities/users/add-user/add-user.component";
import {EditUserComponent} from "./components/entities/users/edit-user/edit-user.component";
import {ShowAllSongsComponent} from "./components/entities/songs/show-all-songs/show-all-songs.component";
import {DetailsSongAdminComponent} from "./components/entities/songs/details-song-admin/details-song-admin.component";
import {EditSongComponent} from "./components/entities/songs/edit-song/edit-song.component";
import {AddSongComponent} from "./components/entities/songs/add-song/add-song.component";
import {ShowAllAlbumsComponent} from "./components/entities/albums/show-all-albums/show-all-albums.component";
import {
  DetailsAlbumAdminComponent
} from "./components/entities/albums/details-album-admin/details-album-admin.component";
import {AddAlbumComponent} from "./components/entities/albums/add-album/add-album.component";
import {EditAlbumComponent} from "./components/entities/albums/edit-album/edit-album.component";
import {AddSongToAlbumComponent} from "./components/entities/albums/add-song-to-album/add-song-to-album.component";
import {DetailsAlbumUserComponent} from "./components/entities/albums/details-album-user/details-album-user.component";
import {
  AddAlbumToPlaylistComponent
} from "./components/entities/playlists/add-album-to-playlist/add-album-to-playlist.component";
import {DetailsSongUserComponent} from "./components/entities/songs/details-song-user/details-song-user.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  {path: 'user-page', component: UserPageComponent, canActivate: [authGuard]},
  {path: 'playlists/add', component: AddPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/details/:id', component: DetailsPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/edit/:id', component: EditPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/add-song/:id', component: AddSongToPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/add-album/:id', component: AddAlbumToPlaylistComponent, canActivate: [authGuard]},
  {path: 'songs/details/:id', component: DetailsSongUserComponent, canActivate: [authGuard]},
  {path: 'albums/details/:id', component: DetailsAlbumUserComponent, canActivate: [authGuard]},
  {path: 'admin-page', component: AdminPageComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/users', component: ShowAllUsersComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/users/add', component: AddUserComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/users/details/:id', component: DetailsUserComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/users/edit/:id', component: EditUserComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists', component: ShowAllArtistsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/add', component: AddArtistComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/details/:id', component: DetailsArtistComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/edit/:id', component: EditArtistComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/albums', component: ShowAllAlbumsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/albums/add', component: AddAlbumComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/albums/details/:id', component: DetailsAlbumAdminComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/albums/edit/:id', component: EditAlbumComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/albums/add-song/:id', component: AddSongToAlbumComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/songs', component: ShowAllSongsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/songs/add', component: AddSongComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/songs/details/:id', component: DetailsSongAdminComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/songs/edit/:id', component: EditSongComponent, canActivate: [authGuard, adminGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
