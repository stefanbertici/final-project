import {Time} from "@angular/common";

export interface SongDto {
  id: number;
  title: string;
  duration: Time;
  createdDate: Date;
}
