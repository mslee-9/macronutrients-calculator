package ui;

import javafx.scene.input.InputMethodTextRun;
import model.DailyMacros;
import model.WeeklySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.midi.Soundbank;
import javax.sound.midi.Track;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {

//  Labels
    private JLabel label;
    private JLabel loadLabel;
    private JLabel printLabel;
    private JLabel welcomeLabel;
    private JLabel trackerLabel;
    private JPanel panel;
    private DefaultListModel listModel;
    private JList list;
    private JTextField field;
    private JTextField weekDayName;
    JTextField calorieGoal;
    private JPanel trackerPanel;
    private JLabel daysOfWeek;

    private static final String JSON_STORE = "./data/weeklySummary.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DailyMacros selected;
    private WeeklySummary weeklysummary;
    private Scanner input = new Scanner(System.in);
    private JFrame trackerFrame;
    private JTextField weekName;

    public GUI() {
        super("Tracker Application");
        this.setSize(400, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.PINK);
        this.setVisible(true);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setLayout(new FlowLayout());
        JButton trackButton = new JButton("Track");
        trackButton.setActionCommand("trackButton");
        trackButton.addActionListener(this);

        JButton loadButton = new JButton("Load Data");
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);

        JButton printButton = new JButton("Print Data");
        printButton.setActionCommand("printButton");
        printButton.addActionListener(this);

        add(trackButton);
        add(loadButton);
        add(printButton);

        setVisible(true);
        setResizable(false);
    }

    public void runTracker() {
        trackerFrame = new JFrame("Tracker");
        trackerFrame.setSize(400, 400);
        trackerFrame.setBackground(Color.WHITE);
        trackerFrame.setVisible(true);

        trackerPanel = new JPanel();
        trackerFrame.add(trackerPanel);
        trackerPanel.setVisible(true);

        selectNameAndWeek();
    }

    public void selectNameAndWeek() {
        welcomeLabel = new JLabel("Please enter your name and week (e.g. Stella Week 1)");
        trackerPanel.add(welcomeLabel);

        weekName = new JTextField(1);
        weekName.setColumns(10);
        trackerPanel.add(weekName);

        JButton enterButton = new JButton("Enter");
        enterButton.setActionCommand("enterButton");
        enterButton.addActionListener(this);
        trackerPanel.add(enterButton);
        trackerPanel.setVisible(true);
    }

    private void selectDayOfWeek() {
        weeklysummary = new WeeklySummary(weekName.getText());
        daysOfWeek = new JLabel("Type in day of week (e.g. Mon for Monday)");
        trackerPanel.add(daysOfWeek);

        weekDayName = new JTextField(1);
        weekDayName.setColumns(5);
        trackerPanel.add(weekDayName);

        JButton enterButton2 = new JButton("Enter2");
        enterButton2.setActionCommand("enterButton2");
        enterButton2.addActionListener(this);
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
        JLabel daySelected = new JLabel("Day Selected!");
        trackerPanel.add(daySelected);
    }

    private void promptInputGoals() {
        inputCalorieGoals();
        inputMacrosGoals();
    }

    public void inputCalorieGoals() {
        JLabel calorieGoalPrompt = new JLabel("<html>Enter daily calorie goal<br></html>");
        trackerPanel.add(calorieGoalPrompt);

        calorieGoal = new JTextField();
        calorieGoal.setColumns(5);

        trackerPanel.add(calorieGoal);
        trackerFrame.add(trackerPanel);
        trackerPanel.setVisible(true);
        trackerFrame.setVisible(true);

        JButton enterButton3 = new JButton("Enter3");
        enterButton3.setActionCommand("enterButton3");
        enterButton3.addActionListener(this);
        trackerPanel.add(enterButton3);
    }

    public void setCalorieGoals() {
        String calorieGoalText = calorieGoal.getText();
        int calorieGoalInt = Integer.parseInt(calorieGoalText);

        if (calorieGoalInt > 0) {
            selected.setCalorieGoal(calorieGoalInt);
        } else {
            JLabel calorieUnderZero = new JLabel(" Calorie goal must be greater than zero...");
            trackerPanel.add(calorieUnderZero);
            inputCalorieGoals();
        }
        trackerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets macros goals for selected day
    public void inputMacrosGoals() {
        JLabel carbsGoalInput = new JLabel("Enter target carbs goal as % (e.g. 0.4 for 40%)\n");
        JTextField carbsGoal = new JTextField();
        carbsGoal.setColumns(5);
        trackerPanel.add(carbsGoalInput);
        trackerPanel.add(carbsGoal);

        //double carbsAmount = input.nextDouble();

        JLabel proteinGoalInput = new JLabel("Enter target protein goal as % (e.g. 0.3 for 30%)\n");
        JTextField proteinGoal = new JTextField();
        carbsGoal.setColumns(5);
        trackerPanel.add(proteinGoalInput);
        trackerPanel.add(proteinGoal);

      //  double proteinAmount = input.nextDouble();

        JLabel fatGoalInput = new JLabel("Enter target fat goal as % (e.g. 0.3 for 30%)\n");
        JTextField fatGoal = new JTextField();
        carbsGoal.setColumns(5);
        trackerPanel.add(fatGoalInput);
        trackerPanel.add(fatGoal);
        double fatAmount = input.nextDouble();

    //    double totalAmount = carbsAmount + proteinAmount + fatAmount;
//
//        if (totalAmount != 1.0) {
//            System.out.println("Enter three percentages that add up to 100%");
//        } else {
//            selected.setCarbsGoalsPercentage(carbsAmount);
//            selected.setProteinGoalsPercentage(proteinAmount);
//            selected.setFatGoalsPercentage(fatAmount);
//            selected.calculateMacroGoals();
//            JLabel macroBreakdown = new JLabel("Your target macro breakdown in grams are:");
//            JLabel carbBreakdown = new JLabel("Carbs:" + " " + selected.getCarbsGoalGrams());
//            JLabel proteinBreakdown = new JLabel("Protein:" + " " + selected.getProteinGoalGrams());
//            JLabel fatBreakdown = new JLabel("Fat:" + " " + selected.getFatGoalGrams());
//            trackerPanel.add(macroBreakdown);
//            trackerPanel.add(carbBreakdown);
//            trackerPanel.add(proteinBreakdown);
//            trackerPanel.add(fatBreakdown);
//        }
    }

    private void loadWeeklySummary() {
        try {
            weeklysummary = jsonReader.read();
            loadLabel =  new JLabel("<html>Loaded "
                    + weeklysummary.getName() + " from " + JSON_STORE + "</br></html>");
        } catch (IOException e) {
            JLabel exceptionLabel = new JLabel("Unable to read from file: " + JSON_STORE);
            this.add(exceptionLabel);
        }
        this.add(loadLabel);
        setVisible(true);
    }

    private void printWeeklySummary() {
        printLabel = new JLabel("<html>Here is the weekly summary from Monday to Sunday:<br/></html>");
        this.add(printLabel);
        List<DailyMacros> dailyMacros = weeklysummary.getWeeklySummaryList();

        for (DailyMacros dm: dailyMacros) {
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
            case "enterButton":
                selectDayOfWeek();
                break;
            case "enterButton2":
                selectDay();
                promptInputGoals();
                break;
            case "enterButton3":
                setCalorieGoals();
                break;
            default:
                printWeeklySummary();
                break;
        }
    }

}

