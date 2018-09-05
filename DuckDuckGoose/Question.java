import java.util.ArrayList;

public class Question {
    private ArrayList<Clause> clauses;
    private boolean answer;
    //0 - "and"; 1 - "or"
    private int operator;

    Question(String ans){
        clauses = new ArrayList<>();
        //Default is "and" unless specified otherwise
        operator = 0;
        if(ans.equals("yes"))
            answer = true;
        else
            answer = false;
    }

    public void addClause(Clause santa){
        clauses.add(santa);
    }

    public void setOperator(int i){
        operator = i;
    }

    public boolean evaluateQuestion(int[] testCombination){
        boolean correctness;
        if (operator == 1){
            correctness = false;
            for (int i = 0; i < clauses.size(); i++){
                if(clauses.get(i).evaluate(testCombination))
                    correctness = true;
            }
        }
        else{
            correctness = true;
            for (int i = 0; i < clauses.size(); i++){
                if(!(clauses.get(i).evaluate(testCombination)))
                    correctness = false;
            }
        }
        return (correctness == answer);
    }
}
