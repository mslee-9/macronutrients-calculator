package ui;

import model.DailyMacros;
import model.WeeklySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/weeklySummary.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Container container = new Container();

    private final Color blue = new Color(223, 247, 252);
    private final Color lilac = new Color(230, 229, 255);
    private final Color coral = new Color(246, 226, 207);

    private DailyMacros selected;
    private WeeklySummary weeklysummary;

    private JLabel loadLabel = new JLabel();
    private JLabel printLabel = new JLabel();
    private JLabel welcomeLabel;
    private JPanel trackerPanel;
    private JLabel daysOfWeek;
    private JLabel calorieGoalInput;
    private JLabel fatGoalInput;
    private JLabel carbsGoalInput;
    private JLabel proteinGoalInput;
    private JLabel enterCarbs;
    private JLabel enterProtein;
    private JLabel enterFat;
    private JLabel dayOfWeekLabel = new JLabel();

    private JButton enterButton;
    private JButton enterButton2;
    private JButton enterButton3;
    private JButton enterButton4;
    private JButton enterButton5;

    private JTextField weekName;
    private JTextField calorieGoal;
    private JTextField carbsGoal;
    private JTextField proteinGoal;
    private JTextField fatGoal;
    private JTextField weekDayName;
    private JTextField carbsMealInput;
    private JTextField proteinMealInput;
    private JTextField fatMealInput;

    private JFrame trackerFrame;
    private JFrame saveFrame;
    private JFrame mealTrackerFrame;

    private JPanel mainPanel;
    private JPanel savePanel;
    private JPanel mealTrackerPanel;

    private String daySelected;

    public GUI() {
        super("Tracker Application");
        this.setSize(400, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setLayout(new FlowLayout());
        setVisible(true);
        setResizable(false);

        constructMainPanel();
    }

    private void constructMainPanel() {
        mainPanel = new JPanel();

        JButton trackButton = new JButton("Track");
        trackButton.setActionCommand("trackButton");
        trackButton.addActionListener(this);

        JButton loadButton = new JButton("Load Data");
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);

        JButton printButton = new JButton("Print Data");
        printButton.setActionCommand("printButton");
        printButton.addActionListener(this);

        mainPanel.add(trackButton);
        mainPanel.add(loadButton);
        mainPanel.add(printButton);

        add(mainPanel);
        mainPanel.setSize(400,800);
        setVisible(true);
    }

    private void runTracker() {
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setSize(400, 500);
        trackerFrame.setBackground(Color.WHITE);

        trackerPanel = new JPanel();
        trackerPanel.setBackground(lilac);
        trackerFrame.add(trackerPanel);
        trackerFrame.setVisible(true);
        trackerFrame.setResizable(false);
        selectNameAndWeek();

        trackerFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        trackerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                promptSave();
            }
        });
    }

    private void promptSave() {
        constructSaver();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            saveWeeklySummary();
            saveFrame.dispose();
            trackerFrame.dispose();
        });
        JButton dontSaveButton = new JButton("Don't save");
        dontSaveButton.addActionListener(e -> {
            saveFrame.dispose();
            trackerFrame.dispose();
        });
        savePanel.add(saveButton);
        savePanel.add(dontSaveButton);
        saveFrame.add(savePanel);
        saveFrame.setVisible(true);
    }

    private void constructSaver() {
        saveFrame = new JFrame("Do you want to save?");
        saveFrame.setSize(400, 100);
        saveFrame.setResizable(false);
        savePanel = new JPanel();
        savePanel.setBackground(coral);
    }

    private void saveWeeklySummary() {
        try {
            jsonWriter.open();
            jsonWriter.write(weeklysummary);
            jsonWriter.close();
            JLabel saveSuccess = new JLabel("Saved " + weeklysummary.getName() + " to " + JSON_STORE);
            savePanel.add(saveSuccess);
            saveFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            JLabel saveFail = new JLabel("Unable to write to file: " + JSON_STORE);
            savePanel.add(saveFail);
            saveFrame.setVisible(true);
        }
    }

    public void selectNameAndWeek() {
        welcomeLabel = new JLabel("Please enter your name and week (e.g. Stella Week 1)");
        trackerPanel.add(welcomeLabel);

        weekName = new JTextField(1);
        weekName.setColumns(10);
        trackerPanel.add(weekName);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> selectDayOfWeek());
        trackerPanel.add(enterButton);

        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);
    }

    private void selectDayOfWeek() {
        String weekNameString = weekName.getText();
        welcomeLabel.setText("Name & Week: " + weekNameString);
        weekName.setVisible(false);
        enterButton.setVisible(false);

        weeklysummary = new WeeklySummary(weekNameString);
        daysOfWeek = new JLabel("Type in day of week (e.g. Mon for Monday)");
        trackerPanel.add(daysOfWeek);

        weekDayName = new JTextField(1);
        weekDayName.setColumns(5);
        trackerPanel.add(weekDayName);

        enterButton2 = new JButton("Enter");
        enterButton2.addActionListener(e -> {
            selectDay();
            selectDayString();
            daysOfWeek.setText("Day of Week: " + daySelected);
            promptInputGoals();
        });
        trackerPanel.add(enterButton2);

        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);
    }

    public void selectDay() {
        switch (weekDayName.getText().toLowerCase()) {
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

    private void promptInputGoals() {
        enterButton2.setVisible(false);
        weekDayName.setVisible(false);
        inputCalorieGoals();
    }

    private void inputCalorieGoals() {
        calorieGoalInput = new JLabel("Enter daily calorie goal");
        trackerPanel.add(calorieGoalInput);

        calorieGoal = new JTextField();
        calorieGoal.setColumns(5);

        trackerPanel.add(calorieGoal);
        trackerFrame.add(trackerPanel);

        enterButton3 = new JButton("Enter");
        enterButton3.addActionListener(e -> setCalorieGoals());
        trackerPanel.add(enterButton3);
        trackerFrame.setVisible(true);
    }

    public void setCalorieGoals() {
        String calorieGoalText = calorieGoal.getText();
        calorieGoalInput.setText("Daily Calorie Goal: " + calorieGoalText);
        int calorieGoalInt = Integer.parseInt(calorieGoalText);

        if (calorieGoalInt > 0) {
            selected.setCalorieGoal(calorieGoalInt);
        } else {
            JLabel calorieUnderZero = new JLabel(" Calorie goal must be greater than zero...");
            trackerPanel.add(calorieUnderZero);
            inputCalorieGoals();
        }
        trackerFrame.setVisible(true);
        inputMacrosGoals();
    }

    private void inputMacrosGoals() {
        enterButton3.setVisible(false);
        calorieGoal.setVisible(false);
        carbsGoalInput = new JLabel("Enter target carbs goal as % (e.g. 0.4 for 40%)\n");
        carbsGoal = new JTextField(5);
        trackerPanel.add(carbsGoalInput);
        trackerPanel.add(carbsGoal);

        proteinGoalInput = new JLabel("Enter target protein goal as % (e.g. 0.3 for 30%)\n");
        proteinGoal = new JTextField(5);
        trackerPanel.add(proteinGoalInput);
        trackerPanel.add(proteinGoal);

        fatGoalInput = new JLabel("Enter target fat goal as % (e.g. 0.3 for 30%)\n");
        fatGoal = new JTextField(5);
        trackerPanel.add(fatGoalInput);
        trackerPanel.add(fatGoal);

        enterButton4 = new JButton("Enter");
        enterButton4.addActionListener(e -> setMacroGoals());
        trackerPanel.add(enterButton4);
    }

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
        } else {
            displayMacroBreakdown(carbsAmount, proteinAmount, fatAmount);
        }
    }

    private void displayMacroBreakdown(double carbsAmount, double proteinAmount, double fatAmount) {
        enterButton4.setVisible(false);
        selected.setCarbsGoalsPercentage(carbsAmount);
        selected.setProteinGoalsPercentage(proteinAmount);
        selected.setFatGoalsPercentage(fatAmount);
        selected.calculateMacroGoals();
        JLabel macroBreakdown = new JLabel("Your target macro breakdown in grams are:");
        JLabel carbBreakdown = new JLabel("Carbs:" + " " + selected.getCarbsGoalGrams() + " (g)");
        JLabel proteinBreakdown = new JLabel("Protein:" + " " + selected.getProteinGoalGrams() + " (g)");
        JLabel fatBreakdown = new JLabel("Fat:" + " " + selected.getFatGoalGrams() + " (g)");
        trackerPanel.add(macroBreakdown);
        trackerPanel.add(carbBreakdown);
        trackerPanel.add(proteinBreakdown);
        trackerPanel.add(fatBreakdown);
        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);
        continueTracking();
    }

    private void continueTracking() {
        JButton inputMeal = new JButton("Input Meal");
        inputMeal.addActionListener(e -> inputMealPrompt());

        JButton viewSummary = new JButton("View Summary");
        viewSummary.addActionListener(e -> viewSummary());

        trackerPanel.add(inputMeal);
        trackerPanel.add(viewSummary);
    }

    //MODIFIES: this
    //EFFECTS: adds grams of macronutrients consumed to respective categories
    public void inputMealPrompt() {
        constructTracker();

        enterCarbs = new JLabel("Enter grams of carbs consumed\n");
        enterProtein = new JLabel("Enter grams of protein consumed\n");
        enterFat = new JLabel("Enter grams of fat consumed\n");

        carbsMealInput = new JTextField(3);
        proteinMealInput = new JTextField(3);
        fatMealInput = new JTextField(3);

        enterButton5 = new JButton("Enter");
        enterButton5.addActionListener(e -> inputMeal());

        mealTrackerPanel.add(enterCarbs);
        mealTrackerPanel.add(carbsMealInput);
        mealTrackerPanel.add(enterProtein);
        mealTrackerPanel.add(proteinMealInput);
        mealTrackerPanel.add(enterFat);
        mealTrackerPanel.add(fatMealInput);
        mealTrackerPanel.add(enterButton5);
        mealTrackerFrame.setVisible(true);
    }

    private void constructTracker() {
        mealTrackerFrame = new JFrame();
        mealTrackerFrame.setSize(300, 200);
        mealTrackerFrame.setResizable(false);

        mealTrackerPanel = new JPanel();
        mealTrackerPanel.setBackground(blue);
        mealTrackerFrame.add(mealTrackerPanel);
    }

    private void inputMeal() {
        enterButton5.setVisible(false);
        carbsMealInput.setVisible(false);
        proteinMealInput.setVisible(false);
        fatMealInput.setVisible(false);

        String carbsAmountMeal = carbsMealInput.getText();
        String proteinAmountMeal = proteinMealInput.getText();
        String fatAmountMeal = fatMealInput.getText();

        selected.addCarbsConsumed(Integer.parseInt(carbsAmountMeal));
        selected.addProteinConsumed(Integer.parseInt(proteinAmountMeal));
        selected.addFatConsumed(Integer.parseInt(fatAmountMeal));

        enterCarbs.setText("Carbs consumed: " + carbsAmountMeal + " (g)");
        enterProtein.setText("Protein consumed: " + proteinAmountMeal + " (g)");
        enterFat.setText("Fat consumed: " + fatAmountMeal + " (g)");
    }

    //EFFECTS: prints summary of daily intake and goals
    private void viewSummary() {
        int difference = selected.compareCalorieGoals();
        JLabel calGoal = new JLabel("Your total calorie goal was:" + " " + selected.getCalorieGoal());
        JLabel calConsumed = new JLabel("Calories consumed today was:" + " " + selected.getTotalCaloriesConsumed());
        JLabel metGoalOrNot = new JLabel("");
        if (selected.compareCalorieGoals() == 0) {
            metGoalOrNot.setText("You met your calorie goal!");
        } else if (selected.compareCalorieGoals() > 0) {
            metGoalOrNot.setText("You were over your calorie goal by" + " " + difference);
        } else if (selected.compareCalorieGoals() < 0) {
            metGoalOrNot.setText("You were under your calorie goal by" + " " + -difference);
        }
        trackerPanel.add(calGoal);
        trackerPanel.add(calConsumed);
        trackerPanel.add(metGoalOrNot);
        trackerFrame.setVisible(true);
    }

    private void loadWeeklySummary() {
        this.add(loadLabel);
        try {
            weeklysummary = jsonReader.read();
            loadLabel.setText("Loaded " + weeklysummary.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            JLabel exceptionLabel = new JLabel("Unable to read from file: " + JSON_STORE);
            this.add(exceptionLabel);
        }
        setVisible(true);
    }

    private void printWeeklySummary() {
        this.add(printLabel);
        this.add(dayOfWeekLabel);

        printLabel.setText("Here is the weekly summary from Monday to Sunday:");
        List<DailyMacros> dailyMacros = weeklysummary.getWeeklySummaryList();

        for (DailyMacros dm : dailyMacros) {
            JLabel dayOfWeekLabel = new JLabel(dm.formatNicely());
            this.add(dayOfWeekLabel);
        }
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "trackButton":
                runTracker();
                break;
            case "loadButton":
                loadWeeklySummary();
                break;
            case "printButton":
                printWeeklySummary();
                break;
        }
    }
}