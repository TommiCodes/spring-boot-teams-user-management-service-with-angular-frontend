import { ActivatedRoute, Data, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { Component } from '@angular/core';
import { UserPagedResponse } from 'src/app/models/user.interfaces';
import { Pageable } from 'src/app/models/interfaces';

@Component({
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.scss']
})
export class UsersPageComponent {

  users$: Observable<UserPagedResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['users']));

  constructor(private activatedRoute: ActivatedRoute, private router: Router) { }

  onPagination(pageable: Pageable) {
    // If the query Params change, then our resolver will run again and load new teams into the route data
    // here we reload the same url but with different query Params
    this.router.navigate([], {
      queryParams: {
        pageSize: pageable.size,
        pageNumber: pageable.number
      }
    });
  }

  onSearchUsername(username: string) {
    this.router.navigate([], {
      queryParams: {
        username
      }
    })
  }

}
