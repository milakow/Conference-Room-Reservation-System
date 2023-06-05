import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConferenceRoomService } from './conference-room.service';

describe('ConferenceRoomService', () => {
  let service: ConferenceRoomService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports: [HttpClientTestingModule]});
    service = TestBed.inject(ConferenceRoomService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
