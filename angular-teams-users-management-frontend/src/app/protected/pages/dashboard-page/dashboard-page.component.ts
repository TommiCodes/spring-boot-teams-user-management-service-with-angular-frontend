import { map, Observable } from 'rxjs';
import { Component } from '@angular/core';
import { ActivatedRoute, Data } from '@angular/router';
import { User } from 'src/app/models/user.interfaces';

@Component({
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss'],
})
export class DashboardPageComponent {

  ownProfile$: Observable<User> = this.activatedRoute.data.pipe(map((data: Data) => data['ownProfile']));

  constructor(private activatedRoute: ActivatedRoute) { }
}
