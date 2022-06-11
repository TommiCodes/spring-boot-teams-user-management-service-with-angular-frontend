import { Pageable } from '../../../models/interfaces';
import { TeamsPagedResponse } from '../../../models/team.interfaces';
import { Team } from 'src/app/models/team.interfaces';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';
import { UntypedFormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs';


@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnInit, OnChanges {

  @Input() teamsPagedResponse!: TeamsPagedResponse | null;
  @Output() paginate: EventEmitter<Pageable> = new EventEmitter<Pageable>();
  @Output() search: EventEmitter<string> = new EventEmitter<string>();

  searchTeamName = new UntypedFormControl();

  displayedColumns: string[] = ['id', 'name', 'actions'];
  // 'Definite Assignment Assertion' with "<property>!" to tell typescript that this variable will have a value at runtime
  dataSource: MatTableDataSource<Team> = new MatTableDataSource<Team>();

  ngOnInit(): void {
    // If the users enters something to search for a team, we delay the request for 500ms and then emit a SearchEvent to our parent
    this.searchTeamName.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      tap((teamName) => this.search.emit(teamName))
    ).subscribe();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['teamsPagedResponse'].currentValue) {
      this.dataSource.data = this.teamsPagedResponse!._embedded.teams;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginate.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }

}
