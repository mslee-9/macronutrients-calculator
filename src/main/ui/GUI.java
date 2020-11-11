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
import java.net.URL;
import java.util.List;
import javax.sound.sampled.*;

//SOURCES (This class was built using demo's in the websites below):
//     Stack OverFlow: https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
//     Oracle Java Documentation: https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html
//                                https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
//                                https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
//     CPSC 210 Resources: SpaceInvaders, RobustTrafficLights, and DrawingPlayer
//Graphical User Interface (GUI) for Calories & macros tracking application
public class GUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/weeklySummary.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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
    private JLabel enterCarbs = new JLabel();
    private JLabel enterProtein = new JLabel();
    private JLabel enterFat = new JLabel();
    private JLabel invalidMeal = new JLabel("Enter integer number for each macro!");
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

    private JPanel savePanel;
    private JPanel mealTrackerPanel;

    private String daySelected;
    String carbsGoalString;
    String proteinGoalString;
    String fatGoalString;

    //EFFECTS: constructs GUI class and calls method to construct main panel
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

    //EFFECTS: constructs main panel with three buttons, "Track", "Load Data", and "Print Data"
    //         and adds the buttons to the main action listener
    private void constructMainPanel() {
        JPanel mainPanel = new JPanel();

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
        mainPanel.setSize(400, 800);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: constructs tracker frame and panel, prompts user to select name and week for the week, and
    //         when prompted to close, calls promptSave method
    private void runTracker() {
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setSize(400, 500);
        trackerFrame.setBackground(Color.WHITE);

        trackerPanel = new JPanel();
        trackerPanel.setBackground(lilac);
        trackerPanel.setLayout(new BoxLayout(trackerPanel, BoxLayout.Y_AXIS));

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

    //MODIFIES: this
    //EFFECTS: Calls method that construct saver frame and panel, displays two buttons "Save" and "Don't Save"
    //         If user selects "Save", save data entered so far, exit tracker
    //         If user selects "Don't save", exit tracker without saving
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

    //MODIFIES: this
    //EFFECTS: make a new frame and panel that goes in it for the saver
    private void constructSaver() {
        saveFrame = new JFrame("Do you want to save?");
        saveFrame.setSize(400, 100);
        saveFrame.setResizable(false);
        savePanel = new JPanel();
        savePanel.setBackground(coral);
    }

    //MODIFIES: this
    //EFFECTS: saves weekly summary data to a JSON file
    //         If file cannot be found, throw FileNotFoundException
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

    //MODIFIES: this
    //EFFECTS: Prompts user to enter name and week
    //         when "Enter" button is pressed, call processSelectDayOfWeek and selectDayOfWeek method
    public void selectNameAndWeek() {
        welcomeLabel = new JLabel("Please enter your name and week (e.g. Stella Week 1)");

        trackerPanel.add(welcomeLabel);
        trackerPanel.add(Box.createVerticalStrut(10));

        weekName = new JTextField(1);
        weekName.setMaximumSize(new Dimension(200, 25));
        trackerPanel.add(weekName);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSelectDayOfWeek();
                selectDayOfWeek();
            }
        });
        trackerPanel.add(enterButton);

        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: gets user input in String, displays selected name and week,
    //         constructs new weeklySummary instance with chosen name
    //         makes previous input fields and buttons invisible
    private void processSelectDayOfWeek() {
        String weekNameString = weekName.getText();
        welcomeLabel.setText("Name & Week: " + weekNameString);

        weeklysummary = new WeeklySummary(weekNameString);

        weekName.setVisible(false);
        enterButton.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: Prompts user to choose a day of week, calls processSelectDay,
    //         and calls promptInputGoals to continue execution
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
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: calls selectDay and selectDayString and displays the chosen day of week
    private void processSelectDay() {
        selectDay();
        selectDayString();
        daysOfWeek.setText("Day of Week: " + daySelected);
    }


    //REQUIRES: command must be one of mon, tue, wed, thu, fri, sat, or sun
    //MODIFIES: this
    //EFFECTS: selects day of the week, monday through sunday, in weekly summary corresponding to the index
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

    //REQUIRES: command must be one of mon, tue, wed, thu, fri, sat, or sun
    //MODIFIES: this
    //EFFECTS: selects and updates daySelected field with chosen day  of the week, monday through sunday
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

    //MODIFIES: this
    //EFFECTS: Calls method to input Calorie Goals to start prompting user's goal inputs
    private void promptInputGoals() {
        enterButton2.setVisible(false);
        weekDayName.setVisible(false);
        inputCalorieGoals();
    }

    //MODIFIES: this
    //EFFECTS: Prompts user to input calorie goals and when "Enter" button is clicked, call method to set calorie goals
    private void inputCalorieGoals() {
        calorieGoalInput = new JLabel("Enter daily calorie goal (kcal)");
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(calorieGoalInput);

        trackerPanel.add(Box.createVerticalStrut(10));
        calorieGoal = new JTextField(2);
        calorieGoal.setMaximumSize(new Dimension(200, 25));

        trackerPanel.add(calorieGoal);
        trackerFrame.add(trackerPanel);

        enterButton3 = new JButton("Enter");
        enterButton3.addActionListener(e -> setCalorieGoals());
        trackerPanel.add(enterButton3);
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Prompts user to input calorie goals and when "Enter" button is clicked, call method to set calorie goals
    //         Once valid calorie goal was inputted, call method to prompt inputting of Macros goals
    public void setCalorieGoals() {
        String calorieGoalText = calorieGoal.getText();
        calorieGoalInput.setText("Daily Calorie Goal: " + calorieGoalText + " kcal");
        int calorieGoalInt = Integer.parseInt(calorieGoalText);

        if (calorieGoalInt > 0) {
            selected.setCalorieGoal(calorieGoalInt);
            inputMacrosGoals();

        } else {
            calorieGoal.setVisible(false);
            enterButton3.setVisible(false);
            JLabel calorieUnderZero = new JLabel("Calorie goal must be greater than zero...");
            trackerPanel.add(calorieUnderZero);
            inputCalorieGoals();
        }
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Prompts user to input Macros goals and calls setMacroGoals when "Enter" button is pressed
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

    //MODIFIES: this
    //EFFECTS: Reads User input for Macro goals, sets previous input fields invisible, displays
    //         Percentage Macro goals
    //         If invalid percentages were entered, prompts reader to enter again else call displayMacroBreakdown
    private void setMacroGoals() {
        carbsGoalString = carbsGoal.getText();
        proteinGoalString = proteinGoal.getText();
        fatGoalString = fatGoal.getText();

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

    //MODIFIES: this
    //EFFECTS: Make previous input button invisible, calculate and display Macro breakdown for the day
    //         call continueTracking method to continue execution
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

        trackerPanel.add(Box.createVerticalStrut(20));
        trackerPanel.add(macroBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(carbBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(proteinBreakdown);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(fatBreakdown);
        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);

        continueTracking();
    }

    //MODIFIES: this
    //EFFECTS: Display two new buttons "Input Meal" and "View Summary"
    //         If user selects "Input Meal", call constructTracker then inputMealPrompt to continue execution
    //         If user selects "View Summary", call viewSummary method
    private void continueTracking() {
        JButton inputMeal = new JButton("Input Meal");

        inputMeal.addActionListener(e -> {
            constructTracker();
            inputMealPrompt();
        });
        JButton viewSummary = new JButton("View Summary");
        viewSummary.addActionListener(e -> viewSummary());

        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(inputMeal);
        trackerPanel.add(viewSummary);
    }

    //MODIFIES: this
    //EFFECTS: adds grams of macronutrients consumed to respective categories
    public void inputMealPrompt() {
        enterCarbs.setText("Enter grams of carbs consumed\n");
        enterProtein.setText("Enter grams of protein consumed\n");
        enterFat.setText("Enter grams of fat consumed\n");

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

    //MODIFIES: this
    //EFFECTS: constructs tracker frame and panel that is added to the frame
    private void constructTracker() {
        mealTrackerFrame = new JFrame();
        mealTrackerFrame.setSize(300, 200);
        mealTrackerFrame.setResizable(true);

        mealTrackerPanel = new JPanel();
        mealTrackerPanel.setBackground(blue);
        mealTrackerFrame.add(mealTrackerPanel);
    }

    //MODIFIES: this
    //EFFECTS: Makes previous input buttons and fields invisible, reads user inputs and if they're valid,
    //         output text showing user their input
    //         If inputs are invalid, prompts user to re-enter by showing warning message
    private void inputMeal() {
        enterButton5.setVisible(false);
        carbsMealInput.setVisible(false);
        proteinMealInput.setVisible(false);
        fatMealInput.setVisible(false);

        String carbsAmountMeal = carbsMealInput.getText();
        String proteinAmountMeal = proteinMealInput.getText();
        String fatAmountMeal = fatMealInput.getText();

        mealTrackerPanel.add(invalidMeal);
        invalidMeal.setVisible(false);

        if (!(carbsAmountMeal.equals("") || proteinAmountMeal.equals("") || fatAmountMeal.equals(""))) {
            selected.addCarbsConsumed(Integer.parseInt(carbsAmountMeal));
            selected.addProteinConsumed(Integer.parseInt(proteinAmountMeal));
            selected.addFatConsumed(Integer.parseInt(fatAmountMeal));

            enterCarbs.setText("Carbs consumed: " + carbsAmountMeal + " (g)");
            enterProtein.setText("Protein consumed: " + proteinAmountMeal + " (g)");
            enterFat.setText("Fat consumed: " + fatAmountMeal + " (g)");
        } else {
            invalidMeal.setVisible(true);
            mealTrackerPanel.setVisible(true);
            inputMealPrompt();
        }
    }

    //MODIFIES: this
    //EFFECTS: prints summary of daily intake and goals
    private void viewSummary() {
        int difference = selected.compareCalorieGoals();
        JLabel calGoal = new JLabel("Your total calorie goal was:" + " " + selected.getCalorieGoal());
        JLabel calConsumed = new JLabel("Calories consumed today was:" + " "
                + selected.getTotalCaloriesConsumed());
        JLabel metGoalOrNot = new JLabel("");

        if (selected.compareCalorieGoals() == 0) {
            metGoalOrNot.setText("You met your calorie goal!");
        } else if (selected.compareCalorieGoals() > 0) {
            metGoalOrNot.setText("You were over your calorie goal by" + " " + difference);
        } else if (selected.compareCalorieGoals() < 0) {
            metGoalOrNot.setText("You were under your calorie goal by" + " " + -difference);
        }
        trackerPanel.add(Box.createVerticalStrut(30));
        trackerPanel.add(calGoal);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(calConsumed);
        trackerPanel.add(Box.createVerticalStrut(10));
        trackerPanel.add(metGoalOrNot);
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: loads weekly summary data from JSON file
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

    //MODIFIES: this
    //EFFECTS: prints weekly summary data from Monday through Sunday
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

    //EFFECTS: Get action command and perform appropriate task for buttons on the main panel
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