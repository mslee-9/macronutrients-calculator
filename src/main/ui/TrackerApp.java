package ui;


import model.DailyMacros;
import model.WeeklySummary;

import java.util.Scanner;

//Calories & macros tracking application
public class TrackerApp {
    private DailyMacros selected;
    private WeeklySummary weeklysummary;
    private Scanner input = new Scanner(System.in);

    //EFFECTS: runs the tracker application
    public TrackerApp() {
        weeklysummary = new WeeklySummary();
        runTracker();
    }

    //Reference: this method was built with reference to CPSC210 TellerApp provided on CPSC210 edX edge
    //MODIFIES: this
    //EFFECTS: prompts user to select day of week, calorie and macro goals, and processes user input
    public void runTracker() {
        boolean keepGoing = true;
        String command;

        displayDaysOfWeek();
        command = input.next();
        command = command.toLowerCase();
        selectDay(command);

        inputCalorieGoals();
        inputMacrosGoals();

        while (keepGoing) {
            displayOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("v")) {
                processCommand(command);
                keepGoing = false;
            } else {
                processCommand(command);
            }
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
        if (command.equals("i")) {
            inputMeal();
        } else if (command.equals("v")) {
            viewSummary();
        } else {
            System.out.println("Choose valid option");
        }
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
        System.out.println("\tq -> quit");
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