import { Reservation } from "./reservation";

export class ConferenceRoom {
    "id": number;
    "name": string;
    "identifier": string;
    "level": number;
    "availability": boolean;
    "numberOfPlaces": NumberOfPlaces;
    "reservationList": Reservation[];


    constructor(
        id: number,
        name: string,
        identifier: string,
        level: number,
        availability: boolean,
        numberOfPlaces: NumberOfPlaces,
      ) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfPlaces = numberOfPlaces;
        this.reservationList = [];
      }

}

export class NumberOfPlaces {
    "sitting": number;
    "standing": number;
    constructor(
        sitting: number,
        standing: number
    ){
        this.sitting = sitting;
        this.standing = standing;
    }
  }
  

