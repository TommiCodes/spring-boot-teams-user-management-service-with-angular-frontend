import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamJoinRequestsPageComponent } from './team-join-requests-page.component';

describe('TeamJoinRequestsPageComponent', () => {
  let component: TeamJoinRequestsPageComponent;
  let fixture: ComponentFixture<TeamJoinRequestsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamJoinRequestsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamJoinRequestsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
