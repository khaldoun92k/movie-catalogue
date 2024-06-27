import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { User } from '../model/user';
import { UserService } from './user-service.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve users from the API via GET', () => {
    const dummyUsers: User[] = [
      { userId: 1  ,  username: "user1"  ,  password: "password1"  ,  createdAt:new Date() },
      { userId: 2  ,  username: "user2"  ,  password: "password2"  ,  createdAt:new Date() }
    ];

    const mockResponse = {
      _embedded: {
        userList: dummyUsers,
        _links: { self: { href: '/users' } }
      }
    };

    service.findAll().subscribe(users => {
      expect(users.length).toBe(2);
      expect(users).toEqual(dummyUsers);
    });

    const req = httpMock.expectOne('/users');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should return an empty array if _embedded or userList is missing', () => {
    const mockResponse = {};

    service.findAll().subscribe(users => {
      expect(users.length).toBe(0);
      expect(users).toEqual([]);
    });

    const req = httpMock.expectOne('/users');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });
});
