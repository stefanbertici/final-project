import {Component, OnInit} from '@angular/core';
import {UserViewDto} from "../../../../models/userViewDto";
import {UserService} from "../../../../services/user.service";
import {AuthService} from "../../../../services/auth.service";

@Component({
  selector: 'app-show-all-users',
  templateUrl: './show-all-users.component.html',
  styleUrls: ['./show-all-users.component.scss']
})
export class ShowAllUsersComponent implements OnInit {

  users: UserViewDto[] = [];
  selectedUserIdForDelete: number | undefined;
  selectedUserIdForPromote: number | undefined;
  selectedUserIdForDemote: number | undefined;
  selectedUserIdForActivate: number | undefined;
  selectedUserIdForDeactivate: number | undefined;

  constructor(private authService: AuthService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  private loadUsers() {
    this.userService.getAll().subscribe((data: UserViewDto[]) => this.users = data);
  }

  canPromote(user: UserViewDto) {
    return 'user' === user.role && this.authService.getUserId() !== user.id;
  }

  canDemote(user: UserViewDto) {
    return 'admin' === user.role && this.authService.getUserId() !== user.id;
  }

  canDeactivate(user: UserViewDto) {
    return 'active' === user.status && this.authService.getUserId() !== user.id;
  }

  canActivate(user: UserViewDto) {
    return 'inactive' === user.status && this.authService.getUserId() !== user.id;
  }

  startDelete(id: number | undefined) {
    this.selectedUserIdForDelete = id;
  }

  confirmDelete() {
    if (this.selectedUserIdForDelete !== undefined) {
      this.userService.delete(this.selectedUserIdForDelete).subscribe(data => {
        alert("User deleted!");
        this.selectedUserIdForDelete = undefined;
        this.loadUsers();
      });
    }
  }

  cancelDelete() {
    this.selectedUserIdForDelete = undefined;
  }

  startPromote(id: number) {
    this.selectedUserIdForPromote = id;
  }

  confirmPromote() {
    if (this.selectedUserIdForPromote !== undefined) {
      this.userService.promote(this.selectedUserIdForPromote).subscribe(data => {
        alert("User promoted to admin!");
        this.selectedUserIdForPromote = undefined;
        this.loadUsers();
      });
    }
  }

  cancelPromote() {
    this.selectedUserIdForPromote = undefined;
  }

  startDemote(id: number) {
    this.selectedUserIdForDemote = id;
  }

  confirmDemote() {
    if (this.selectedUserIdForDemote !== undefined) {
      this.userService.demote(this.selectedUserIdForDemote).subscribe(data => {
        alert("User demoted to user!");
        this.selectedUserIdForDemote = undefined;
        this.loadUsers();
      });
    }
  }

  cancelDemote() {
    this.selectedUserIdForDemote = undefined;
  }

  startActivate(id: number) {
    this.selectedUserIdForActivate = id;
  }

  confirmActivate() {
    if (this.selectedUserIdForActivate !== undefined) {
      this.userService.activate(this.selectedUserIdForActivate).subscribe(data => {
        alert("User activated!");
        this.selectedUserIdForActivate = undefined;
        this.loadUsers();
      });
    }
  }

  cancelActivate() {
    this.selectedUserIdForActivate = undefined;
  }

  startDeactivate(id: number) {
    this.selectedUserIdForDeactivate = id;
  }

  confirmDeactivate() {
    if (this.selectedUserIdForDeactivate !== undefined) {
      this.userService.deactivate(this.selectedUserIdForDeactivate).subscribe(data => {
        alert("User deactivated!");
        this.selectedUserIdForDeactivate = undefined;
        this.loadUsers();
      });
    }
  }

  cancelDeactivate() {
    this.selectedUserIdForDeactivate = undefined;
  }
}
