package dev.jonas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IoTest {

  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {

  }

  @Test
  void testPositiveEquals() {
    TrainDeparture trainDeparture = new TrainDeparture();
    int[] time = new int[]{23, 56};
    trainDeparture.setDepartureTime(time);
    assertEquals(time, trainDeparture.getDepartureTime(), "DepartureTime should be equal");
  }

  @Test
  void testNegativeEquals() {
    TrainDeparture trainDeparture = new TrainDeparture();
    int[] time = new int[]{23, 56};
    int[] time2 = new int[]{23, 57};
    trainDeparture.setDepartureTime(time);
    assertNotEquals(time2, trainDeparture.getDepartureTime(), "DepartureTime should be equal");
  }
}