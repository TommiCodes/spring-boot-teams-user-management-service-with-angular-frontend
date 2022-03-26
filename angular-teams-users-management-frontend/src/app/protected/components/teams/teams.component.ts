import { TeamsPagedResponse } from './../../../model/team.interfaces';
import { Team } from 'src/app/model/team.interfaces';
import { AfterContentInit, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnChanges {

  @Input() teamsPagedResponse!: TeamsPagedResponse | null;

  displayedColumns: string[] = ['id', 'name'];
  // 'Definite Assignment Assertion' with "<property>!" to tell typescript that this variable will have a value at runtime
  dataSource: MatTableDataSource<Team> = new MatTableDataSource<Team>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['teamsPagedResponse'].currentValue) {
      this.dataSource.data = this.teamsPagedResponse!._embedded.teams;
    }
  }

}
