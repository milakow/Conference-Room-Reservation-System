package com.example.demo.service;

import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> listAllOrganizations(){
        return organizationRepository.findAll();
    };

    public Optional<Organization> getOrganizationById(long id){
        if (organizationRepository.existsById(id)) {
            return organizationRepository.findById(id);
        } else throw new IllegalArgumentException("Organization with id " + id + " does not exist.");
    }

    public void deleteOrganization(long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
        } else throw new IllegalArgumentException("Organization with id " + id + " does not exist.");
    }

    public void addOrganization(Organization organization){
        if(!organizationRepository.existsById(organization.getId())){
            organizationRepository.save(organization);
        } else throw new IllegalArgumentException("Organization with id " + organization.getId() + " already exists.");
    }

    public void updateOrganization(long id, Organization updatedOrganization){
        if(organizationRepository.existsById(id)){
            updatedOrganization.setId(id);
            organizationRepository.save(updatedOrganization);
        } else throw new IllegalArgumentException("Organization with id " + id + " does not exist.");
    }

}
