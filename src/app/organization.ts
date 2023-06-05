import { Reservation } from "./reservation";

export class Organization {
    "id": number;
    "name": string;
    "reservationList": Reservation[];
    constructor(id: number, name:string){
        this.id = id;
        this.name = name;
        this.reservationList = [];
    }
}
