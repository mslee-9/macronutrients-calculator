package ui;

import model.WeeklySummary;
import model.exceptions.InvalidWeekIndexException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TrackerFrame extends JFrame {

    private final JPanel trackerPanel;
    private final SaverFrame saverFrame;
    private final GUI gui;

    private MealTrackerFrame mealTrackerFrame;

    private JTextField calorieGoal;
    private JTextField carbsGoal;
    private JTextField proteinGoal;
    private JTextField fatGoal;
    private JTextField weekName;
    private JTextField weekDayName;

    private JButton enterButton;
    private JButton enterButton2;
    private JButton enterButton3;
    private JButton enterButton4;

    private JLabel welcomeLabel;
    private JLabel daysOfWeek;
    private JLabel fatGoalInput;
    private JLabel carbsGoalInput;
    private JLabel proteinGoalInput;
    private JLabel calorieGoalInput;

    private String daySelected;

    public TrackerFrame(GUI gui) {
        this.setName("Tracker");
        this.setSize(400, 600);
        this.setBackground(Color.WHITE);

        trackerPanel = new JPanel();
        trackerPanel.setBackground(Color.WHITE);
        trackerPanel.setLayout(new BoxLayout(trackerPanel, BoxLayout.Y_AXIS));

        this.add(trackerPanel);
        this.setVisible(true);
        this.setResizable(true);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saverFrame.promptSave();
            }
        });
        saverFrame = new SaverFrame(this);

        this.gui = gui;
    }

    // MODIFIES: this
    // EFFECTS: constructs tracker frame and panel, prompts user to select name and week for the week, and
    //         when prompted to close, calls promptSave method
    public void runTrackerFrame() {
        selectNameAndWeek();
        gui.soundEffects.playStartSound();
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to enter name and week
    //          when "Enter" button is pressed, call processSelectDayOfWeek and selectDayOfWeek method
    public void selectNameAndWeek() {
        welcomeLabel = new JLabel("Please enter your name and week (e.g. Stella Week 1)");

        trackerPanel.add(welcomeLabel);
        trackerPanel.add(Box.createVerticalStrut(10));

        weekName = new JTextField(1);
        weekName.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(weekName);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            processSelectDayOfWeek();
            selectDayOfWeek();
        });
        trackerPanel.add(enterButton);

        trackerPanel.setVisible(true);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: gets user input in String, displays selected name and week,
    //           constructs new weeklySummary instance with chosen name
    //           makes previous input fields and buttons invisible
    private void processSelectDayOfWeek() {
        String weekNameString = weekName.getText();
        welcomeLabel.setText("Name & Week: " + weekNameString);

        gui.setWeeklySummary(new WeeklySummary(weekNameString));

        weekName.setVisible(false);
        enterButton.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to choose a day of week, calls processSelectDay,
    //           and calls promptInputGoals to continue execution
    private void selectDayOfWeek() {
        daysOfWeek = new JLabel("Type in day of week (e.g. Mon for Monday)");
        trackerPanel.add(daysOfWeek);
        trackerPanel.add(Box.createVerticalStrut(10));

        weekDayName = new JTextField(2);
        weekDayName.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(weekDayName);

        enterButton2 = new JButton("Enter");
        enterButton2.addActionListener(e -> {
            processSelectDay();
            promptInputGoals();
        });
        trackerPanel.add(enterButton2);

        trackerPanel.setVisible(true);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: calls selectDay and selectDayString and displays the chosen day of week
    private void processSelectDay() {
        selectDay();
        selectDayString();
        daysOfWeek.setText("Day of Week: " + daySelected);
    }

    // REQUIRES: command must be one of mon, tue, wed, thu, fri, sat, or sun
    // MODIFIES: this
    // EFFECTS: selects day of the week, monday through sunday, in weekly summary corresponding to the index
    public void selectDay() {
        try {
            String command = weekDayName.getText().toLowerCase();
            gui.setSelected(gui.getWeeklySummary().getDailyMacro(selectDayOfWeekIndex(command)));
        } catch (InvalidWeekIndexException e) {
            System.out.println("Invalid Week Index: Index must be between 0 and 6 inclusive");
        }
    }

    // REQUIRES: command is one of mon, tue, wed, thu, fri, sat, and sun
    // EFFECTS: returns the appropriate index of week between 0 to 6 inclusive,
    //           where 0 indicates Monday and 6 indicates Sunday
    public int selectDayOfWeekIndex(String command) {
        switch (command) {
            case "mon":
                return 0;
            case "tue":
                return 1;
            case "wed":
                return 2;
            case "thu":
                return 3;
            case "fri":
                return 4;
            case "sat":
                return 5;
            default:
                return 6;
        }
    }

    // REQUIRES: command must be one of mon, tue, wed, thu, fri, sat, or sun
    // MODIFIES: this
    // EFFECTS: selects and updates daySelected field with chosen day  of the week, monday through sunday
    public void selectDayString() {
        switch (weekDayName.getText().toLowerCase()) {
            case "mon":
                daySelected = "Monday";
                break;
            case "tue":
                daySelected = "Tuesday";
                break;
            case "wed":
                daySelected = "Wednesday";
                break;
            case "thu":
                daySelected = "Thursday";
                break;
            case "fri":
                daySelected = "Friday";
                break;
            case "sat":
                daySelected = "Saturday";
                break;
            case "sun":
                daySelected = "Sunday";
                break;
        }
    }


    // MODIFIES: this
    // EFFECTS: Calls method to input Calorie Goals to start prompting user's goal inputs
    protected void promptInputGoals() {
        enterButton2.setVisible(false);
        weekDayName.setVisible(false);
        inputCalorieGoals();
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input calorie goals and when "Enter" button is clicked, call method to set calorie goals
    private void inputCalorieGoals() {
        calorieGoalInput = new JLabel("Enter daily calorie goal (kcal)");
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(calorieGoalInput);

        trackerPanel.add(Box.createVerticalStrut(10));
        calorieGoal = new JTextField(2);
        calorieGoal.setMaximumSize(new Dimension(200, 25));

        trackerPanel.add(calorieGoal);

        enterButton3 = new JButton("Enter");
        enterButton3.addActionListener(e -> setCalorieGoals());
        trackerPanel.add(enterButton3);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input calorie goals and when "Enter" button is clicked, call method to set calorie goals
    //           Once valid calorie goal was inputted, call method to prompt inputting of Macros goals
    public void setCalorieGoals() {
        enterButton3.setVisible(false);
        calorieGoal.setVisible(false);

        String calorieGoalText = calorieGoal.getText();
        calorieGoalInput.setText("Daily Calorie Goal: " + calorieGoalText + " kcal");
        trackerPanel.setVisible(true);
        calorieGoalInput.setVisible(true);

        int calorieGoalInt = Integer.parseInt(calorieGoalText);
        if (calorieGoalInt > 0) {
            getGui().getSelected().setCalorieGoal(calorieGoalInt);
            inputMacrosGoals();

        } else {
            JLabel calorieUnderZero = new JLabel("Calorie goal must be greater than zero...");
            trackerPanel.add(calorieUnderZero);
            inputCalorieGoals();
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input Macros goals and calls setMacroGoals when "Enter" button is pressed
    private void inputMacrosGoals() {
        carbsGoalInput = new JLabel("Enter target carbs goal as % (e.g. 0.4 for 40%)\n");
        trackerPanel.add(Box.createVerticalStrut(10));
        carbsGoal = new JTextField();
        carbsGoal.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(carbsGoalInput);
        trackerPanel.add(carbsGoal);

        proteinGoalInput = new JLabel("Enter target protein goal as % (e.g. 0.3 for 30%)\n");
        trackerPanel.add(Box.createVerticalStrut(10));
        proteinGoal = new JTextField(2);
        proteinGoal.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(proteinGoalInput);
        trackerPanel.add(proteinGoal);

        fatGoalInput = new JLabel("Enter target fat goal as % (e.g. 0.3 for 30%)\n");
        trackerPanel.add(Box.createVerticalStrut(10));
        fatGoal = new JTextField(2);
        fatGoal.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(fatGoalInput);
        trackerPanel.add(fatGoal);

        enterButton4 = new JButton("Enter");
        enterButton4.addActionListener(e -> setMacroGoals());
        trackerPanel.add(enterButton4);
    }

    // MODIFIES: this
    // EFFECTS: Reads User input for Macro goals, sets previous input fields invisible, displays percentage Macro goals
    //           If invalid percentages were entered, prompts reader to enter again else call displayMacroBreakdown
    private void setMacroGoals() {
        String carbsGoalString = carbsGoal.getText();
        String proteinGoalString = proteinGoal.getText();
        String fatGoalString = fatGoal.getText();

        carbsGoal.setVisible(false);
        proteinGoal.setVisible(false);
        fatGoal.setVisible(false);

        double carbsAmount = Double.parseDouble(carbsGoalString);
        double proteinAmount = Double.parseDouble(proteinGoalString);
        double fatAmount = Double.parseDouble(fatGoalString);

        carbsGoalInput.setText("% Carbs Goal: " + (carbsAmount * 100) + " %");
        proteinGoalInput.setText("% Protein Goal: " + (proteinAmount * 100) + " %");
        fatGoalInput.setText("% Fat Goal: " + (fatAmount * 100) + " %");

        double totalAmount = carbsAmount + proteinAmount + fatAmount;
        if (totalAmount != 1.0) {
            JLabel mustAddUpTo = new JLabel("Enter three percentages that add up to 100%");
            trackerPanel.add(mustAddUpTo);
            mustAddUpTo.setVisible(true);
            inputMacrosGoals();
        } else {
            displayMacroBreakdown(carbsAmount, proteinAmount, fatAmount);
        }
    }

    // MODIFIES: this
    // EFFECTS: Make previous input button invisible, calculate and display Macro breakdown for the day
    //           call continueTracking method to continue execution
    private void displayMacroBreakdown(double carbsAmount, double proteinAmount, double fatAmount) {
        enterButton4.setVisible(false);
        getGui().getSelected().setCarbsGoalsPercentage(carbsAmount);
        getGui().getSelected().setProteinGoalsPercentage(proteinAmount);
        getGui().getSelected().setFatGoalsPercentage(fatAmount);
        getGui().getSelected().calculateMacroGoals();

        JLabel macroBreakdown = new JLabel("Your target macro breakdown in grams are:");
        JLabel carbBreakdown = new JLabel("Carbs:" + " " + getGui().getSelected().getCarbsGoalGrams() + " (g)");
        JLabel proteinBreakdown = new JLabel("Protein:" + " " + getGui().getSelected().getProteinGoalGrams() + " (g)");
        JLabel fatBreakdown = new JLabel("Fat:" + " " + getGui().getSelected().getFatGoalGrams() + " (g)");

        trackerPanel.add(Box.createVerticalStrut(20));
        trackerPanel.add(macroBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(carbBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(proteinBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(fatBreakdown);
        continueTracking();
    }

    // MODIFIES: this
    // EFFECTS: Display two new buttons "Input Meal" and "View Summary"
    //           If user selects "Input Meal", call constructTracker then inputMealPrompt to continue execution
    //           If user selects "View Summary", call viewSummary method
    private void continueTracking() {
        JButton inputMeal = new JButton("Input Meal");

        inputMeal.addActionListener(e -> {
            mealTrackerFrame = new MealTrackerFrame(this);
            mealTrackerFrame.inputMealPrompt();
        });
        JButton viewSummary = new JButton("View Summary");
        viewSummary.addActionListener(e -> viewSummary());

        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(inputMeal);
        trackerPanel.add(viewSummary);
    }

    // MODIFIES: this
    // EFFECTS: prints summary of daily intake and goals
    private void viewSummary() {
        int difference = getGui().getSelected().compareCalorieGoals();
        JLabel calGoal = new JLabel("Your total calorie goal was:" + " " + getGui().getSelected().getCalorieGoal());
        JLabel calConsumed = new JLabel("Calories consumed today was:" + " "
                + getGui().getSelected().getTotalCaloriesConsumed());
        JLabel metGoalOrNot = new JLabel("");

        if (getGui().getSelected().compareCalorieGoals() == 0) {
            metGoalOrNot.setText("You met your calorie goal!");
        } else if (getGui().getSelected().compareCalorieGoals() > 0) {
            metGoalOrNot.setText("You were over your calorie goal by" + " " + difference);
        } else if (getGui().getSelected().compareCalorieGoals() < 0) {
            metGoalOrNot.setText("You were under your calorie goal by" + " " + -difference);
        }
        trackerPanel.add(Box.createVerticalStrut(30));
        trackerPanel.add(calGoal);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(calConsumed);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(metGoalOrNot);
        this.setVisible(true);
    }

    public GUI getGui() {
        return gui;
    }
}
