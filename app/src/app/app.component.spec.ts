import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { BehaviorSubject, of } from 'rxjs';
import { LoginService } from './service/login.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule, convertToParamMap } from '@angular/router';

// Mock ActivatedRoute
class MockActivatedRoute {
  private paramMapSubject = new BehaviorSubject(convertToParamMap({}));

  readonly paramMap = this.paramMapSubject.asObservable();

  setParamMap(params: any) {
    this.paramMapSubject.next(convertToParamMap(params));
  }
}
// Mock LoginService
class MockLoginService {
  isLoggedIn = of(false); // Mock implementation of isLoggedIn observable
}

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockLoginService: MockLoginService; // Mock instance of LoginService

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppComponent ],
      providers: [
        { provide: LoginService, useClass: MockLoginService }, // Provide the mock LoginService
        { provide: ActivatedRoute, useValue: MockActivatedRoute }
      ],
      imports: [
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        RouterModule  
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    mockLoginService = TestBed.inject(LoginService) as MockLoginService; // Inject the mock service
    fixture.detectChanges();
  });

  it('should create the app component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize isLoggedIn to false', () => {
    expect(component.isLoggedIn).toBe(false);
  });

  it('should unsubscribe on ngOnDestroy', () => {
    spyOn(component.loginSub, 'unsubscribe');
    component.ngOnDestroy();
    expect(component.loginSub.unsubscribe).toHaveBeenCalled();
  });
});
