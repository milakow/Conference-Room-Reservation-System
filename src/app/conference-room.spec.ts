import { ConferenceRoom, NumberOfPlaces } from './conference-room';

describe('ConferenceRoom', () => {
  it('should create an instance', () => {
    const numberOfPlaces = new NumberOfPlaces(0, 0);
    expect(new ConferenceRoom(0, '', '', 0,  true, numberOfPlaces)).toBeTruthy();
  });
});
