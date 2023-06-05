import { Component, OnInit } from '@angular/core';
import { ConferenceRoom, NumberOfPlaces } from '../conference-room';
import { ConferenceRoomService } from '../conference-room.service';
import { Reservation } from '../reservation';


@Component({
  selector: 'app-conference-room',
  templateUrl: './conference-room.component.html',
  styleUrls: ['./conference-room.component.css']
})
export class ConferenceRoomComponent implements OnInit {
  numberOfPlaces: NumberOfPlaces = new NumberOfPlaces(0, 0);
  conferenceRooms: ConferenceRoom[] = [];
  reservationList: Reservation[] = [];
  newConferenceRoom: ConferenceRoom = new ConferenceRoom(0, '', '', 0,  true, this.numberOfPlaces);
  isUpdating: boolean = false;
  updatedName: string = '';
  updatedIdentifier: string = '';
  updatedLevel: number = 0;
  updatedAvailability: boolean = false;
  updatedNumberOfSittingPlaces: number = 0;
  updatedNumberOfStandingPlaces: number = 0;
  updatedNumberOfPlaces: NumberOfPlaces = new NumberOfPlaces(this.updatedNumberOfSittingPlaces, this.updatedNumberOfStandingPlaces);
  

  updateConferenceRoomId: number = 0;

  constructor(private service: ConferenceRoomService) {}

  ngOnInit(): void {
    this.loadConferenceRooms();
  }
  
  loadConferenceRooms(): void {
    this.service.getAllConferenceRooms().subscribe((list: ConferenceRoom[]) => {
      this.conferenceRooms = list;
    })
  }
  
  createConferenceRoom(): void {
    this.service.addConferenceRoom(this.newConferenceRoom).subscribe(() => {
      this.loadConferenceRooms(); // Reload the list of ConferenceRoom after creating a new one
      this.resetForm(); // Reset the form fields
    });
  }
  
  deleteConferenceRoom(idToDelete: number): void{
    this.service.deleteConferenceRoom(idToDelete).subscribe(() => {
      this.loadConferenceRooms();
    });
  }
  
  updateConferenceRoom(idToUpdate: number, updatedName: string, updatedIdentifier: string, updatedLevel: number, updatedAvailability: boolean, updatedNuberOfSittingPlaces: number, updatedNuberOfStandingPlaces: number): void {
    const updatedConferenceRoom: ConferenceRoom = {
      id: idToUpdate,
      name: updatedName,
      identifier: updatedIdentifier,
      level: updatedLevel,
      availability: updatedAvailability,
      numberOfPlaces: new NumberOfPlaces(updatedNuberOfSittingPlaces, updatedNuberOfStandingPlaces),
      reservationList: []
    };
  
    this.service.updateConferenceRoom(idToUpdate, updatedConferenceRoom).subscribe(() => {
      this.loadConferenceRooms();
      this.cancelEdit();
    });
  }
  
  cancelEdit(): void {
    this.isUpdating = false; // Ustaw wartość isEditing na false
    // ...
  }
  
  resetForm() :void {
    this.newConferenceRoom = new ConferenceRoom(0, '', '', 0,  true, this.numberOfPlaces); // Reset the newConferenceRoom object
  }















}
