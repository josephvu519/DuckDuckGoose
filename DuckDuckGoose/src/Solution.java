//Joseph Vu
//CMPSC 463: Assignment 2
//Knowledge of string split credited to https://stackoverflow.com/questions/4674850/converting-a-sentence-string-to-a-string-array-of-words-in-java
//and cleanUp method credited to https://www.geeksforgeeks.org/sort-a-string-in-java-2-different-ways/
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    public static void main (String[] args){
        Scanner reader = new Scanner(System.in);
        int games = Integer.parseInt(reader.nextLine());
        //# of Games

        int[] combination;
        ArrayList<Question> questionList;

        //This loop represents how many games are played
        for (int gameCount = 1; gameCount <= games; gameCount++){
            combination = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            questionList = new ArrayList<>();

            reader.nextLine();
            String temp = reader.nextLine();
            String[] tempArray = temp.split("\\s+");
            int numQuestions = Integer.parseInt(tempArray[0]);
            int lies = Integer.parseInt(tempArray[1]);

            String[] questions = new String[numQuestions];
            String[] answers = new String[numQuestions];
            String[] questionBreakdown;

            for(int questionCount = 0; questionCount < numQuestions; questionCount++){
                questions[questionCount] = reader.nextLine();
                questionBreakdown = questions[questionCount].split("\\s+");
                answers[questionCount] = reader.nextLine();
                questionList.add(new Question(answers[questionCount]));
                if (questionBreakdown.length > 3){
                    if(questionBreakdown[3].equals("or"))
                        questionList.get(questionCount).setOperator(1);
                }
                //Breaks down each question line into clauses and makes a new clause to add to the class
                for(int i = 0; i < questionBreakdown.length; i = i + 4){
                    questionList.get(questionCount).addClause(new Clause(questionBreakdown[i], questionBreakdown[i+1], questionBreakdown[i+2]));
                }
            }

            combination = giveCombination(combination, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0, lies, questionList);

            //Translates the integer array and outputs the final answer for the specific game
            String[] stringCombination = new String[10];
            for (int i = 0; i < combination.length; i++){
                stringCombination[i] = translate(combination[i]);
                System.out.print(stringCombination[i] + " ");
            }
            System.out.println();
        }//end game loop
    }

    public static int[] giveCombination(int[] combination, int[] testCombination, int counter, int lies, ArrayList<Question> questionList){
        if(counter > 10){
            return combination;
        }
        if(isValid(testCombination, counter, lies, questionList)){
            combination = merge(testCombination, combination);
        }
        if(counter < 10) {
            for (int i = 1; i <=3; i++) {
                testCombination[counter] = i;
                combination = giveCombination(combination, testCombination, counter + 1, lies, questionList);
            }
        }
        return combination;
    }


    //takes the current int array and validates if it completely correct
    public static boolean isValid(int[] testCombination, int counter, int lies, ArrayList<Question> questionList){
        //Always spits out false if the 10 spots aren't filled
        if(counter < 10) {
            return false;
        }
        int lieCounter = 0;

        for (int i = 0; i < questionList.size(); i++){
            if (!(questionList.get(i).evaluateQuestion(testCombination)))
                lieCounter++;
        }

        //The moment of truth
        if (lieCounter == lies)
            return true;
        else
            return false;
    }


    //------------------------------
    //Utility Helpers
    //------------------------------
    /*
    1- d
    2- g
    3- s
    4- dg
    5- ds
    6- gs
    7- dgs
     */
    public static int[] merge(int[] adder, int[] addee){
        for (int i = 0; i <= 9; i++){
            if (addee[i] != 7){
                switch (addee[i]){
                    case 6:
                        if(adder[i] == 1)
                            addee[i] = 7;
                        break;
                    case 5:
                        if(adder[i] == 2)
                            addee[i] = 7;
                        break;
                    case 4:
                        if(adder[i] == 3)
                            addee[i] = 7;
                        break;
                    case 3:
                        if(adder[i] == 1)
                            addee[i] = 5;
                        else if(adder[i] == 2)
                            addee[i] = 6;
                        break;
                    case 2:
                        if(adder[i] == 1)
                            addee[i] = 4;
                        else if(adder[i] == 3)
                            addee[i] = 6;
                        break;
                    case 1:
                        if(adder[i] == 2)
                            addee[i] = 4;
                        else if(adder[i] == 3)
                            addee[i] = 5;
                        break;
                    case 0:
                        addee[i] = adder[i];
                }
            }
        }
        return addee;
    }
    /*
        public static String[] merge(String[] adder, String[] addee){

        for (int i = 0; i <= 9; i++){
            if (!(addee[i].contains(adder[i]))){
                        addee[i] = addee[i] + adder[i];
            }
        }
        return addee;
    }
     */
        /*
    1- d
    2- g
    3- s
    4- dg
    5- ds
    6- gs
    7- dgs
     */
    public static String translate (int bird){
        String temp = "";
        switch (bird){
            case 7:
                temp = "dgs";
                break;
            case 6:
                temp = "gs";
                break;
            case 5:
                temp = "ds";
                break;
            case 4:
                temp = "dg";
                break;
            case 3:
                temp = "s";
                break;
            case 2:
                temp = "g";
                break;
            case 1:
                temp = "d";
                break;
            case 0:
                temp = "";
        }
        return temp;
    }
    public static int birdType(String t){
        switch (t){
            case "d":
                return 1;
            case "g":
                return 2;
            case "s":
                return 3;
            default:
                return 0;
        }
    }


    public static boolean[] convertBoolean(String[] answers){
        boolean[] temp = new boolean[answers.length];
        for (int i = 0; i < answers.length; i++){
            if (answers[i].equals("yes"))
                temp[i] = true;
            else
                temp[i] = false;
        }
        return temp;
    }

    //Helper counters
    //lst contents can only be single digits
    public static int duckCount(int[] lst){
        int counter = 0;
        for (int i = 0; i < lst.length; i++){
            if(lst[i] == 1)
                counter++;
        }
        return counter;
    }
    public static int gooseCount(int[] lst){
        int counter = 0;
        for (int i = 0; i < lst.length; i++){
            if(lst[i] == 2)
                counter++;
        }
        return counter;
    }
    public static int swanCount(int[] lst){
        int counter = 0;
        for (int i = 0; i < lst.length; i++){
            if(lst[i] == 3)
                counter++;
        }
        return counter;
    }

}
