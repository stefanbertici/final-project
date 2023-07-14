import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../../models/status";
import {PlaylistService} from "../../../services/playlist.service";
import {AuthService} from "../../../services/auth.service";
import {PlaylistViewDto} from "../../../models/playlistViewDto";

@Component({
  selector: 'app-add-playlist',
  templateUrl: './add-playlist.component.html',
  styleUrls: ['./add-playlist.component.scss']
})
export class AddPlaylistComponent implements OnInit {

  form!: FormGroup;
  playlistViewDto!: PlaylistViewDto;
  status!: Status;

  constructor(private authService: AuthService, private playlistService: PlaylistService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'name': ['', Validators.required],
      'type': ['', Validators.required],
    })
  }

  get getFormControl() {
    return this.form.controls;
  }

  onPost() {
    console.log("clicked add playlist")
    this.status = {code: 0, message: "adding..."};
    let name: string = this.form.value.name;
    let type: string = this.form.value.type;
    let playlist: PlaylistViewDto = {
      id: 0, name: name, type: type, createdDate: new Date(),
      updatedDate: new Date(), ownerUserId: this.authService.getUserId()
    };

    this.playlistService.addPlaylist(playlist).subscribe({
      next: (res) => {
        this.playlistViewDto = res;
        this.status.code = 1;
        this.status.message = "added successfully!";
        this.form.reset();
      },
      error: (err) => {
        console.log("Server side error: " + err.message);
        this.status.code = 0;
        this.status.message = "it looks like there was an error :(";
      }
    })
  }
}
