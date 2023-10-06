package dev.jonas;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Class for train-departures. Has the fields departure-time, delay of departure, line, destination, train-number and track-number
 *
 * @author Jonas Birkeli
 * @version 1.0.0
 * @since 1.0.0
 */
public class trainDeparture {
  LocalDateTime departureTime;
  Duration delay;
  String line;
  String destination;
  int trainNumber;
  int track;

  /**
   * Constructor of the TrainDeparture. Needs parameter departure-time, line, trainnumber, destination and departure-track.
   *
   * @param departureTime  Scheduled time for departure of train.
   * @param line Scheduled line of train
   * @param trainNumber Number on train.
   * @param destination End-destination of train.
   * @param track Number of the departure-track.
   */
  public trainDeparture(LocalDateTime departureTime, String line, int trainNumber, String destination, int track) {
    setDepartureTime(departureTime);
    setLine(line);
    setTrainNumber(trainNumber);
    setDestination(destination);
    setTrack(track);
    setDelay(Duration.ZERO);
  }

  /**
   * Constructs the class trainDeparture with undeclared fields.
   */
  public trainDeparture() {
    setDepartureTime(LocalDateTime.now());
    setLine("");
    setDestination("");
    setTrainNumber(-1);
    setTrack(-1);
    setDelay(Duration.ZERO);
  }

  /**
   * Gets the scheduled departure-time of the train departure.
   *
   * @return Scheduled time for departure of train.
   */
  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Sets a time of departure of the train. Given as a timestamp using LocalDateTime.
   *
   * @see LocalDateTime
   * @param departureTime Scheduled time for departure of train.
   */
  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * Gets the departure-delay of the train departure. Needs to be added to the scheduled departure-time. Cannot be negative.
   *
   * @return Delay of train departure.
   */
  public Duration getDelay() {
    return delay;
  }

  /**
   * Sets the departure-delay of the train departure. Must not be a negative duration.
   *
   * @param delay Delay of train. Duration.ZERO if no delay
   */
  public void setDelay(Duration delay) {
    this.delay = delay;
  }

  /**
   * Gets the scheduled line of the train. Might be empty if not decalred.
   *
   * @return Line of train as a full or empty string.
   */
  public String getLine() {
    return line;
  }

  /**
   * Sets the line of the train. Empty if not decalred.
   *
   * @param line Scheduled line of train
   */
  public void setLine(String line) {
    this.line = line;
  }

  /**
   * Gets the train-number. Value is non-zero, but might be -1 if not declared. Number is unique to this train.
   *
   * @return Is greater than 0 or -1
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Sets the train-number. Set as -1 if you cannot declare this yet. Number must be unique to this train.
   *
   * @param trainNumber Number must be greater than 0 or -1 if not declared yet.
   */
  public void setTrainNumber(int trainNumber) {
    this.trainNumber = trainNumber;
  }

  /**
   * Gets the destination of the train. Empty means not decalred.
   *
   * @return End-destination of train given in the format: "Start - End" or empty if not declared.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the train destination. Empty means not declared.
   *
   * @param destination End-destination of train given in the format: "Start - End"
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Gets the track where the train departs from. Non-zero int for declared track. -1 means undeclared.
   *
   * @return Value is non-zero or -1 if undeclared.
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the departure-track of the train. Non-zero int for declared track, -1 for undeclared.
   *
   * @param track Departure-track value.
   */
  public void setTrack(int track) {
    this.track = track;
  }
}
