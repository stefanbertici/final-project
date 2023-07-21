import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "../services/auth.service";

@Injectable({
  providedIn: 'root'
})
class PermissionsService {

  constructor(private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (authService.getUserRole() === 'admin') {
      return true;
    } else {
      router.navigate(['/']);
      return false;
    }
  }
}

export const adminGuard: CanActivateFn = (route, state) => {
  return inject(PermissionsService).canActivate(route, state);
};

