package dev.jonas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The {@code Interface} class represents the interface of the application.
 * Handles all user input and output, and has a state-machine to handle different modes.
 *
 * <p><br>
 *   The {@code Interface} class has one constructor:
 *   <blockquote><pre>
 *     Interface io = new Interface();
 *     io.start();
 *   </pre></blockquote>
 * </p>
 *
 * @author Jonas Birkeli
 * @version 1.2.0
 * @since 1.0.0
 */
@SuppressWarnings({"ClassWithoutLogger", "PublicMethodWithoutLogging",
  "WeakerAccess"})
public class Io {

  // Variables used in this class
  private int state;
  private boolean running;
  private ArrayList<TrainDeparture> trainDepartures;
  private TrainDeparture selectedTrainDeparture;
  private int[] currentTime;

  // Constants for the different modes
  private static final int SELECTMODE = 0;
  private static final int VIEW_TRAIN_DEPARTURE_MODE = 1;
  private static final int ADD_TRAIN_DEPARTURE_MODE = 2;
  private static final int ASSIGN_TRACK_MODE = 3;
  private static final int NOTIFY_DELAY_MODE = 4;
  private static final int SEARCH_TRAIN_BY_NUMBER_MODE = 5;
  private static final int SEARCH_TRAIN_BY_DESTINATION_MODE = 6;
  private static final int UPDATE_TIME_MODE = 7;
  private static final int QUIT_APPLICATION_MODE = 10;

  /**
   * Constructs a new {@code Interface} with default fields.
   *
   * @since 1.0.0
   */
  public Io() {
    state = SELECTMODE;
    trainDepartures = new ArrayList<>();
    selectedTrainDeparture = null;
    running = true;
    currentTime = new int[]{16, 37};
  }

  /**
   * Starts the interface and prints a menu of all options to the user. Will loop continuously until
   * the user quits the application.
   *
   * @see #showSelectModeMenu()
   * @see #showTrainDepartureMenu()
   * @see #addTrainDepartureMenu()
   * @see #exitToMenu()
   * @see #notifyDelayMenu()
   * @see #updateTime()
   * @see #quitApplication()
   * @see TrainDeparture
   * @since 1.0.0
   */
  public void start() {
    TrainDeparture train = new TrainDeparture(
        new int[]{16, 37},
        "L1",
        "Oslo S",
        1
    );
    trainDepartures.add(train);
    TrainDeparture train2 = new TrainDeparture(
        new int[]{18, 58},
        "L3",
        "Lillestrøm",
        2
    );
    trainDepartures.add(train2);

    while (running) {
      switch (state) {
        case SELECTMODE -> showSelectModeMenu();
        case VIEW_TRAIN_DEPARTURE_MODE -> showTrainDepartureMenu();
        case ADD_TRAIN_DEPARTURE_MODE -> addTrainDepartureMenu();
        case ASSIGN_TRACK_MODE -> assignTrackMenu();
        case NOTIFY_DELAY_MODE -> notifyDelayMenu();
        case SEARCH_TRAIN_BY_NUMBER_MODE -> searchTrainByNumberMenu();
        case SEARCH_TRAIN_BY_DESTINATION_MODE -> searchTrainByDestinationMenu();
        case UPDATE_TIME_MODE -> updateTime();
        case QUIT_APPLICATION_MODE -> quitApplication();
        default -> running = false;
      }
    }
  }

  /**
   * Menu to search for a train departure by train number.
   * Will ask user for index of train, and set the selected train to a refrenace to this train.
   *
   * @see #getValidInputFromUser(String)
   * @since 1.2.0
   */
  private void searchTrainByNumberMenu() {
    printNumeratedTrainDepartures();

    // Gets input from user
    String input = getValidInputFromUser("Enter train number: ");
    if (input.isEmpty()) { // Checks if input is empty
      exitToMenu();
      return;
    }
    int idx = Integer.parseInt(input);
    if (idx < 0 || idx >= trainDepartures.size()) {
      exitToMenu();
      return;
    }
    selectedTrainDeparture = trainDepartures.get(idx);

    exitToMenu();
  }


