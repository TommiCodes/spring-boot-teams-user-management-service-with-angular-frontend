import { TeamsPagedResponse } from './../../../model/team.interfaces';
import { TeamService } from './../../services/team-service/team.service';
import { Component, OnInit } from '@angular/core';
import { map, tap } from 'rxjs';

@Component({
  selector: 'app-teams-page',
  templateUrl: './teams-page.component.html',
  styleUrls: ['./teams-page.component.scss']
})
export class TeamsPageComponent {

  teamsPaged$ = this.teamService.getAllTeams({
    number: 0,
    size: 20
  });

  constructor(
    private teamService: TeamService
  ) { }

}
