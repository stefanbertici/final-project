import { Component } from '@angular/core';
import {ArtistDto} from "../../../../models/artistDto";
import {ActivatedRoute} from "@angular/router";
import {ArtistService} from "../../../../services/artist.service";
import {UserViewDto} from "../../../../models/userViewDto";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-details-user',
  templateUrl: './details-user.component.html',
  styleUrls: ['./details-user.component.scss']
})
export class DetailsUserComponent {

  userId: number = 0;
  userViewDto?: UserViewDto;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService) {
    this.activatedRoute.params.subscribe((params) => {
      this.userId = params['id'];
    });
  }

  ngOnInit(): void {
    this.userService.getById(this.userId).subscribe((data: UserViewDto) => {
      this.userViewDto = data;
    });
  }
}
