export class Reservation {
    "id": number;
    "identifier": string;
    "startDate": Date;
    "endDate": Date;
    constructor(id: number, identifier: string, startDate: Date, endDate: Date){
        this.id = id;
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
