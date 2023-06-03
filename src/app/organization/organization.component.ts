import { Component, OnInit } from '@angular/core';
import { Organization } from '../organization';
import { OrganizationService } from '../organization.service';

@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styleUrls: ['./organization.component.css']
})
export class OrganizationComponent implements OnInit {
organizations: Organization[] = [];
newOrganization: Organization = new Organization('');
isUpdating: boolean = false;
updatedName: string = '';
updateOrganizationId: number = 0; // Dodaj zmienną updateOrganizationId


constructor(private service: OrganizationService) {}

ngOnInit(): void {
  this.loadOrganizations();
}

loadOrganizations(): void {
  this.service.getAllOrganziations().subscribe((list: Organization[]) => {
    this.organizations = list;
  })
}

createOrganization() :void {
  this.service.addOrganziation(this.newOrganization).subscribe(() => {
    this.loadOrganizations(); // Reload the list of organizations after creating a new one
    this.resetForm(); // Reset the form fields
  });
}

deleteOrganization(idToDelete: number) {
  this.service.deleteOrganization(idToDelete).subscribe(() => {
    this.loadOrganizations();
  });
}

updateOrganization(idToUpdate: number, updatedName: string) {
  const updatedOrganization: Organization = {
    id: idToUpdate,
    name: updatedName
  };

  this.service.updateOrganization(idToUpdate, updatedOrganization).subscribe(() => {
    this.loadOrganizations();
    this.cancelEdit();
  });
}

cancelEdit(): void {
  this.isUpdating = false; // Ustaw wartość isEditing na false
  // ...
}

resetForm() :void {
  this.newOrganization = new Organization(''); // Reset the newOrganziation object
}

}
