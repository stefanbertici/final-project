import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authService = this.injector.get(AuthService)

    if (authService.isLoggedIn()) {
      let requestWithAccessToken = request.clone({
        setHeaders: {
          Authorization:`Bearer ${authService.getAccessToken()}`
        }
      })

      return next.handle(requestWithAccessToken);
    }

    return next.handle(request);
  }
}
