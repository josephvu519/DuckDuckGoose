public class Clause {
    //Type: 1 = "bird"; 2 = "total"
    private int type;
    private int bird;
    private int total;
    private int position;
    Clause(String typeClause, String char2, String char3){
        if (typeClause.equals("bird")){
            type = 1;
            position = Integer.parseInt(char2);
            bird = Solution.birdType(char3);
        }
        else{
            type = 2;
            bird = Solution.birdType(char2);
            total = Integer.parseInt(char3);
        }
    }

    public boolean evaluate(int[] testCombination){
        //Bird
        if(type == 1){
            if (testCombination[position-1  ] == bird)
                return true;
            else
                return false;
        }
        //Total
        else{
            //1-duck;2-goose;3-swan
            switch(bird){
                case 1:
                    if (Solution.duckCount(testCombination) == total)
                        return true;
                    else
                        return false;
                case 2:
                    if (Solution.gooseCount(testCombination) == total)
                        return true;
                    else
                        return false;
                case 3:
                    if (Solution.swanCount(testCombination) == total)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
        }
    }
}
