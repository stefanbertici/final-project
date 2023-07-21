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
  {path: 'admin-page', component: AdminPageComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists', component: ShowAllArtistsComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/add', component: AddArtistComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/details/:id', component: DetailsArtistComponent, canActivate: [authGuard, adminGuard]},
  {path: 'admin-page/artists/edit/:id', component: EditArtistComponent, canActivate: [authGuard, adminGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
