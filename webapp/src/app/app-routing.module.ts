import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {SignupComponent} from "./components/signup/signup.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {UserPageComponent} from "./components/user-page/user-page.component";
import {AdminComponent} from "./components/admin/admin.component";
import {authGuard} from "./guards/auth.guard";
import {AddPlaylistComponent} from "./components/playlist/add-playlist/add-playlist.component";
import {EditPlaylistComponent} from "./components/playlist/edit-playlist/edit-playlist.component";
import {DetailsPlaylistComponent} from "./components/playlist/details-playlist/details-playlist.component";
import {AddSongToPlaylistComponent} from "./components/playlist/add-song-to-playlist/add-song-to-playlist.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  {path: 'user-page', component: UserPageComponent, canActivate: [authGuard]},
  {path: 'admin-page', component: AdminComponent, canActivate: [authGuard]},
  {path: 'playlists/add', component: AddPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/edit/:id', component: EditPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/details/:id', component: DetailsPlaylistComponent, canActivate: [authGuard]},
  {path: 'playlists/add-song/:id', component: AddSongToPlaylistComponent, canActivate: [authGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
