import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';
import { AlertModule } from '../shared/components/alert/alert.module';
import { LoadingModule } from '../shared/components/loading/loading.module';
import { ShowIfLoggedModule } from '../shared/directives/show-if-logged/show-if-logged.module';
import { MaterialModule } from '../shared/material/material.module';
import { RequestInterceptor } from './auth/request.interceptor';
import { HeaderComponent } from './header/header.component';
import { LayoutComponent } from './layout/layout.component';
import { SidenavListComponent } from './sidenav-list/sidenav-list.component';

@NgModule({
    declarations: [
        HeaderComponent,
        SidenavListComponent,
        LayoutComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        AlertModule,
        LoadingModule,
        ShowIfLoggedModule,
        FlexLayoutModule,
        MaterialModule
    ],
    exports: [
        HeaderComponent,
        LayoutComponent,
        SidenavListComponent,
        FlexLayoutModule
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: RequestInterceptor,
            multi: true
        }
    ]
})
export class CoreModule { }