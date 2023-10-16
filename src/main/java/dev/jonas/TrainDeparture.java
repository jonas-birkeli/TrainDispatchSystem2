package dev.jonas;

/**
 * Class for train-departures. Leave no constructor for randomly assigned train-number.
 * Methods in class:
 * {@link #getDepartureTime()}<br>
 * {@link #setDepartureTime(int[])}<br>
 * {@link #getDestination()}<br>
 * {@link #setDestination(String)}<br>
 * {@link #getLine()}<br>
 * {@link #setLine(String)}<br>
 * {@link #getDelay()}<br>
 * {@link #setDelay(int[])}<br>
 * {@link #getTrack()}<br>
 * {@link #setTrack(int)}<br>
 * {@link #getTrainNumber()}<br>
 * {@link #setTrainNumber(int)}<br>
 *
 * @author Jonas Birkeli
 * @version 1.0.1
 * @since 1.0.0
 */
public class TrainDeparture {
  private int[] departureTime;
  private int[] delay;
  private String line;
  private String destination;
  private int trainNumber;
  private int track;

  /**
   * Constructor of the TrainDeparture.
   * Needs parameter departure-time, line, trainnumber, destination and departure-track.
   *
   * @param departureTime  Scheduled time for departure of train.
   * @param line Scheduled line of train
   * @param trainNumber Number on train.
   * @param destination End-destination of train.
   * @param track Number of the departure-track.
   */
  public TrainDeparture(
      int[] departureTime,
      String line,
      int trainNumber,
      String destination,
      int track
  ) {
    setDepartureTime(departureTime);
    setLine(line);
    setTrainNumber(trainNumber);
    setDestination(destination);
    setTrack(track);
    setDelay(new int[]{0, 0});
  }

  /**
   * Constructor of the TrainDeparture with set fields to default values.
   */
  public TrainDeparture() {
    setDepartureTime(new int[]{0, 0});
    setLine("");
    setDestination("");
    setTrainNumber(-1);
    setTrack(-1);
    setDelay(new int[]{0, 0});
  }

  /**
   * Constructor of the TrainDeparture with a list of fields in the format:
   * "{
   * departureHour,
   * departureMinute,
   * departureLinedeparture,
   * Destination,
   * departureTrack,
   * number
   * }".
   *
   * @param fields List of fields in the format:
   *               "{
   *               departureHour,
   *               departureMinute,
   *               departureLine,
   *               departureDestination,
   *               departureTrack,
   *               number
   *               }".
   */
  public TrainDeparture(String[] fields) {
    setDepartureTime(new int[]{Integer.parseInt(fields[0]), Integer.parseInt(fields[1])});
    setLine(fields[2]);
    setDestination(fields[3]);
    setTrack(Integer.parseInt(fields[4]));
    setTrainNumber(Integer.parseInt(fields[5]));
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
   * Sets the departure-delay of the train departure.
   * Must not be a negative duration.
   *
   * @param delay Delay of train. Duration.ZERO if no delay
   */
  public void setDelay(int[] delay) {
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
   * Gets the train-number.
   * Value is non-zero, but might be -1 if not declared.
   * Number is unique to this train.
   *
   * @return Is greater than 0 or -1
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Sets the train-number.
   * Set as -1 if you cannot declare this yet.
   * Number must be unique to this train.
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
   * Gets the track where the train departs from.
   * Non-zero int for declared track. -1 means undeclared.
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
