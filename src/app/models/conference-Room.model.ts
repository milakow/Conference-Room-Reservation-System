import { NumberOfPlaces } from "../conference-room";

export interface ConferenceRoom {
  id: number;
  name: string;
  identifier: string;
  level: number;
  availability: boolean;
  numberOfPlaces: NumberOfPlaces
  }