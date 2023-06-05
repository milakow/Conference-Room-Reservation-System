import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { ConferenceRoom } from './conference-room';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ConferenceRoomService {

  private getAllConferenceRoomsLink = 'http://localhost:8080/conferenceRoom/all';
  private getConferenceRoomByIdLink = 'http://localhost:8080/conferenceRoom/';
  private deleteConferenceRoomLink = 'http://localhost:8080/conferenceRoom/';
  private addConferenceRoomLink = 'http://localhost:8080/conferenceRoom/new';
  private updateConferenceRoomLink = 'http://localhost:8080/conferenceRoom/update/';

  constructor(private http: HttpClient) { }

  public getAllConferenceRooms(): Observable<ConferenceRoom[]> {
    return this.http.get<ConferenceRoom[]>(this.getAllConferenceRoomsLink, httpOptions)
  }

  public getConferenceRoomById(id: number): Observable<ConferenceRoom> {
    return this.http.get<ConferenceRoom>(this.getConferenceRoomByIdLink + id, httpOptions)
  }

  public deleteConferenceRoom(id: number): Observable<any> {
    return this.http.delete(this.deleteConferenceRoomLink + id, httpOptions)
  }

  public createConferenceRoom(conferenceRoom: ConferenceRoom): Observable<void> {
    return this.http.post<void>(this.addConferenceRoomLink, conferenceRoom, httpOptions);
  }

  public addConferenceRoom(conferenceRoom: ConferenceRoom): Observable<void> {
    return this.http.post<void>(this.addConferenceRoomLink, conferenceRoom, httpOptions)
  }

  public updateConferenceRoom(id: number, conferenceRoom: ConferenceRoom): Observable<any> {
    return this.http.put(this.updateConferenceRoomLink + id, conferenceRoom, httpOptions)
  }
}
