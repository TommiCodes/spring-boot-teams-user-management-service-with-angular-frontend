import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtectedShellComponent } from './protected-shell.component';

describe('ProtectedShellComponent', () => {
  let component: ProtectedShellComponent;
  let fixture: ComponentFixture<ProtectedShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtectedShellComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtectedShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
