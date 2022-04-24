import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamMemberEditDialogComponent } from './team-member-edit-dialog.component';

describe('TeamMemberEditDialogComponent', () => {
  let component: TeamMemberEditDialogComponent;
  let fixture: ComponentFixture<TeamMemberEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamMemberEditDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamMemberEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
