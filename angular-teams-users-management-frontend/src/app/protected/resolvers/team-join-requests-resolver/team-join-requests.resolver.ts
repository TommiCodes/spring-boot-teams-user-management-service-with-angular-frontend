import { Pageable } from 'src/app/models/interfaces';
import { JoinRequestService } from '../../services/join-request-service/join-request.service';
import { JoinRequestPageResponse } from '../../../models/join-request.interfaces';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeamJoinRequestsResolver implements Resolve<JoinRequestPageResponse> {

  constructor(private joinRequestService: JoinRequestService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<JoinRequestPageResponse> {
    const teamId = route.paramMap.get('id');
    const pageSize: number = route.queryParamMap.get('pageSize') === null ? 20 : Number(route.queryParamMap.get('pageSize'));
    const pageNumber: number = route.queryParamMap.get('pageNumber') === null ? 0 : Number(route.queryParamMap.get('pageNumber'));

    const pageable: Pageable = {
      number: pageNumber,
      size: pageSize
    };

    return this.joinRequestService.getJoinRequestsByTeamId(Number(teamId), pageable);
  }
}
