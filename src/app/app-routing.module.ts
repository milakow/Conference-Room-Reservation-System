import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationComponent } from './organization/organization.component';
import { ConferenceRoomComponent } from './conference-room/conference-room.component';
import { ReservationComponent } from './reservation/reservation.component';


const routes: Routes = [
  { path: 'organization/all', component: OrganizationComponent },
  { path: 'conferenceRoom/all', component: ConferenceRoomComponent},
  { path: 'reservation/all', component: ReservationComponent },
  { path: 'organization//${reservationId}/addReservationToOrganization/${organizationId}', component: ReservationComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