  /**
   * Menu to search for a train departure by destination.
   * Will ask user for index of train, and set the selected train to a refrenace to this train.
   *
   * @see #getValidInputFromUser(String)
   * @since 1.2.0
   */
  private void searchTrainByDestinationMenu() {
    printNumeratedTrainDepartures();

    // Gets input from user
    String input = getValidInputFromUser("Enter destination: ");
    if (input.isEmpty()) { // Checks if input is empty
      exitToMenu();
      return;
    }

    int idx = Integer.parseInt(input);

    if (idx < 0 || idx >= trainDepartures.size()) {
      exitToMenu();
      return;
    }

    selectedTrainDeparture = trainDepartures.get(idx);

    exitToMenu();
  }

  /**
   * Menu to assign a track to a train departure. Will ask user for index of train departure to
   * assign track to. Will ask user for track number. Converts from string to int with overflow
   * check. Exits to menu when user presses enter.
   *
   * @see TrainDeparture
   * @see TrainDeparture#setTrack(int)
   * @see TrainDeparture#getTrack()
   * @see #getValidInputFromUser(String) 
   * @since 1.2.0
   */
  private void assignTrackMenu() {
    // Prints a detailed list of every train departure in arraylist with indexes prefixed.
    printNumeratedTrainDepartures();

    int index = Integer.parseInt(
        getValidInputFromUser("Enter index of train to assign track to: ")
    );

    // Gets the train departure from the arraylist with the index
    TrainDeparture trainDeparture = trainDepartures.get(index);

    int track = Integer.parseInt(
        getValidInputFromUser("Enter track number: ") // Gets input from user
    );
    if (track == -1) {
      Terminal.writeLine("Invalid input. Must be between 1 and 99");
      exitToMenuWithPrompt();
    }

    // Sets the track of the train departure
    trainDeparture.setTrack(track);

    // Exits the menu when user presses enter
    exitToMenu();
  }

  /**
   * Prints a detailed list of every train departure in hashset with indexes prefixed.
   */
  private void printNumeratedTrainDepartures() {
    clearScreen();  // Clears screen for a readable output

    int i = 0; // Index counter
    for (TrainDeparture trainDeparture : trainDepartures) {
      Terminal.write(i + " : ");
      Terminal.writeLine(trainDeparture.toString());  // Prints the train departure info
      i++;
    }
  }

  /**
   * Menu to notify delay of a train departure. Will ask user for index of train departure to notify
   * delay of. Will ask user for hour and minute of delay. Converts from string to int with overflow
   * check. Exits to menu when user presses enter.
   *
   * @see TrainDeparture
   * @see TrainDeparture#setDelay(int[])
   * @see TrainDeparture#getDelay()
   * @see #getValidInputFromUser(String) 
   * @since 1.2.0
   */
  private void notifyDelayMenu() {
    // Prints a detailed list of every train departure in arraylist with indexes prefixed.
    printNumeratedTrainDepartures();

    int index = Integer.parseInt(
        getValidInputFromUser("Enter index of train to notify delay of: ")
    );

    // Gets the train departure from the arraylist with the index
    TrainDeparture trainDeparture = trainDepartures.get(index);

    int[] delay = new int[2]; // Array to store the delay

    delay[0] = Integer.parseInt(
        getValidInputFromUser("Enter hour of delay: ") // Gets input from user
    );

    delay[1] = Integer.parseInt(
        getValidInputFromUser("Enter minute of delay: ") // Gets input from user
    );

    // Sets the delay of the train departure
    trainDeparture.setDelay(delay);

    // Exits the menu when user presses enter
    exitToMenu();
  }

