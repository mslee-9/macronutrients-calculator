# My Personal Project

## Calories & Macronutrients Tracker

This application helps **record** and **track** daily calories and macronutrients intake.

The application allows users to input their daily goals as well as intakes, 
and informs the users whether they have met their **daily goals**. 

*Macronutrients* include:
- Carbohydrates
- Proteins
- Fats

Calories and macronutrients are **central to health and fitness**. 
Whether your personal fitness goals are to lose weight, gain weight, build muscle, or lean down, 
a calories & macronutrients tracking application can be helpful in meeting those goals. 

This project of building a fitness-related app was of interest to me as I had previously 
been tracking my macronutrients along with having a rigorous strength-training routine. After couple years of tracking 
my food intake, I became familiar with the appropriate intake for myself and paused tracking. 

However, now having no access to public gyms and different fitness goals, I thought a calories & macronutrients 
tracking application where I can add needed functionalities as I see fit would not only be interesting, but be 
helpful in improving my health and overall wellness. 

##User Stories
In the context of a calories & macronutrients tracker application: 
- As a user, I want to be able to set daily calories & macronutrients goals
- As a user, I want to be able to see calculated macronutrient goals in grams (g)
- As a user, I want to be able to input one or more meals for each day and store in weekly record
- As a user, I want to be able to see whether daily goals were met
- As a user, I want to be able to save my daily records 
- As a user, I want to be able to load and view printed weekly records from a file

##Phase 4: Task 2
For task 2 of phase 4, I chose to test and design classes that are robust. 
- DailyMacros class has three methods addCarbsConsumed, addProteinConsumed, and addFatConsumed that throw 
InvalidAmountException if the entered amount is less than zero. 
- WeeklySummary class has getDailyMacro method that throws InvalidWeekIndex Exception
if the index inputted is not between 0 and 6 inclusive. 

##Phase 4: Task 3
If I had more time to work on the project, I would work on refactoring the GUI class further. Right now, there are 
4 different frames: main frame, tracker frame, saver pop-up frame, and input meal pop-up frame. 

I would have to work on figuring how to refactor the GUI class such that these interacting components can be safely 
refactored and re-routed within the main GUI class. Additionally there are panels, labels, and buttons all currently
stored in the GUI class that can be refactored into the 4 additional classes discussed above.
