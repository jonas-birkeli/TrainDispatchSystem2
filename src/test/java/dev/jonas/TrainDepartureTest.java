package dev.jonas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TrainDepartureTest {

  @Test
  void getDepartureTime_DateMatchesDefaultDeparture_True() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertEquals(0, trainDeparture.getDepartureTime()[0]);
    assertEquals(0, trainDeparture.getDepartureTime()[1]);
  }

  @Test
  void setDepartureTime_DateMatchesSetDeparture_True() {
    // Positive test
    TrainDeparture trainDeparture = new TrainDeparture();
    trainDeparture.setDepartureTime(new int[]{23, 56});
    assertEquals(23, trainDeparture.getDepartureTime()[0]);
    assertEquals(56, trainDeparture.getDepartureTime()[1]);
  }

  @Test
  void getDelay() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertEquals(0, trainDeparture.getDelay()[0]);
    assertEquals(0, trainDeparture.getDelay()[1]);

  }

  @Test
  void setDelay() {
    TrainDeparture trainDeparture = new TrainDeparture();
    trainDeparture.setDelay(new int[]{23, 56});
    assertEquals(23, trainDeparture.getDelay()[0]);
    assertEquals(56, trainDeparture.getDelay()[1]);
  }

  @Test
  void getLine() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertEquals("", trainDeparture.getLine());
  }

  @Test
  void setLine() {
    TrainDeparture trainDeparture = new TrainDeparture();
    trainDeparture.setLine("L1");
    assertEquals("L1", trainDeparture.getLine());
  }

  @Test
  void getDestination() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertEquals("", trainDeparture.getDestination());
  }

  @Test
  void setDestination() {
    TrainDeparture trainDeparture = new TrainDeparture();
    trainDeparture.setDestination("Lillestrøm");
    assertEquals("Lillestrøm", trainDeparture.getDestination());
  }

  @Test
  void getTrack() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertEquals(-1, trainDeparture.getTrack());
  }

  @Test
  void setTrack() {
    TrainDeparture trainDeparture = new TrainDeparture();
    trainDeparture.setTrack(3);
    assertEquals(3, trainDeparture.getTrack());
  }

  @Test
  void testToString_MatchingToStringResult_False() {
    TrainDeparture trainDeparture = new TrainDeparture();
    assertNotEquals("00:00", trainDeparture.toString());
  }
}