import { ComponentFixture, TestBed } from '@angular/core/testing';;
import { of } from 'rxjs';
import { UserListComponent } from './user-list.component';
import { User } from '../model/user';
import { UserService } from '../service/user-service.service';
import { NO_ERRORS_SCHEMA } from '@angular/core';


// Mock data
const mockUsers: User[] = [
  { userId: 1  ,  username: "user1"  ,  password: "password1"  ,  createdAt:new Date() },
  { userId: 2  ,  username: "user2"  ,  password: "password2"  ,  createdAt:new Date() }
];

// Mock service
class MockUserService {
  findAll() {
    return of(mockUsers);
  }
}
describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserListComponent ],
      providers: [
        { provide: UserService, useClass: MockUserService }
      ],
      schemas: [NO_ERRORS_SCHEMA] // To ignore any Angular Material specific issues during this test
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    fixture.detectChanges(); // Trigger initial data binding
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load data from the service on init', () => {
    component.ngOnInit();
    fixture.detectChanges();

    expect(component.users).toEqual(mockUsers);
  });

  it('should update dataSource when changes', () => {
    const newMockUsers: User[] = [
      { userId: 1  ,  username: "user1"  ,  password: "password1"  ,  createdAt:new Date() },
      { userId: 2  ,  username: "user2"  ,  password: "password2"  ,  createdAt:new Date() },
      { userId: 3  ,  username: "user3"  ,  password: "password3"  ,  createdAt:new Date() },
      { userId: 4  ,  username: "user4"  ,  password: "password4"  ,  createdAt:new Date() },
    ];

    
    // Simulate the change in data
    spyOn(userService, 'findAll').and.returnValue(of(newMockUsers));

    component.ngOnInit();
    fixture.detectChanges();

    // Since ngOnChanges calls ngOnInit, dataSource should be updated with the service's data, not the newmockUsers
    expect(component.users).toEqual(newMockUsers);
  });
});