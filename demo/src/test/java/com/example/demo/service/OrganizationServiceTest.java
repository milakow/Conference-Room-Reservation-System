package com.example.demo.service;

import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrganizationServiceTest {

    OrganizationRepository organizationRepository;

    @Resource
    @InjectMocks
    OrganizationService organizationService;
    //get all organizations

    @BeforeEach
    void onInit() {
        MockitoAnnotations.openMocks(this);
        long id = 1;
        String name = "XXX";
        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);
        organizationRepository.save(organization);
    }

    @Test
    void getOrganizationById(){
        //given
        long id = 1;

        //when
        Optional<Organization> result = organizationService.getOrganizationById(id);

        //then
        assertEquals(result.get(), organizationRepository.findById(id));

    }
}
//given

//when

//then
