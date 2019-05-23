import { Component, Input, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Observable } from "rxjs";
import { switchMap, tap } from 'rxjs/operators';
import { UserService } from 'src/app/core/user/user.service';
import { NewComment } from "../../../core/model/NewComment";
import { PhotoComment } from "../../../core/model/photo-comment";
import { PhotoService } from "../../photo/photo.service";




@Component({
    selector: 'ap-photo-comments',
    templateUrl: './photo-comments.component.html',
    styleUrls: ['photo-comments.css']
})
export class PhotoCommentsComponent implements OnInit {

    @Input() photoId: number;
    commentForm: FormGroup;
    userId: number;
    comment = new NewComment('');

    comments$: Observable<PhotoComment[]>;

    constructor(
        private photoService: PhotoService,
        private userService: UserService,
        private formBuilder: FormBuilder
    ) { }

    ngOnInit(): void {
        this.userService.getUser()
            .subscribe(user => this.userId = user.id);

        this.comments$ = this.photoService.getComments(this.photoId);
        
        this.commentForm = this.formBuilder.group({
            comment: ['', Validators.maxLength(300)]
        });
    }

    save() {
        this.comment.text = this.commentForm.get('comment').value as string;
        this.comments$ = this.photoService
            .addComment(this.photoId, this.userId, this.comment)
            .pipe(switchMap(() => this.photoService.getComments(this.photoId)))
            .pipe(tap(() => {
                this.commentForm.reset();
            }));
    }
}