  /**
   * Prints a detailed menu of each train departure and its info in a clean format. Waits for user
   * input via terminal before going back to main menu.
   *
   * @since 1.0.0
   */
  private void showTrainDepartureMenu() {
    clearScreen();

    // Formats the current time to HH:MM
    String formattedCurrentTime = getFormattedTimeWithInts(
        currentTime[0],
        currentTime[1]
    );

    // Prints a nice header
    StringBuilder header = new StringBuilder();
    header.append("Train Dispatch System 1.0\n\n")
        .append("Train departures\n\n")
        .append("AVGANGER Departures")
        .append("      ")
        .append("SPOR Track")
        .append("      ")
        .append(formattedCurrentTime)
        .append("\n");
    Terminal.writeLine(header.toString());

    // Iterates through every train departure,
    // and gets the info to each train departure and prints it in a nice format
    for (TrainDeparture trainDeparture : trainDepartures) {
      // Formats the time to HH:MM, taking delay into account
      String formattedTime = getFormattedTimeWithInts(
          trainDeparture.getDepartureTime()[0] + trainDeparture.getDelay()[0],
          trainDeparture.getDepartureTime()[1] + trainDeparture.getDelay()[1]
      );
      // Reverses the destination, needed to format the string with x amount of whitespaces.
      StringBuilder footer = new StringBuilder();
      footer.append(formattedTime)
          .append(" ")
          .append(trainDeparture.getLine())
          .append(" ")
          .append(appendWhitespaceToString(trainDeparture.getDestination(), 17))
          // Leading whitespaces for fixed length
          .append(" ")
          .append(trainDeparture.getTrack());

      // Prints the info in a nice format
      Terminal.writeLine(footer.toString());
    }
    // Makes a new line for the next menu
    Terminal.writeLine("\n\n\n");
    // TODO implement terminal library

    // Waits for user input before going back to main menu
    exitToMenuWithPrompt();
  }

  /**
   * Appends x amount of whitespaces to a string. The amount of whitespaces is determined by the
   * length parameter minus the length of the input.
   *
   * @param input  Beginning of string
   * @param length Length of output string
   * @return input with x amount of whitespaces appended
   * @since 1.1.0
   */

  private StringBuilder appendWhitespaceToString(String input, int length) {
    // Reversing the input
    StringBuilder inputReversed = new StringBuilder();
    inputReversed.append(input);
    inputReversed.reverse();

    String whitespacesReversed = String.format("%1$" + length + "s", inputReversed);
    // Catting the leading whitespaces to reversed destination
    StringBuilder destination = new StringBuilder(whitespacesReversed);
    return destination.reverse();
  }

  /**
   * Makes a string in the format HH:MM. Hour cannot be more than 24, minute cannot be over 60.
   * Complete overflow check.
   *
   * @param hour   Non-negative integer representing hour
   * @param minute Non-negative integer representing minute
   * @return String in format HH:MM
   * @since 1.0.0
   */
  private static String getFormattedTimeWithInts(int hour, int minute) {
    // Copies the hour and minute to new variables
    int newHour = hour;
    int newMinute = minute;

    newHour += newMinute / 60;  // Floor division of minute added to Hour
    newMinute = newMinute % 60;  // 60 minute overflow check

    // Returns the formatted time
    return String.format("%02d:%02d", newHour, newMinute);
  }

  /**
   * Prints a detailed menu of different things the user can do. Prompts user with input to select
   * an option. Will loop continuously until valid input has been received from user. User may at
   * any time quit the application by selecting the respective value.
   *
   * @since 1.0.0
   */
  private void showSelectModeMenu() {
    // Variables used in this method
    int choice = 0;
    boolean stopLoop = false;

    while (!stopLoop) {
      // Prompts the user of different options to explore
      String message = """
          Train Dispatch System 1.0

          Choices:
          1: View train departures
          2: Add new train departure
          3: Assign track to departure
          4: Notify delay of departure
          5: Search for train based on train-number
          6: Search for train based on destination

          7: Update time
          10: Quit application
              """;
      clearScreen();
      Terminal.writeLine(message);

      if (selectedTrainDeparture != null) {
        Terminal.writeLine("Selected departure: " + selectedTrainDeparture);
      }

      // Asks the user for an input, must be of type integer.
      // Catches any IOException
      try {
        choice = Terminal.readInt("Please enter value: ");
      } catch (NumberFormatException | NoSuchElementException | IllegalStateException
               | IOException e) {
        Terminal.writeLine("Invalid input, please try again.");
      }

      if ((1 <= choice && choice <= 7) || choice == 10) {
        state = choice;
        stopLoop = true;
      } else {
        clearScreen();
      }
    }
  }

