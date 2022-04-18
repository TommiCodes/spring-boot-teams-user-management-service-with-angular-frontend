import { User } from 'src/app/model/user.interfaces';
import { UserPagedResponse } from './../../../model/user.interfaces';
import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { outputAst } from '@angular/compiler';
import { Pageable } from 'src/app/model/interfaces';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-team-users',
  templateUrl: './team-users.component.html',
  styleUrls: ['./team-users.component.scss']
})
export class TeamUsersComponent implements OnChanges{

  @Input() members!: UserPagedResponse | null;
  @Output() paginate: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  displayedColumns: string[] = ['id', 'email', 'firstname', 'lastname'];
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['members'].currentValue) {
      this.dataSource.data = this.members!._embedded.users;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginate.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }


}
