import { Observable } from 'rxjs';
import { UserService } from './../../services/user-service/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/interfaces';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  user: Observable<User> = this.userService.getOwnProfile();

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
  }

}
