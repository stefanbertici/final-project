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
import { AddPlaylistComponent } from './components/entities/playlists/add-playlist/add-playlist.component';
import { EditPlaylistComponent } from './components/entities/playlists/edit-playlist/edit-playlist.component';
import { DetailsPlaylistComponent } from './components/entities/playlists/details-playlist/details-playlist.component';
import { AddSongToPlaylistComponent } from './components/entities/playlists/add-song-to-playlist/add-song-to-playlist.component';
import { ShowAllArtistsComponent } from './components/entities/artists/show-all-artists/show-all-artists.component';
import { AddArtistComponent } from './components/entities/artists/add-artist/add-artist.component';
import { DetailsArtistComponent } from './components/entities/artists/details-artist/details-artist.component';
import { EditArtistComponent } from './components/entities/artists/edit-artist/edit-artist.component';

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
    EditArtistComponent
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
