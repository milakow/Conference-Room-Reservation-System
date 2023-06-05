import { Component, Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Organization} from '../organization';
import { OrganizationService } from '../organization.service';
import { ReservationService } from '../reservation.service';
import { Reservation } from '../reservation';
import { ConferenceRoom, NumberOfPlaces } from '../conference-room';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatNativeDateModule} from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]

})


export class ReservationComponent implements OnInit{
  reservationList: Reservation[] = [];
  conferenceRoomId: number = 0;
  reservationId: number = 0;
  organizationId: number = 0;

  organizationList: Organization[] = [];
  conferenceRoomsList: ConferenceRoom[] = [];
  identifier: string = '';
  startDate: Date = new Date;
  endDate: Date = new Date;
  newReservation: Reservation = new Reservation(0, this.identifier, this.startDate, this.endDate);
  // isUpdating: boolean = false;
  updatedReservationId: number = 0;
  updatedIdentifier: string = '';
  updatedStartDate: Date = new Date;
  updatedEndDate: Date = new Date;
  
  
  constructor(private service: ReservationService, private router: Router, private organizationService: OrganizationService, private route: ActivatedRoute) {}
  
  ngOnInit(): void {
    this.loadReservations();
  }
  
  loadReservations(): void {
    this.service.getAllReservations().subscribe((list: Reservation[]) => {
      this.reservationList = list;
    })
  }

  addReservationToOrganization(reservationId: number, organizationId: number): void {
    console.log(organizationId);
    this.organizationService.addReservationToOrganization(reservationId, organizationId).subscribe( () => {
      this.loadReservations();
    })
  }

  createReservation(): void {
    this.service.addReservation(this.newReservation, this.conferenceRoomId).subscribe(() => {
      this.loadReservations(); 
      this.resetForm(); 
    });  }

  addIdentifier(identifier: string): void {
    this.newReservation.identifier = identifier;
  }

  addStartDate(startDate: Date): void {
    this.newReservation.startDate = startDate;
  }

  addEndDate(endDate: Date): void {
    this.newReservation.endDate = endDate;
  }
  
  deleteReservation(idToDelete: number): void {
    this.service.deleteReservation(idToDelete).subscribe(() => {
      this.loadReservations();
    });
  }

  updateReservation(idToUpdate: number, updatedIdentifier: string, updatedStartDate: Date, updatedEndDate: Date): void {
    const updatedReservation: Reservation = {
      id: idToUpdate,
      identifier: updatedIdentifier,
      startDate: updatedStartDate,
      endDate: updatedEndDate

      
    };
    this.service.updateReservation(idToUpdate, updatedReservation).subscribe(() => {
      this.loadReservations();
    });
  }
  
  resetForm() :void {
    this.newReservation = new Reservation(0, '', new Date(), new Date()); // Reset the newOrganziation object
  }

}
