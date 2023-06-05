import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Organization } from './organization';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};


@Injectable({
  providedIn: 'root'
})
export class OrganizationService {
  // private apiServerUrl = '';
  private getAllOrganziationsLink = 'http://localhost:8080/organization/all';
  private getOrganziationByIdLink = 'http://localhost:8080/organization/';
  private deleteOrganziationLink = 'http://localhost:8080/organization/';
  private addOrganziationLink = 'http://localhost:8080/organization/new';
  private updateOrganziationLink = 'http://localhost:8080/organization/update/';
  private apiUrl = 'http://localhost:8080/organization';



  constructor(private http: HttpClient) { }

  public getAllOrganziations(): Observable<Organization[]> {
    return this.http.get<Organization[]>(this.getAllOrganziationsLink, httpOptions)
  }

  public getOrganziationById(id: number): Observable<Organization> {
    return this.http.get<Organization>(this.getOrganziationByIdLink + id, httpOptions)
  }

  public deleteOrganization(id: number): Observable<any> {
    return this.http.delete(this.deleteOrganziationLink + id, httpOptions)
  }

  public addReservationToOrganization(reservationId: number, organizationId: number){
    const url = `${this.apiUrl}/${reservationId}/addReservationToOrganization/${organizationId}`;
    return this.http.post<void>(url, httpOptions);
  }

  public createOrganization(organization: Organization): Observable<void> {
    return this.http.post<void>(this.addOrganziationLink, organization, httpOptions);
  }

  public addOrganziation(organization: Organization): Observable<void> {
    return this.http.post<void>(this.addOrganziationLink, organization, httpOptions)
  }

  public updateOrganization(id: number, organization: Organization): Observable<any> {
    return this.http.put(this.updateOrganziationLink + id, organization, httpOptions)
  }
}
