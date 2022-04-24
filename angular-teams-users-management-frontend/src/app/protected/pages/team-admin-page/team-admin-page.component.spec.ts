import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamAdminPageComponent } from './team-admin-page.component';

describe('TeamJoinRequestsPageComponent', () => {
  let component: TeamAdminPageComponent;
  let fixture: ComponentFixture<TeamAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamAdminPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
