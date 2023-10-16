package dev.jonas;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;


/**
 * Class for the interface of the program.
 *
 * @author Jonas Birkeli
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"ClassWithoutLogger", "PublicMethodWithoutLogging",
  "WeakerAccess"})
public class Interface {
  // Variables used in this class
  private int state;
  private boolean running;
  private final Scanner scanner;
  private ArrayList<TrainDeparture> trainDepartures;
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
   * Constructor of the interface.
   *
   * @since 1.0.0
   */
  public Interface() {
    state = SELECTMODE;
    trainDepartures = new ArrayList<>();
    running = true;
    scanner = new Scanner(System.in);
    currentTime = new int[]{16, 37};
  }

  /**
   * Starts the interface. Has 8 different modes with a base-mode of viewing the options.
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
        case ASSIGN_TRACK_MODE -> exitToMenu();
        case NOTIFY_DELAY_MODE -> notifyDelayMenu();
        case SEARCH_TRAIN_BY_NUMBER_MODE -> exitToMenu();
        case SEARCH_TRAIN_BY_DESTINATION_MODE -> exitToMenu();
        case UPDATE_TIME_MODE -> updateTime();
        case QUIT_APPLICATION_MODE -> quitApplication();
        default -> running = false;
      }
    }
  }

  /**
   * Prints a detailed list of every train departure in hashset with indexes prefixed.
   */
  private void printNumeratedTrainDepartures() {
    int i = 0;
    for (TrainDeparture trainDeparture : trainDepartures) {
      System.out.print(i + " : ");
      System.out.println(trainDeparture.toString());
      i++;
    }
  }

  /**
   *
   */
  private void notifyDelayMenu() {
    // Prints a detailed list of every train departure in hashset with indexes prefixed.
    printNumeratedTrainDepartures();

    int index = Integer.parseInt(
        getValidInputFromUser("Enter index of train to notify delay of: ")
    );

    TrainDeparture trainDeparture = trainDepartures.get(index);

    int[] delay = new int[2];

    delay[0] = Integer.parseInt(
        getValidInputFromUser("Enter hour of delay: ")
    );

    delay[1] = Integer.parseInt(
        getValidInputFromUser("Enter minute of delay: ")
    );

    trainDeparture.setDelay(delay);

    // Exits the menu when user presses enter
    exitToMenuWithPrompt();
  }

  /**
   * Prints a detailed menu of each train departure and its info in a clean format.
   * Waits for user input via terminal before going back to main menu.
   *
   * @since 1.0.0
   */
  private void showTrainDepartureMenu() {
    // Formats the current time to HH:MM
    String formattedCurrentTime = getFormattedTimeWithInts(
        currentTime[0],
        currentTime[1]
    );

    // Prints a nice header
    System.out.print("AVGANGER Departures");
    System.out.print("      ");
    System.out.print("SPOR Track");
    System.out.print("      ");
    System.out.print(formattedCurrentTime + "\n");

    // Iterates through every train departure,
    // and gets the info to each train departure and prints it in a nice format
    for (TrainDeparture trainDeparture : trainDepartures) {
      // Formats the time to HH:MM, taking delay into account
      String formattedTime = getFormattedTimeWithInts(
          trainDeparture.getDepartureTime()[0] + trainDeparture.getDelay()[0],
          trainDeparture.getDepartureTime()[1] + trainDeparture.getDelay()[1]
      );

      // Reverses the destination, needed to format the string with x amount of whitespaces.
      StringBuilder destination = appendWhitespaceToString(trainDeparture.getDestination(), 17);

      // Prints the info in a nice format
      System.out.print(formattedTime + " ");
      System.out.print(trainDeparture.getLine() + " ");
      System.out.printf(destination.toString());
      System.out.print(trainDeparture.getTrack() + "\n");
    }
    // Makes a new line for the next menu
    System.out.println("\n\n\n");


    // Waits for user input before going back to main menu
    exitToMenuWithPrompt();
  }

  /**
   * Appends x amount of whitespaces to a string. The amount of whitespaces is determined by the length parameter minus the length of the input.
   *
   * @param input Beginning of string
   * @param length Length of output string
   * @return input with x amount of whitespaces appended
   */
  @NotNull
  private static StringBuilder appendWhitespaceToString(String input, int length) {
    // Reversing the input
    StringBuilder inputReversed = new StringBuilder();
    inputReversed.append(input);
    inputReversed.reverse();

    // Catting the leading whitespaces to reversed destination
    String formattedInputReversed = String.format("%1$" + length + "s", inputReversed);
    StringBuilder destination = new StringBuilder();
    destination.append(formattedInputReversed);
    return destination.reverse();
  }
  @Test
  public void testStringWhitespaceCount() {
    assert(appendWhitespaceToString("Oslo S", 8).toString().equals("Oslo S  "));
  }

  /**
   * Makes a string in the format HH:MM. Hour cannot be more than 24, minute cannot be over 60. Complete overflow check.
   *
   * @param hour Non-negative integer representing hour
   * @param minute Non-negative integer representing minute
   * @return String in format HH:MM
   */
  private static String getFormattedTimeWithInts(int hour, int minute) {
    // Copies the hour and minute to new variables
    int newHour = hour;
    int newMinute = minute;

    // Overflow check
    if (newMinute >= 60) {
      newHour = (newHour + 1) % 24;  // 24 hour overflow check
      newMinute = newMinute % 60;  // 60 minute overflow check

    }
    // Returns the formatted time
    return String.format("%02d:%02d", newHour, newMinute);
  }

  /**
   * Prints a detailed menu of different things the user can do.
   * Prompts user with input to select an option.
   * Will loop continuously until valid input has been received from user.
   * User may at any time quit the application by selecting the respective value.
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
      System.out.println(message);

      // Asks the user for an input, must be of type integer.
      // Catches any IOException
      try {
        System.out.print("Please enter value: ");
        choice = Integer.parseInt(scanner.nextLine());

        System.out.println();
      } catch (NumberFormatException | NoSuchElementException | IllegalStateException e) {
        System.out.println("Error " + e + "\n");
      }

      if ((1 <= choice && choice <= 7) || choice == 10) {
        state = choice;
        stopLoop = true;
      } else {
        clearScreen();
        System.out.println("Invalid input. Try again\n\n\n");
      }
    }
  }

  /**
   * Menu to update the time.
   * Will ask user for input for hour and minute separately.
   * Converts from string to int with overflow check.
   *
   * @since 1.0.0
   */
  private void updateTime() {
    // Flushes the scanner
    scanner.nextLine();

    // Prompts the user for hour input
    System.out.println("Update time");
    System.out.println("Enter new hour: ");
    String hourInput = scanner.nextLine();  // Gets input from user
    if (hourInput.isEmpty()) {  // Checks if input is empty
      exitToMenu();
      return;
    }


    // Prompts the user for minute input
    System.out.println("Enter new minute: ");
    String minuteInput = scanner.nextLine();  // Gets input from user
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
  }

  /**
   * Menu to add train departures.
   * Will ask user for input for every field.
   * Handles every input type conversion exceptions with while true.
   * Can exit loop at any moment.
   *
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

    // Flushes the scanner
    scanner.nextLine();

    // Prints a nice header
    System.out.println("Add new train departure");

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
    System.out.println("Exiting application");
    System.exit(0);
  }

  /**
   * Waits for user input, and does nothing with it. Sets the program state to menu.
   * Catches any exception.
   */
  private void exitToMenuWithPrompt() {
    try {
      System.out.println("Enter to exit to menu");
      scanner.nextLine();
      scanner.nextLine();
      state = SELECTMODE;
    } catch (NoSuchElementException | IllegalStateException e) {
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
   * Gets a string from user.
   * Will catch any error the input method throws and retry getting input from user.
   *
   * @param inputMessage A message the user will be prompted with when waiting for input.
   * @return Null if input is empty, input-string if something.
   */

  private String getValidInputFromUser(String inputMessage) {
    boolean retry;
    String input;
    input = "";
    do {
      try {
        System.out.println(inputMessage);

        input = scanner.nextLine();
        retry = false;
      } catch (NoSuchElementException | IllegalStateException e) {
        System.out.println("Error " + e + "\n");
        retry = true;
      }
    } while (retry);
    return input;
  }

  /**
   * Clears the terminal of any previously written output.
   * Clears the output stream for new input.
   */
  private static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  /**
   * Compares itself to another object based on hashcode.
   *
   * @param o Some other object
   * @return similarity between self and other objec.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Interface that)) {
      return false;
    }
    return state == that.state
      && running == that.running
      && Objects.equals(scanner, that.scanner)
      && Objects.equals(trainDepartures, that.trainDepartures);
  }


  /**
   * Hashes fields of this object and returns the hash.
   */
  @Override
  public int hashCode() {
    return Objects.hash(state, running, scanner, trainDepartures);
  }
}
