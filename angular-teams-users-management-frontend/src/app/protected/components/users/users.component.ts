import { User, UserPagedResponse } from 'src/app/model/user.interfaces';
import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { Pageable } from 'src/app/model/interfaces';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, OnChanges {

  @Input() usersResp!: UserPagedResponse | null;
  @Output() paginate: EventEmitter<Pageable> = new EventEmitter<Pageable>();
  @Output() search: EventEmitter<string> = new EventEmitter<string>();

  usernameSearchString = new FormControl();

  displayedColumns: string[] = ['id', 'username', 'firstname', 'lastname', 'email'];
  // 'Definite Assignment Assertion' with "<property>!" to tell typescript that this variable will have a value at runtime
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();

  ngOnInit(): void {
    // If the users enters something to search for a user, we delay the request for 500ms and then emit a SearchEvent to our parent
    this.usernameSearchString.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      tap((username) => this.search.emit(username))
    ).subscribe();

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['usersResp'].currentValue) {
      this.dataSource.data = this.usersResp!._embedded.users;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginate.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }

}
