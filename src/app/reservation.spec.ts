import { Reservation } from './reservation';

describe('Reservation', () => {
  it('should create an instance', () => {
    expect(new Reservation(0, '', new Date(), new Date())).toBeTruthy();
  });
});
