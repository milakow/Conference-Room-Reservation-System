package com.example.demo.service;

import com.example.demo.model.Organization;
import com.example.demo.model.Reservation;
import com.example.demo.repository.OrganizationRepository;
import jakarta.annotation.Resource;
import jakarta.validation.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {
    private Organization organization;
    private Validator validator;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ReservationService reservationService;

    @Resource
    @InjectMocks
    private OrganizationService organizationService;
    //get all organizations

    @BeforeEach
    void onInit() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getListAllOrganizations() {
        //given
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(new Organization(1, "Organization1"));
        organizationList.add(new Organization(2, "Organization2"));
        organizationList.add(new Organization(3, "Organization3"));
        when(organizationRepository.findAll()).thenReturn(organizationList);

        //when
        List<Organization> result = organizationService.listAllOrganizations();
//soft assertions
        //then
        assertEquals(3, result.size());
        assertEquals("Organization2", result.get(1).getName());
        assertThat(result.get(0).getName()).isEqualTo("Organization1");
        verify(organizationRepository).findAll();
    }

    @Test
    void getOrganizationByIdShouldReturnOrganisationIfExists() {
        // given
        long id = 1;
        Organization organization1 = new Organization(id, "organization1");
        when(organizationRepository.existsById(id)).thenReturn(true);
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization1));

        //when
        Organization result = organizationService.getOrganizationById(id);

        //then
//        assertEquals(result, organization1);
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).findById(id);
    }

    @Test
    void getOrganizationByIdShouldThrowException() {
        //given
        long id = 2;
        when(organizationRepository.existsById(id)).thenReturn(false);
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> organizationService.getOrganizationById(id));
        verify(organizationRepository).existsById(id);
        verify(organizationRepository, never()).findById(id);

    }

    @Test
    void deleteOrganizationShouldDeleteIfExists(){
        //given
        long id = 1;
        when(organizationRepository.existsById(id)).thenReturn(true);

        //when
        organizationService.deleteOrganization(id);

        //then
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).deleteById(id);
    }

    @Test
    void deleteOrganizationShouldThrowException(){
        //given
        long id = 1;
        when(organizationRepository.existsById(id)).thenReturn(false);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> organizationService.deleteOrganization(id));
        verify(organizationRepository).existsById(id);
        verify(organizationRepository, never()).deleteById(id);

    }

    @Test
    void addOrganizationShouldAddOrganization(){
        //given
        long id = 1;
        Organization organization = new Organization(id, "Organization1");
        when(organizationRepository.existsById(id)).thenReturn(false);

        //when
        organizationService.addOrganization(organization);

        //then
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).save(organization);
    }

    @Test
    void addOrganizationShouldThrowExceptionIfOrganizationAlreadyExists(){
        //given
        long id = 1;
        Organization organization = new Organization(id, "Organization1");
        organizationRepository.save(organization);
        when(organizationRepository.existsById(id)).thenReturn(true);

        //when
        //then
        Exception e = assertThrows(IllegalArgumentException.class, () -> organizationService.addOrganization(organization));
        assertEquals( "Organization with id 1 already exists.", e.getMessage());
    }

    @Test
    void addOrganizationWithWrongNameLengthShouldThrowException(){
        long id = 1;
        Organization organization = new Organization(id, "O");
        when(organizationRepository.existsById(id)).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<Organization>> violations = validator.validate(organization);

        //then
        softAssertions.assertThat( violations.toString()).contains("Name length should be between 2 and 20 characters.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }

    @Test
    void addOrganizationWithBlankNameLengthShouldThrowException(){
        long id = 1;
        Organization organization = new Organization();
        organization.setId(id);
        when(organizationRepository.existsById(id)).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<Organization>> violations = validator.validate(organization);

        //then
        softAssertions.assertThat( violations.toString()).contains("Name cannot be blank.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }


    @Test
    void addReservationToOrganizationShouldAddReservation(){
        //given
        long reservationId = 1;
        long organizationId = 2;
        Organization organization = new Organization(organizationId, "Org1");
        Reservation reservation = new Reservation(reservationId, "Res1", LocalDateTime.parse("2023-06-03T08:00:00"), LocalDateTime.parse("2023-06-03T09:00:00"));
        when(organizationRepository.existsById(organizationId)).thenReturn(true);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        //when
        organizationService.addReservationToOrganization(reservationId, organizationId);

        //then
        verify(organizationRepository, times(1)).findById(organizationId);
        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(organizationRepository, times(1)).save(organization);
    }


    @Test
    void updateOrganizationShouldUpdateOrganization(){
        //given
        long id = 1;
        Organization organization = new Organization(id, "Org");
        organizationRepository.save(organization);
        Organization newOrganization = new Organization(0, "Updated organization");
        when(organizationRepository.existsById(id)).thenReturn(true);

        //when
        organizationService.updateOrganization(id, newOrganization);

        //then
        assertEquals(organization.getId(), newOrganization.getId());
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).save(newOrganization);
    }

    @Test
    void updateOrganizationShouldThrowException() {
        // given
        long id = 1;
        Organization updatedOrganization = new Organization(id, "Updated Org");
        when(organizationRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> organizationService.updateOrganization(id, updatedOrganization));
        verify(organizationRepository).existsById(id);
        verify(organizationRepository, never()).save(updatedOrganization);
    }
}



