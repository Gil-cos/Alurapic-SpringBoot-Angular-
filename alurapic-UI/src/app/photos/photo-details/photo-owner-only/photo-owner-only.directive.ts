import { Directive, ElementRef, Input, OnInit, Renderer } from "@angular/core";
import { Photo } from "../../../core/model/photo";
import { UserService } from "../../../core/user/user.service";

@Directive({
    selector: '[photoOwnerOnly]'
})
export class PhotoOwnerOnlyDirective implements OnInit { 

    @Input() ownedPhoto: Photo;
    
    constructor(
        private element: ElementRef<any>,
        private renderer: Renderer,
        private userService: UserService
    ) {}

    ngOnInit(): void {
        this.userService
            .getUser()
            .subscribe(user => {
                if(!user || user.id != this.ownedPhoto.user.id) {
                    this.renderer.setElementStyle(
                        this.element.nativeElement, 'display', 'none'
                    );
                }
            });
    }
}