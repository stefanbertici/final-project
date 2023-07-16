import {Time} from "@angular/common";

export interface SongViewDto {
  id: number;
  artist: string;
  title: string;
  alternativeTitles: string[];
  duration: Time;
  createdDate: Date;
  composers: string[];
  albums: string[];
}
