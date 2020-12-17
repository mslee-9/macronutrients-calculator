package ui;

import model.exceptions.InvalidAmountException;

import javax.swing.*;
import java.awt.*;

public class MealTrackerFrame extends JFrame {

    private TrackerFrame trackerFrame;

    private JLabel enterCarbs = new JLabel();
    private JLabel enterProtein = new JLabel();
    private JLabel enterFat = new JLabel();
    private JLabel invalidMeal = new JLabel("Enter integer number for each macro!");

    private JPanel mealTrackerPanel;
    private JButton enterButton5;

    private JTextField carbsMealInput;
    private JTextField proteinMealInput;
    private JTextField fatMealInput;

    // EFFECTS: constructs tracker frame and panel that is added to the frame
    public MealTrackerFrame(TrackerFrame trackerFrame) {
        this.setSize(300, 200);
        this.setResizable(true);

        mealTrackerPanel = new JPanel();
        mealTrackerPanel.setBackground(Color.WHITE);
        this.add(mealTrackerPanel);
        this.trackerFrame = trackerFrame;
    }

    // MODIFIES: this
    // EFFECTS: Makes previous input buttons and fields invisible, reads user inputs and if they're valid,
    //           output text showing user their input
    //           If inputs are invalid, prompts user to re-enter by showing warning message
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
        try {
            if (!(carbsAmountMeal.equals("") || proteinAmountMeal.equals("") || fatAmountMeal.equals(""))) {
                trackerFrame.getGui().getSelected().addCarbsConsumed(Integer.parseInt(carbsAmountMeal));
                trackerFrame.getGui().getSelected().addProteinConsumed(Integer.parseInt(proteinAmountMeal));
                trackerFrame.getGui().getSelected().addFatConsumed(Integer.parseInt(fatAmountMeal));

                setMealAmounts(carbsAmountMeal, proteinAmountMeal, fatAmountMeal);
            } else {
                invalidMeal.setVisible(true);
                mealTrackerPanel.setVisible(true);
                inputMealPrompt();
            }
        } catch (InvalidAmountException e) {
            System.out.println("Amount must be greater than 0");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds grams of macronutrients consumed to respective categories
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
        this.setVisible(true);
    }

    private void setMealAmounts(String carbsAmountMeal, String proteinAmountMeal, String fatAmountMeal) {
        enterCarbs.setText("Carbs consumed: " + carbsAmountMeal + " (g)");
        enterProtein.setText("Protein consumed: " + proteinAmountMeal + " (g)");
        enterFat.setText("Fat consumed: " + fatAmountMeal + " (g)");
    }
}
