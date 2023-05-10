import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  userFullName: string = this.authService.getFullName() ?? "";

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }

}
