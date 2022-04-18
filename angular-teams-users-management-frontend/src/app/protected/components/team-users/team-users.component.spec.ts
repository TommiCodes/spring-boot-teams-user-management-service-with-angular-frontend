import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamUsersComponent } from './team-users.component';

describe('TeamUsersComponent', () => {
  let component: TeamUsersComponent;
  let fixture: ComponentFixture<TeamUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
