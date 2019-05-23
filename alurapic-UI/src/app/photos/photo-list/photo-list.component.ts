import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Photo } from '../../core/model/photo';
import { PhotoService } from '../photo/photo.service';
import { ResponseApi } from '../../core/model/ResponseApi';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit {

  photos: Photo[] = [];
  filter: string = '';
  hasMore: boolean = true;
  currentPage: number = 1;
  userName: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private photoService: PhotoService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.userName = params.userName;
      this.photoService
        .listFromUser(this.userName)
        .subscribe((responseApi: ResponseApi) => {
          this.photos = this.photos.concat(responseApi['data']);
        });
    });
  }

  load() {
    this.photoService
      .listFromUser(this.userName)
      .subscribe((responseApi: ResponseApi) => {
        this.filter = '';
        this.photos = this.photos.concat(responseApi['data']);
        if (!this.photos.length) this.hasMore = false;
      });
  }
}