  /**
   * Menu to update the time. Will ask user for input for hour and minute separately. Converts from
   * string to int with overflow check.
   *
   * @since 1.0.0
   */
  private void updateTime() {
    Terminal.writeLine("Update time");

    String hourInput;
    try {
      hourInput = Terminal.readString("Enter new hour: ");
    } catch (IOException e) {
      hourInput = "";  // Makes input empty, exits method below
    }
    if (hourInput.isEmpty()) {  // Checks if input is empty
      exitToMenu();
      return;
    }

    String minuteInput;
    try {
      minuteInput = Terminal.readString("Enter new minute: ");
    } catch (IOException e) {
      minuteInput = "";  // Makes input empty, exits method below
    }

    // Prompts the user for minute input
    if (minuteInput.isEmpty()) {  // Checks if input is empty
      exitToMenu();
      return;
    }
    int newHour = Integer.parseInt(hourInput);  // Converts from string to int
    int newMinute = Integer.parseInt(minuteInput);  // Converts from string to int

    // Overflow check
    newHour += newMinute / 60;  // Floor division of minute added to Hour
    newHour = newHour % 24;  // 24 hour overflow check
    newMinute = newMinute % 60;  // 60 minute overflow check

    // Sets the new time
    currentTime = new int[]{newHour, newMinute};
    exitToMenu();
  }

  /**
   * Menu to add train departures. Will ask user for input for every field. Handles every input type
   * conversion exceptions with while true. Can exit loop at any moment.
   *
   * @see #getValidInputFromUser(String) 
   * @since 1.0.0
   */
  private void addTrainDepartureMenu() {
    // Variables used in this method
    String input;
    String[] results = new String[6];

    String[] inputFields = {
        "Hour for planned departure:",
        "Minute for planned departure: ",
        "Destination for departure: ",
        "Line of train departure: ",
        "Train-track for departure:",
    };

    // Prints a nice header
    Terminal.writeLine("Add new train departure");

    // Gets input from user for every field
    for (int i = 0; i < inputFields.length; i++) {
      // Gets input from user with a while loop to handle any exception
      input = getValidInputFromUser(inputFields[i]);
      if (input.isEmpty()) {  // Checks if input is empty
        exitToMenu();
        return;
      }
      results[i] = input;  // Adds the input to the results array with respective indexes
    }

    // Creates a new train departure with the results array,
    // and adds to the trainDepartures arraylist collection
    TrainDeparture trainDeparture = new TrainDeparture(results);
    trainDepartures.add(trainDeparture);

    exitToMenu();
  }

  /**
   * Notifies the user before quitting the application.
   *
   * @since 1.0.0
   */
  private void quitApplication() {
    Terminal.writeLine("Exiting application");
    System.exit(0);
  }

  /**
   * Waits for user input, and does nothing with it. Sets the program state to menu. Catches any
   * exception.
   */
  private void exitToMenuWithPrompt() {
    try {
      Terminal.readString("Enter to exit to menu");
      state = SELECTMODE;
    } catch (NoSuchElementException | IllegalStateException | IOException e) {
      state = SELECTMODE;
    }
  }

  /**
   * Sets the state of the class to menu-mode.
   */
  private void exitToMenu() {
    state = SELECTMODE;
  }

  /**
   * Gets a string from user. Will catch any error the input method throws and retry getting input
   * from user.
   *
   * @param inputMessage A message the user will be prompted with when waiting for input.
   * @return Null if input is empty, input-string if something
   *
   * @see #getValidInputFromUser(String) .
   */

  private String getValidInputFromUser(String inputMessage) {
    // Variables used in this method
    boolean retry;
    String input;
    input = "";

    // We want to execute the code in the do-while loop at least once.
    do {
      try {
        input = Terminal.readString(inputMessage);

        retry = false;
      } catch (NoSuchElementException | IllegalStateException | IOException e) {
        Terminal.writeLine("Error " + e + "\n");
        retry = true;  // If exception, we want to retry the input until conditions is satisfied.
      }
    } while (retry);

    return input;
  }

  /**
   * Clears the terminal of any previously written output.
   * Used for cleaner output.
   *
   * @since 1.0.0
   */
  private static void clearScreen() {
    Terminal.write("\033[H\033[2J");
  }

  /**
   * Compares itself to another object based on hashcode.
   *
   * @param o Some other object
   * @return similarity between self and other object
   * @since 1.0.0
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Io io)) {
      return false;
    }
    return state == io.state && running == io.running && Objects.equals(trainDepartures,
        io.trainDepartures) && Objects.equals(selectedTrainDeparture,
        io.selectedTrainDeparture) && Arrays.equals(currentTime, io.currentTime);
  }

  /**
   * Hashes fields of the object and returns the hash.
   *
   * @since 1.0.0
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(state, running, trainDepartures, selectedTrainDeparture);
    result = 31 * result + Arrays.hashCode(currentTime);
    return result;
  }
}