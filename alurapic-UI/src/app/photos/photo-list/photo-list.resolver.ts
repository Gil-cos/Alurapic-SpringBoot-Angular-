import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ResponseApi } from 'src/app/core/model/ResponseApi';
import { PhotoService } from '../photo/photo.service';


@Injectable({ providedIn: 'root'})
export class PhotoListResolver implements Resolve<Observable<ResponseApi>>{

    constructor(private service: PhotoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResponseApi> {
        const userName = route.params.userName;
        return this.service.listFromUser(userName);
    }
}