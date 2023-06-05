package com.example.demo.service;

import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Reservation;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.ReservationRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ConferenceRoomService conferenceRoomService;

    @Resource
    @InjectMocks
    private ReservationService reservationService;
    @BeforeEach
    void onInit() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAllReservations() {
        // given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, "RES1", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        reservations.add(new Reservation(2, "RES2", LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
        when(reservationRepository.findAll()).thenReturn(reservations);

        // when
        List<Reservation> result = reservationService.listAllReservations();

        // then
        assertEquals(2, result.size());
        assertThat(result.get(0).getIdentifier()).isEqualTo("RES1");
        assertThat(result.get(1).getId()).isEqualTo(2);
        verify(reservationRepository).findAll();
    }

    @Test
    void getReservationByIdShouldReturnReservationIfExists() {
        // given
        long id = 1;
        Reservation reservation = new Reservation(id, "Res012", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        when(reservationRepository.existsById(id)).thenReturn(true);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        // when
        Reservation result = reservationService.getReservationById(id);

        // then
        verify(reservationRepository).existsById(id);
        verify(reservationRepository).findById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getReservationByIdShouldThrowExceptionIfReservationDoesNotExist() {
        // given
        long id = 1;
        when(reservationRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> reservationService.getReservationById(id));
        verify(reservationRepository).existsById(id);
        verify(reservationRepository, never()).findById(id);
    }

    @Test
    void deleteReservationShouldDeleteIfExists() {
        // given
        long id = 1;
        when(reservationRepository.existsById(id)).thenReturn(true);

        // when
        reservationService.deleteReservation(id);

        // then
        verify(reservationRepository).existsById(id);
        verify(reservationRepository).deleteById(id);
    }

    @Test
    void deleteReservationShouldThrowExceptionIfReservationDoesNotExist() {
        // given
        long id = 1;
        when(reservationRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> reservationService.deleteReservation(id));
        verify(reservationRepository).existsById(id);
        verify(reservationRepository, never()).deleteById(id);
    }

    @Test
    void checkReservationShouldThrowExceptionIfStartDateIsInPast() {
        // given
        long reservationId = 1;
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 1, 12, 0);
        Reservation reservation = new Reservation(reservationId, "ResIdentifier", startDate, endDate);

        // when, then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationService.checkReservation(reservation));
        assertEquals("Reservation can't start in the past", exception.getMessage());
    }

    @Test
    void checkReservationShouldThrowExceptionIfEndDateIsBeforeStartDate() {
        // given
        long reservationId = 1;
        LocalDateTime startDate = LocalDateTime.of(2023, 7, 1, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 1, 1, 12, 0);
        Reservation reservation = new Reservation(reservationId, "ResIdentifier", startDate, endDate);

        // when, then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationService.checkReservation(reservation));
        assertEquals("Start of reservation must be before its end.", exception.getMessage());
    }

    @Test
    void checkReservationShouldReturnReservationIfValid() {
        // given
        long reservationId = 1;
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 10, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 10, 12, 0);
        Reservation reservation = new Reservation(reservationId, "ResIdentifier", startDate, endDate);

        // when
        Reservation result = reservationService.checkReservation(reservation);

        // then
        assertEquals(reservation, result);
    }

    @Test
    void updateReservationShouldUpdateExistingReservation() {
        // given
        long existingReservationId = 1;
        Reservation existingReservation = new Reservation(existingReservationId, "ResIdentifier", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Reservation updatedReservation = new Reservation(existingReservationId, "UpdatedResIdentifier", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        when(reservationRepository.existsById(existingReservationId)).thenReturn(true);

        // when
        reservationService.updateReservation(existingReservationId, updatedReservation);

        // then
        verify(reservationRepository).save(updatedReservation);
    }

    @Test
    void updateReservationShouldThrowExceptionIfReservationDoesNotExist() {
        // given
        long nonExistingReservationId = 1;
        Reservation updatedReservation = new Reservation(nonExistingReservationId, "UpdatedResIdentifier", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        when(reservationRepository.existsById(nonExistingReservationId)).thenReturn(false);

        // when, then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationService.updateReservation(nonExistingReservationId, updatedReservation));
        assertEquals("Reservation with id " + nonExistingReservationId + " does not exist.", exception.getMessage());
    }



}