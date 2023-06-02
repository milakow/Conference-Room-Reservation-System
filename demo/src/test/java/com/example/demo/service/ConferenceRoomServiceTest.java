package com.example.demo.service;

import com.example.demo.model.ConferenceRoom;
import com.example.demo.model.Organization;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ConferenceRoomRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConferenceRoomServiceTest {

    private Validator validator;

    @Mock
    private ConferenceRoomRepository conferenceRoomRepository;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ConferenceRoomService conferenceRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getListAllConferenceRooms() {
        // given
        List<ConferenceRoom> conferenceRooms = new ArrayList<>();
        conferenceRooms.add(new ConferenceRoom(1, "Room1", "Identifier1", 1, true, null));
        conferenceRooms.add(new ConferenceRoom(2, "Room2", "Identifier2", 2, true, null));
        when(conferenceRoomRepository.findAll()).thenReturn(conferenceRooms);

        // when
        List<ConferenceRoom> result = conferenceRoomService.listAllConferenceRooms();

        // then
        assertEquals(2, result.size());
        assertEquals("Room1", result.get(0).getName());
        assertEquals("Room2", result.get(1).getName());
        verify(conferenceRoomRepository).findAll();
    }

    @Test
    void getConferenceRoomByIdShouldReturnConferenceRoomIfExists() {
        // given
        long id = 1;
        ConferenceRoom conferenceRoom = new ConferenceRoom(id, "Room1", "Identifier1", 1, true, null);
        when(conferenceRoomRepository.existsById(id)).thenReturn(true);
        when(conferenceRoomRepository.findById(id)).thenReturn(Optional.of(conferenceRoom));

        // when
        ConferenceRoom result = conferenceRoomService.getConferenceRoomById(id);

        // then
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository).findById(id);
    }

    @Test
    void getConferenceRoomByIdShouldThrowException() {
        // given
        long id = 2;
        when(conferenceRoomRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.getConferenceRoomById(id));
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository, never()).findById(id);
    }

    @Test
    void deleteConferenceRoomShouldDeleteIfExists() {
        // given
        long id = 1;
        when(conferenceRoomRepository.existsById(id)).thenReturn(true);

        // when
        conferenceRoomService.deleteConferenceRoom(id);

        // then
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository).deleteById(id);
    }

    @Test
    void deleteConferenceRoomShouldThrowException() {
        // given
        long id = 1;
        when(conferenceRoomRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.deleteConferenceRoom(id));
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository, never()).deleteById(id);
    }

    @Test
    void addReservationToConferenceRoomShouldAddReservation() {
        // given
        long reservationId = 1;
        long conferenceRoomId = 2;
        ConferenceRoom conferenceRoom = new ConferenceRoom(conferenceRoomId, "Room1", "Identifier1", 1, true, null);
        Reservation reservation = new Reservation(reservationId, "Reserv100", LocalDateTime.parse("2023-06-03T08:00:00"), LocalDateTime.parse("2023-06-03T09:00:00"));
        when(conferenceRoomRepository.existsById(conferenceRoomId)).thenReturn(true);
        when(conferenceRoomRepository.findById(conferenceRoomId)).thenReturn(Optional.of(conferenceRoom));
        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        when(conferenceRoomRepository.save(conferenceRoom)).thenReturn(conferenceRoom);

        // when
        conferenceRoomService.addReservationToConferenceRoom(reservationId, conferenceRoomId);

        // then
        assertEquals(conferenceRoom, reservation.getConferenceRoom());
        verify(conferenceRoomRepository).existsById(conferenceRoomId);
        verify(conferenceRoomRepository).findById(conferenceRoomId);
        verify(reservationService).getReservationById(reservationId);
        verify(conferenceRoomRepository).save(conferenceRoom);
    }

    @Test
    void addConferenceRoomShouldAddConferenceRoom() {
        // given
        ConferenceRoom conferenceRoom = new ConferenceRoom(1, "Room1", "Identifier1", 1, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        when(conferenceRoomRepository.save(conferenceRoom)).thenReturn(conferenceRoom);

        // when
        conferenceRoomService.addConferenceRoom(conferenceRoom);

        // then
        verify(conferenceRoomRepository).existsById(conferenceRoom.getId());
        verify(conferenceRoomRepository).save(conferenceRoom);
    }

    @Test
    void addConferenceRoomWithWrongLengthOfName(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom(2, "X", "Identifier1", 1, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

        //then
        softAssertions.assertThat( violations.toString()).contains("Conference room name length should be between 2 and 20 characters.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }
    @Test
        void addConferenceRoomWithEmptyName(){
            //given
            ConferenceRoom conferenceRoom = new ConferenceRoom(2, "", "Identifier1", 1, true, null);
            when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
            SoftAssertions softAssertions = new SoftAssertions();

            //when
            Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

            //then
            softAssertions.assertThat( violations.toString()).contains("Conference room name cannot be blank.");
            softAssertions.assertThat(2).isEqualTo(violations.size());
            softAssertions.assertAll();
        }

    @Test
    void addConferenceRoomWithWrongLengthOfIdentifier(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom(2, "XXX", "Identifier1forConferenceRoomNamesXXX", 1, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

        //then
        softAssertions.assertThat( violations.toString()).contains("Conference room identifier length should be between 2 and 20 characters.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }
    @Test
    void addConferenceRoomWithEmptyIdentifier(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom(2, "XXX", "", 1, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

        //then
        softAssertions.assertThat( violations.toString()).contains("Conference room identifier cannot be blank.");
        softAssertions.assertThat(2).isEqualTo(violations.size());
        softAssertions.assertAll();
    }
    @Test
    void addConferenceRoomWithNegativeLevel(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom(2, "XXX", "Id1", -2, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

        //then
        softAssertions.assertThat( violations.toString()).contains("Level value must be at least 0.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }

    @Test
    void addConferenceRoomWithWrongLevel(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom(2, "XXX", "Id1", 15, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(false);
        SoftAssertions softAssertions = new SoftAssertions();

        //when
        Set<ConstraintViolation<ConferenceRoom>> violations = validator.validate(conferenceRoom);

        //then
        softAssertions.assertThat( violations.toString()).contains("Level value must be max 10.");
        softAssertions.assertThat(1).isEqualTo(violations.size());
        softAssertions.assertAll();
    }


    @Test
    void addConferenceRoomShouldThrowExceptionIfConferenceRoomExists() {
        // given
        ConferenceRoom conferenceRoom = new ConferenceRoom(1, "Room1", "Identifier1", 1, true, null);
        when(conferenceRoomRepository.existsById(conferenceRoom.getId())).thenReturn(true);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.addConferenceRoom(conferenceRoom));
        verify(conferenceRoomRepository).existsById(conferenceRoom.getId());
        verify(conferenceRoomRepository, never()).save(conferenceRoom);
    }

    @Test
    void updateConferenceRoomShouldUpdateConferenceRoomIfExists() {
        // given
        long id = 1;
        ConferenceRoom updatedConferenceRoom = new ConferenceRoom(id, "UpdatedRoom", "UpdatedIdentifier", 2, false, null);
        when(conferenceRoomRepository.existsById(id)).thenReturn(true);
        when(conferenceRoomRepository.save(updatedConferenceRoom)).thenReturn(updatedConferenceRoom);

        // when
        conferenceRoomService.updateConferenceRoom(id, updatedConferenceRoom);

        // then
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository).save(updatedConferenceRoom);
    }

    @Test
    void updateConferenceRoomShouldThrowExceptionIfConferenceRoomDoesNotExist() {
        // given
        long id = 1;
        ConferenceRoom updatedConferenceRoom = new ConferenceRoom(id, "UpdatedRoom", "UpdatedIdentifier", 2, false, null);
        when(conferenceRoomRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.updateConferenceRoom(id, updatedConferenceRoom));
        verify(conferenceRoomRepository).existsById(id);
        verify(conferenceRoomRepository, never()).save(updatedConferenceRoom);
    }


}