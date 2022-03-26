import { map, Observable } from 'rxjs';
import { Component } from '@angular/core';
import { ActivatedRoute, Data } from '@angular/router';
import { JoinRequest } from 'src/app/model/join-request.interfaces';
import { Team } from 'src/app/model/team.interfaces';
import { User } from 'src/app/model/user.interfaces';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss'],
})
export class DashboardPageComponent {

  ownProfile$: Observable<User> = this.activatedRoute.data.pipe(map((data: Data) => data['ownProfile']));
  ownTeams$: Observable<Team[]> = this.activatedRoute.data.pipe(map((data: Data) => data['ownTeams']));
  ownLastJoinRequests$: Observable<JoinRequest[]> = this.activatedRoute.data.pipe(map((data: Data) => data['ownJoinRequests']));

  constructor(private activatedRoute: ActivatedRoute) { }
}
