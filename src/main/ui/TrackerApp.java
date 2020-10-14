package ui;


import model.DailyMacros;
import model.WeeklySummary;

import javax.jws.soap.SOAPBinding;
import java.util.Scanner;

//Calories & Macros Tracker Application
public class TrackerApp {
    private DailyMacros selected;
    private WeeklySummary weeklysummary;
    private Scanner input = new Scanner(System.in);

    public TrackerApp() {
        weeklysummary = new WeeklySummary();
        runTracker();
    }

    public void runTracker() {
        boolean keepGoing = true;
        String command = null;

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

    public void selectDay(String command) {
        if (command.equals("mon")) {
            selected = weeklysummary.getDailyMacro(0);
        } else if (command.equals("tue")) {
            selected = weeklysummary.getDailyMacro(1);
        } else if (command.equals("wed")) {
            selected = weeklysummary.getDailyMacro(2);
        } else if (command.equals("thu")) {
            selected = weeklysummary.getDailyMacro(3);
        } else if (command.equals("fri")) {
            selected = weeklysummary.getDailyMacro(4);
        } else if (command.equals("sat")) {
            selected = weeklysummary.getDailyMacro(5);
        } else if (command.equals("sun")) {
            selected = weeklysummary.getDailyMacro(6);
        }
    }

    private void processCommand(String command) {
        if (command.equals("i")) {
            inputMeal();
        } else if (command.equals("v")) {
            viewSummary();
        } else {
            System.out.println("Choose valid option");
        }
    }

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

    public void displayOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> input meal");
        System.out.println("\tv -> end day and view summary");
        System.out.println("\tq -> quit");
    }

    public void inputCalorieGoals() {
        System.out.print("Enter daily calorie goal\n");
        int amount = input.nextInt();

        if (amount > 0) {
            selected.setCalorieGoal(amount);
        } else {
            System.out.println("Calorie goal must be greater than zero...");
        }
    }

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
            selected.getFatGaolsPercentage(fatAmount);
            System.out.printf("Your target macro breakdown in grams are:\n", selected.calculateMacroGoals());
        }
    }

    public void inputMeal() {
        System.out.print("Enter grams of carbs consumed\n");
        int carbsAmount = input.nextInt();

        System.out.print("Enter grams of protein consumed\n");
        int proteinAmount = input.nextInt();

        System.out.print("Enter grams of fat consumed\n");
        int fatAmount = input.nextInt();

        int carbs = selected.getCarbsGrams();
        carbs += carbsAmount;

        int protein = selected.getProteinGrams();
        protein += proteinAmount;

        int fat = selected.getFatGrams();
        fat += fatAmount;
    }

    private void viewSummary() {
        System.out.printf("Your total calorie goal was:", selected.getCalorieGoal());
    }
}
