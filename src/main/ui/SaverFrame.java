package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class SaverFrame extends JFrame {

    private TrackerFrame trackerFrame;
    private JPanel savePanel;

    // EFFECTS: make a new frame and panel that goes in it for the saver
    public SaverFrame(TrackerFrame trackerFrame) {
        super("Do you want to save?");

        this.setSize(400, 100);
        this.setResizable(false);
        this.trackerFrame = trackerFrame;

        savePanel = new JPanel();
        savePanel.setBackground(Color.WHITE);
    }

    // MODIFIES: this
    // EFFECTS: Calls method that construct saver frame and panel, displays two buttons "Save" and "Don't Save"
    //           If user selects "Save", save data entered so far, exit tracker
    //           If user selects "Don't save", exit tracker without saving
    protected void promptSave() {
        trackerFrame.getGui().soundEffects.playExitSound();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            saveWeeklySummary();
            this.dispose();
            trackerFrame.dispose();
        });
        JButton dontSaveButton = new JButton("Don't save");
        dontSaveButton.addActionListener(e -> {
            this.dispose();
            trackerFrame.dispose();
        });
        savePanel.add(saveButton);
        savePanel.add(dontSaveButton);
        this.add(savePanel);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: saves weekly summary data to a JSON file
    //           If file cannot be found, throw FileNotFoundException
    private void saveWeeklySummary() {
        try {
            trackerFrame.getGui().jsonWriter.open();
            trackerFrame.getGui().jsonWriter.write(trackerFrame.getGui().getWeeklySummary());
            trackerFrame.getGui().jsonWriter.close();

            JLabel saveSuccess = new JLabel("Saved " + trackerFrame.getGui().getWeeklySummary().getName());
            savePanel.add(saveSuccess);
            this.setVisible(true);
        } catch (FileNotFoundException e) {
            JLabel saveFail = new JLabel("Unable to write to file: ");
            savePanel.add(saveFail);
            this.setVisible(true);
        }
    }
}
