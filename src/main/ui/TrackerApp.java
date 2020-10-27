package ui;


import model.DailyMacros;
import model.WeeklySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//SOURCE: JsonSerializationDemo - few methods in this class pertaining to the persistence function
//                                were built based on JsonSerializationDemo
//Calories & macros tracking application
public class TrackerApp {
    private static final String JSON_STORE = "./data/weeklySummary.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DailyMacros selected;
    private WeeklySummary weeklysummary;
    private Scanner input = new Scanner(System.in);

    //EFFECTS: runs the tracker application
    public TrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startApplication();
    }

    //Source: this method was built with reference to CPSC210 TellerApp provided on CPSC210 edX edge
    //MODIFIES: this
    //EFFECTS: starts application by displaying initial options and processes user input
    public void startApplication() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayInitialOptions();
            command = input.next();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processInitialCommand(command);
            }
        }
    }

    //Source: this method was built with reference to CPSC210 TellerApp provided on CPSC210 edX edge
    //MODIFIES: this
    //EFFECTS: prompts user to select day of week, calorie and macro goals, and processes user input
    public void runTracker() {
        boolean keepGoing = true;
        String command;

        welcomeMessageTracker();
        displayWeek();
        promptInputGoals();

        while (keepGoing) {
            command = displayTrackerOptions();

            if (command.equals("v")) {
                processCommand(command);
                System.out.println("\nDo you want to save your record?\n y -> yes \n n -> no");
                command = input.next();
                if (command.equals("y")) {
                    saveWeeklySummary();
                    System.out.println("Your record was saved! Goodbye");
                    keepGoing = false;
                } else {
                    System.out.println("Quitting tracker. Goodbye!");
                    keepGoing = false;
                }
            } else {
                processCommand(command);
            }
        }
    }

    //EFFECTS: displays tracker options
    private String displayTrackerOptions() {
        String command;
        displayOptions();
        command = input.next();
        command = command.toLowerCase();
        return command;
    }

    //EFFECTS: displays days of week
    private void displayWeek() {
        String command;
        displayDaysOfWeek();
        command = input.next();
        command = command.toLowerCase();
        selectDay(command);
    }

    //EFFECTS: displays welcome message and prompts user to name the week
    private void welcomeMessageTracker() {
        String command;
        System.out.println("Welcome!\nPlease enter your name & week (e.g. SarahWeek1)");
        command = input.next();
        selectNameAndWeek(command);
    }

    //EFFECTS: prompts user to input calories and macros goals
    private void promptInputGoals() {
        inputCalorieGoals();
        inputMacrosGoals();
    }

    //EFFECTS: displays initial options menu
    private void displayInitialOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> track");
        System.out.println("\tl -> load weekly summary from file");
        System.out.println("\tp -> print weekly summary in file");
        System.out.println("\tq -> quit");
    }

    //Reference: this method was built with reference to CPSC210 TellerApp provided on CPSC210 edX edge
    //MODIFIES: this
    //EFFECTS: processes user command
    private void processInitialCommand(String command) {
        switch (command) {
            case "t":
                runTracker();
                break;
            case "l":
                loadWeeklySummary();
                break;
            case "p":
                printWeeklySummary();
                break;
            default:
                System.out.println("Choose valid option");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: loads weekly summary from file
    private void loadWeeklySummary() {
        try {
            weeklysummary = jsonReader.read();
            System.out.println("Loaded " + weeklysummary.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: prints all daily macros record in weekly summary to the console
    private void printWeeklySummary() {
        System.out.println("Here is the weekly summary from Monday to Sunday:\n");
        List<DailyMacros> dailyMacros = weeklysummary.getWeeklySummaryList();

        for (DailyMacros dm: dailyMacros) {
            System.out.println(dm.formatNicely());
        }
    }

    //REQUIRES: command must be one of mon, tue, wed, thu, fri, sat, or sun
    //MODIFIES: this
    //EFFECTS: selects day of the week, monday through sunday, in weekly summary
    public void selectDay(String command) {
        switch (command) {
            case "mon":
                selected = weeklysummary.getDailyMacro(0);
                break;
            case "tue":
                selected = weeklysummary.getDailyMacro(1);
                break;
            case "wed":
                selected = weeklysummary.getDailyMacro(2);
                break;
            case "thu":
                selected = weeklysummary.getDailyMacro(3);
                break;
            case "fri":
                selected = weeklysummary.getDailyMacro(4);
                break;
            case "sat":
                selected = weeklysummary.getDailyMacro(5);
                break;
            case "sun":
                selected = weeklysummary.getDailyMacro(6);
                break;
        }

    }

    //Reference: this method was built with reference to CPSC210 TellerApp provided on CPSC210 edX edge
    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "i":
                inputMeal();
                break;
            case "v":
                viewSummary();
                break;
            case "s":
                saveWeeklySummary();
                break;
            default:
                System.out.println("Choose valid option");
                break;
        }
    }

    private void saveWeeklySummary() {
        try {
            jsonWriter.open();
            jsonWriter.write(weeklysummary);
            jsonWriter.close();
            System.out.println("Saved " + weeklysummary.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the name of weekly summary
    public void selectNameAndWeek(String command) {
        weeklysummary = new WeeklySummary(command);
    }

    //EFFECTS: displays days of week to user
    public void displayDaysOfWeek() {
        System.out.println("\nSelect from:");
        System.out.println("\tmon");
        System.out.println("\ttue");
        System.out.println("\twed");
        System.out.println("\tthu");
        System.out.println("\tfri");
        System.out.println("\tsat");
        System.out.println("\tsun");
    }

    //EFFECTS: displays menu of options to user
    public void displayOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> input meal");
        System.out.println("\tv -> end day and view summary");
        System.out.println("\ts -> save daily record to file");
    }

    //MODIFIES: this
    //EFFECTS: sets calorie goal for selected day
    public void inputCalorieGoals() {
        System.out.print("Enter daily calorie goal\n");
        int amount = input.nextInt();

        if (amount > 0) {
            selected.setCalorieGoal(amount);
        } else {
            System.out.println(" Calorie goal must be greater than zero...");
        }
    }

    //MODIFIES: this
    //EFFECTS: sets macros goals for selected day
    public void inputMacrosGoals() {
        System.out.print("Enter target carbs goal as % (e.g. 0.4 for 40%)\n");
        double carbsAmount = input.nextDouble();

        System.out.print("Enter target protein goal as % (e.g. 0.4 for 40%)\n");
        double proteinAmount = input.nextDouble();

        System.out.print("Enter target fat goal as % (e.g. 0.4 for 40%)\n");
        double fatAmount = input.nextDouble();

        double totalAmount = carbsAmount + proteinAmount + fatAmount;

        if (totalAmount != 1.0) {
            System.out.println("Enter three percentages that add up to 100%");
        } else {
            selected.setCarbsGoalsPercentage(carbsAmount);
            selected.setProteinGoalsPercentage(proteinAmount);
            selected.setFatGoalsPercentage(fatAmount);
            selected.calculateMacroGoals();
            System.out.println("Your target macro breakdown in grams are:");
            System.out.println("Carbs:" + " " + selected.getCarbsGoalGrams());
            System.out.println("Protein:" + " " + selected.getProteinGoalGrams());
            System.out.println("Fat:" + " " + selected.getFatGoalGrams());
        }
    }

    //MODIFIES: this
    //EFFECTS: adds grams of macronutrients consumed to respective categories
    public void inputMeal() {
        System.out.print("Enter grams of carbs consumed\n");
        int carbsAmount = input.nextInt();

        System.out.print("Enter grams of protein consumed\n");
        int proteinAmount = input.nextInt();

        System.out.print("Enter grams of fat consumed\n");
        int fatAmount = input.nextInt();

        selected.addCarbsConsumed(carbsAmount);
        selected.addProteinConsumed(proteinAmount);
        selected.addFatConsumed(fatAmount);
    }

    //EFFECTS: prints summary of daily intake and goals
    private void viewSummary() {
        int difference = selected.compareCalorieGoals();
        System.out.println("Your total calorie goal was:" + " " + selected.getCalorieGoal());
        System.out.println("Calories consumed today was:" + " " + selected.getTotalCaloriesConsumed());
        if (selected.compareCalorieGoals() == 0) {
            System.out.println("You met your calorie goal!");
        } else if (selected.compareCalorieGoals() > 0) {
            System.out.println("You were over your calorie goal by" + " " + difference);
        } else if (selected.compareCalorieGoals() < 0) {
            System.out.println("You were under your calorie goal by" + " " + -difference);
        }
    }
}