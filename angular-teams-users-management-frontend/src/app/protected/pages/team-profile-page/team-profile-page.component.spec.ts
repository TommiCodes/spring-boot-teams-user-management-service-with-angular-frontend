import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamProfilePageComponent } from './team-profile-page.component';

describe('TeamProfilePageComponent', () => {
  let component: TeamProfilePageComponent;
  let fixture: ComponentFixture<TeamProfilePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamProfilePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamProfilePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
