import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Organization } from '../organization';
import { OrganizationService } from '../organization.service';
import { Router } from '@angular/router';
import { Reservation } from '../reservation';


@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styleUrls: ['./organization.component.css']
})
export class OrganizationComponent implements OnInit {
// @Output() selectOrganizationId: EventEmitter<number> = new EventEmitter<number>();
// orgIdForRes: number = 0;
organizations: Organization[] = [];
newOrganization: Organization = new Organization(0, '');
reservationList: Reservation[] = [];
isUpdating: boolean = false;
updatedName: string = '';
updateOrganizationId: number = 0; // Dodaj zmienną updateOrganizationId


constructor(private service: OrganizationService, private router: Router) {}

ngOnInit(): void {
  this.loadOrganizations();
}

loadOrganizations(): void {
  this.service.getAllOrganziations().subscribe((list: Organization[]) => {
    this.organizations = list;
  })
}

addReservationToOrganization(reservationId: number, organizationId: number): void {
  this.service.addReservationToOrganization(reservationId, organizationId).subscribe(() => {
  this.loadOrganizations();
})
}


createReservation(): void {
  // console.log(id);
  // this.orgIdForRes = id;
  // this.selectOrganizationId.emit(id);
  this.router.navigate(['reservation/all']);
}

createOrganization(): void {
  this.service.addOrganziation(this.newOrganization).subscribe(() => {
    this.loadOrganizations(); // Reload the list of organizations after creating a new one
    this.resetForm(); // Reset the form fields
  });
}

deleteOrganization(idToDelete: number): void {
  this.service.deleteOrganization(idToDelete).subscribe(() => {
    this.loadOrganizations();
  });
}

updateOrganization(idToUpdate: number, updatedName: string, reservationList: []): void {
  const updatedOrganization: Organization = {
    id: idToUpdate,
    name: updatedName,
    reservationList: this.reservationList
  };

  this.service.updateOrganization(idToUpdate, updatedOrganization).subscribe(() => {
    this.loadOrganizations();
    this.cancelEdit();
  });
}


cancelEdit(): void {
  this.isUpdating = false; // Ustawia wartość isEditing na false
  // ...
}

resetForm() :void {
  this.newOrganization = new Organization(0, ''); // Reset the newOrganziation object
}

}
