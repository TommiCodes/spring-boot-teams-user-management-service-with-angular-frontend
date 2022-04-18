import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamProfileComponent } from './team-profile.component';

describe('TeamProfileComponent', () => {
  let component: TeamProfileComponent;
  let fixture: ComponentFixture<TeamProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
