import { UserService } from './../../services/user-service/user.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Data, ActivatedRoute } from '@angular/router';
import { map, Observable, tap } from 'rxjs';
import { User } from 'src/app/model/user.interfaces';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.scss']
})
export class UpdateProfileComponent implements OnInit {

  ownProfile$: Observable<User> = this.activatedRoute.data.pipe(map((data: Data) => data['ownProfile']));

  profileForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    username: new FormControl(null, [Validators.required]),
    firstname: new FormControl(null, [Validators.required]),
    lastname: new FormControl(null, [Validators.required]),
  });

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadForm();
  }

  loadForm() {
    this.ownProfile$.pipe(
      tap((user: User) => {
        // TODO: Implement Passwort Change
        this.profileForm.controls['email'].setValue(user.email);
        this.profileForm.controls['username'].setValue(user.username);
        this.profileForm.controls['firstname'].setValue(user.firstname);
        this.profileForm.controls['lastname'].setValue(user.lastname);
      })
    ).subscribe();
  }


  update() {
    this.userService.updateOwnProfile(this.profileForm.value).subscribe();
  }

}
