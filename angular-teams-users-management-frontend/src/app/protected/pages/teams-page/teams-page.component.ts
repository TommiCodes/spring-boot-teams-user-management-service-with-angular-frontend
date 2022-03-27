import { Pageable } from './../../../model/interfaces';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { TeamsPagedResponse } from './../../../model/team.interfaces';
import { Component } from '@angular/core';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-teams-page',
  templateUrl: './teams-page.component.html',
  styleUrls: ['./teams-page.component.scss']
})
export class TeamsPageComponent {

  teamsPaged$: Observable<TeamsPagedResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['allTeams']));

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  paginate(pageable: Pageable) {
    // If the query Params change, then our resolver will run again and load new teams into the route data
    // here we reload the same url but with different query Params
    this.router.navigate([], {
      queryParams: {
        pageSize: pageable.size,
        pageNumber: pageable.number
      }
    });
  }

}
