import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserAuthenticated } from 'src/app/core/model/UserAuthenticated';
import { PlatformDetectorService } from 'src/app/core/plataform-detector/platform-detector.service';
import { UserService } from 'src/app/core/user/user.service';
import { AuthService } from '../../core/auth/auth.service';
import { CurrentUser } from '../../core/model/CurrentUser';

@Component({
    templateUrl: './signin.component.html',
    styleUrls: ['./signin.component.css']
})
export class SignInComponent implements OnInit {

    fromUrl: string;
    loginForm: FormGroup;
    userAuthenticated = new UserAuthenticated('','');
    
    @ViewChild('userNameInput') userNameInput: ElementRef<HTMLInputElement>;

    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private userService: UserService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService,
        private activatedRoute: ActivatedRoute) { }

    ngOnInit(): void {
        this.activatedRoute
            .queryParams
            .subscribe(params => this.fromUrl = params['fromUrl']);

        this.loginForm = this.formBuilder.group({
            userName: ['', Validators.required],
            password: ['', Validators.required]
        });

        this.platformDetectorService.isPlatformBrowser() &&
            this.userNameInput.nativeElement.focus();
    }

    login() {
        const userName = this.loginForm.get('userName').value;
        const password = this.loginForm.get('password').value;
        this.userAuthenticated.userName = userName;
        this.userAuthenticated.password = password;


        this.authService
            .authenticate(this.userAuthenticated)
            .subscribe(
                (currentUser: CurrentUser) => {
                    this.userService.setToken(currentUser.token),
                    this.fromUrl
                        ? this.router.navigateByUrl(this.fromUrl)
                        : this.router.navigate(['user', userName])
                },
                err => {
                    console.log(err);
                    this.loginForm.reset();
                    this.platformDetectorService.isPlatformBrowser() &&
                        this.userNameInput.nativeElement.focus();
                    alert('Invalid user name or password');
                }
            );
    }
}