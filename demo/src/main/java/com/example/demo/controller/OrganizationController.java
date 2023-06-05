package com.example.demo.controller;

import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "http://localhost:4200")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/all")
    public List<Organization> listAllOrganizations() {
        return organizationService.listAllOrganizations();
    }

    @GetMapping("/{id}")
    public Organization getOrganizationById(@PathVariable long id) {
        return organizationService.getOrganizationById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable long id) {
        organizationService.deleteOrganization(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{reservationId}/addReservationToOrganization/{organizationId}")
    public void addReservationToOrganization(@PathVariable long reservationId, @PathVariable long organizationId) {
        organizationService.addReservationToOrganization(reservationId, organizationId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void addOrganization(@RequestBody Organization organization) {
        organizationService.addOrganization(organization);
    }

    @PutMapping("/update/{id}")
    public void updateOrganization(@PathVariable long id, @RequestBody Organization updatedOrganization) {
        organizationService.updateOrganization(id, updatedOrganization);
    }

}
