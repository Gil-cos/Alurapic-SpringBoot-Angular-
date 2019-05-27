import { Component, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/core/model/User';
import { UserService } from '../user/user.service';

@Component({
    selector: 'ap-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent { 

    @Output() public sidenavToggle = new EventEmitter();
    
    user$: Observable<User>;

    constructor(
        private userService: UserService, 
        private router:Router) {

        this.user$ = userService.getUser();
    }

    logout() {
        this.userService.logout();
        this.router.navigate(['']);
    }

    public onToggleSidenav = () => {
        this.sidenavToggle.emit();
      }
}