import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamJoinRequestsComponent } from './team-join-requests.component';

describe('TeamJoinRequestsComponent', () => {
  let component: TeamJoinRequestsComponent;
  let fixture: ComponentFixture<TeamJoinRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamJoinRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamJoinRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
