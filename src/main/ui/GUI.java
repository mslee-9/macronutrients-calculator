package ui;

import model.DailyMacros;
import model.WeeklySummary;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

//Graphical User Interface (GUI) for Calories & macros tracking application
public class GUI extends JFrame implements ActionListener {

    protected static final String JSON_STORE = "./data/weeklySummary.json";

    private final JLabel loadLabel = new JLabel();
    private final JLabel printLabel = new JLabel();
    private final JLabel dayOfWeekLabel = new JLabel();

    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;

    protected SoundEffects soundEffects = new SoundEffects();
    private DailyMacros selected;
    private WeeklySummary weeklySummary;

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
        TrackerFrame trackerFrame = new TrackerFrame(this);
        trackerFrame.runTrackerFrame();
    }


    //MODIFIES: this
    //EFFECTS: loads weekly summary data from JSON file
    private void loadWeeklySummary() {
        this.add(loadLabel);
        try {
            weeklySummary = jsonReader.read();
            loadLabel.setText("Loaded " + weeklySummary.getName() + " from " + JSON_STORE);
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
        List<DailyMacros> dailyMacros = weeklySummary.getWeeklySummaryList();

        for (DailyMacros dm : dailyMacros) {
            JLabel dayOfWeekLabel = new JLabel(dm.formatNicely());
            this.add(dayOfWeekLabel);
        }
        setVisible(true);
    }

    public void setWeeklySummary(WeeklySummary weeklysummary) {
        this.weeklySummary = weeklysummary;
    }

    public void setSelected(DailyMacros selected) {
        this.selected = selected;
    }

    public WeeklySummary getWeeklySummary() {
        return weeklySummary;
    }

    public DailyMacros getSelected() {
        return selected;
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