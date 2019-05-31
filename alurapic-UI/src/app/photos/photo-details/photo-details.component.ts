import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";
import { Like } from 'src/app/core/model/Like';
import { Photo } from "../../core/model/photo";
import { UserService } from "../../core/user/user.service";
import { AlertService } from "../../shared/components/alert/alert.service";
import { PhotoService } from "../photo/photo.service";


@Component({
    templateUrl: './photo-details.component.html',
    styleUrls: ['./photo-details.component.css']
})
export class PhotoDetailsComponent implements OnInit {

    photo$: Observable<Photo>;
    photoId: number;
    like = new Like(0, null, null);
    photoIsLiked: boolean;

    constructor(
        private route: ActivatedRoute,
        private photoService: PhotoService,
        private router: Router,
        private alertService: AlertService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.photoId = this.route.snapshot.params.photoId;
        this.photo$ = this.photoService.findById(this.photoId);

        this.photoService.photoLiked(this.photoId)
            .subscribe(photoIsLiked => this.photoIsLiked = photoIsLiked);


        this.photo$.subscribe(() => { }, err => {
            console.log(err);
            this.router.navigate(['not-found']);
        });
    }

    remove() {
        this.photoService
            .removePhoto(this.photoId)
            .subscribe(
                () => {
                    this.alertService.success("Photo removed", true);
                    this.router.navigate(['/user', this.userService.getUserName()], { replaceUrl: true });
                },
                err => {
                    console.log(err);
                    this.alertService.warning('Could not delete the photo!', true);
                });
    }

    liked(photo: Photo) {
        this.photoService
            .like(photo.id, this.like)
            .subscribe(response => {
                if (response['data']) {
                    this.photo$ = this.photoService.findById(photo.id);
                    this.photoService.photoLiked(this.photoId)
                        .subscribe(photoIsLiked => this.photoIsLiked = photoIsLiked);
                }
            },
                err => {
                    console.log(err['error']['errors'][0]);
                    this.alertService.warning(err['error']['errors'][0], true);
                });
    }
}