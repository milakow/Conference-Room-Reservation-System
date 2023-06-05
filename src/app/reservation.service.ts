import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Reservation } from './reservation';


@Injectable({
  providedIn: 'root'
})

export class ReservationService {
  private apiUrl = 'http://localhost:8080/reservation';
  private addReservationLink = 'http://localhost:8080/reservation/new/';


  constructor(private http: HttpClient) { }

  
  public getAllReservations(): Observable<Reservation[]> {
    const url = `${this.apiUrl}/all`;
    return this.http.get<Reservation[]>(url);
  }

  public getReservationById(id: number): Observable<Reservation> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Reservation>(url);
  }

  public createReservation(reservation: Reservation): Observable<void> {
    return this.http.post<void>(this.addReservationLink, reservation);
  }

  public addReservation(reservation: Reservation, conferenceRoomId: number): Observable<void> {
    return this.http.post<void>(this.addReservationLink + conferenceRoomId, reservation);
  }

  public updateReservation(id: number, reservation: Reservation): Observable<Reservation> {
    const url = `${this.apiUrl}/update/${id}`;
    return this.http.put<Reservation>(url, reservation);
  }

  public deleteReservation(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
