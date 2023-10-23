package dev.jonas;


import java.util.Objects;

/**
 * The {@code TrainDeparture} class represents a train on a station.
 * All {@code TrainDeparture}s has a departuretime, delay, line, destination and track.
 *
 * <p>The {@code TrainDeparture} class has three constructors:
 * <blockquote><pre>
 *   TrainDeparture trainDeparture = new TrainDeparture();
 *   TrainDeparture trainDeparture = new TrainDeparture({23, 56}, "L1", "Hamburg", 3);
 *   TrainDeparture trainDeparture = new TrainDeparture({23, 56, "L1", "Hamburg", 3});
 * </pre></blockquote>
 *
 * @author Jonas Birkeli
 * @version 1.1.0
 * @since 1.0.0
 */
public class TrainDeparture {
  private int[] departureTime;
  private int[] delay;
  private String line;
  private String destination;
  private int track;

  /**
   * Constructs a new {@code TrainDeparture} with the given parameters.
   *
   * @param departureTime  Scheduled time for departure of train.
   * @param line Scheduled line of train
   * @param destination End-destination of train.
   * @param track Number of the departure-track.
   */
  public TrainDeparture(
      int[] departureTime,
      String line,
      String destination,
      int track
  ) {
    setDepartureTime(departureTime);
    setLine(line);
    setDestination(destination);
    setTrack(track);
    setDelay(new int[]{0, 0});
  }

  /**
   * Constructs a new {@code TrainDeparture} with default fields.
   *
   * @since 1.0.1
   */
  public TrainDeparture() {
    setDepartureTime(new int[]{0, 0});
    setLine("");
    setDestination("");
    setTrack(-1);
    setDelay(new int[]{0, 0});
  }

  /**
   * Constructor of the TrainDeparture with a list of fields in the format:<br>
   * "{<br>
   * departureHour,
   * departureMinute,<br>
   * departureLinedeparture,<br>
   * Destination,<br>
   * departureTrack<br>
   * }".
   *
   * @param fields List of fields in the format "{HH, mm, line, destination, track}".
   *               Cannot be null.
   *
   * @since 1.1.0
   */
  public TrainDeparture(String[] fields) {
    if (fields == null) {
      return;
    }
    setDepartureTime(new int[]{Integer.parseInt(fields[0]), Integer.parseInt(fields[1])});
    setLine(fields[2]);
    setTrack(Integer.parseInt(fields[3]));
    setDestination(fields[4]);
    setDelay(new int[]{0, 0});
  }

  /**
   * Gets the scheduled departure-time of the train departure.
   *
   * @return Formatted departure-time as am integer array of "{HH, mm}". Cannot be null.
   */
  public int[] getDepartureTime() {
    return departureTime;
  }

  /**
   * Sets a time of departure of the train given by an integer array.
   *
   * @param departureTime Needs to be in the format "{HH, mm}". Cannot be null.
   */
  public void setDepartureTime(int[] departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * Gets the departure-delay of the train departure.
   * Needs to be added to the scheduled departure-time. Cannot be negative.
   *
   * @return Delay of train departure.
   */
  public int[] getDelay() {
    return delay;
  }

  /**
   * Sets the {@code delay} of the train departure.
   * Must not be over 24 hours or negative.
   *
   * @param delay Delay in the format "{HH, mm}". Cannot be null.
   */
  public void setDelay(int[] delay) {
    if (delay[0] < 24 && delay[1] < 60 && delay[0] >= 0 && delay[1] >= 0) {
      this.delay = delay;
    } else {
      this.delay = new int[]{0, 0};
    }
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
   * Sets the {@code Line} of the train. If empty or null, line is set to empty.
   * For example: "L1".
   *
   * @param line Scheduled line of train
   */
  public void setLine(String line) {
    this.line = Objects.requireNonNullElse(line, "");
  }

  /**
   * Gets the destination of the train. Empty means not decalred.
   *
   * @return End-destination of train given in the format: "Start - End" or empty if not declared.
   */
  public String getDestination() {
    return destination;
  }

  /*** Sets the {@code Destination}. If empty or null, destination is set to empty.
   *
   * @param destination End-destination of train-departure. Example: "Lillehammer"
   *
   * @since 1.0.0
   */
  public void setDestination(String destination) {
    this.destination = Objects.requireNonNullElse(destination, "");
  }

  /**
   * Gets the {@code track} where the train departs from.
   * Non-zero int for declared track.
   * Value is -1 if undeclared.
   *
   * @return Value is positive non-zero or -1 if undeclared.
   *
   * @since 1.0.0
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the {@code track} where the train departs from.
   * {@code Track} must be between 1 and 99.
   * Value set to -1 if conditions are not met.
   *
   * @param track Track of departure. Example: 3
   * @since 1.0.0
   */
  public void setTrack(int track) {
    if (track <= 0 || track > 99) {
      this.track = -1;
    } else {
      this.track = track;
    }
  }

  @Override
  public String toString() {
    StringBuilder objectInformation;
    objectInformation = new StringBuilder();
    objectInformation
        .append(getLine())
        .append(" ")
        .append(getTrack())
        .append(" ")
        .append(getDepartureTime()[0])
        .append(":")
        .append(getDepartureTime()[1])
        .append(" ")
        .append(getDestination());
    return objectInformation.toString();
  }
}
