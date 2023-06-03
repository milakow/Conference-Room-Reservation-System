import { Component } from '@angular/core';
import { Organization } from './organization';
import { OrganizationService } from './organization.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'finalProjectFrontend';
}

  // public organizations: Organization[];

  // constructor(private service: OrganizationService){}

  // public getOrganizations(): void{
  //   this.service.getOrganziations().subs
  // }

