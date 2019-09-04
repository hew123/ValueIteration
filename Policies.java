public class Policies {

    //curr, up, down, left, right
    static public void valueIteration(Cell cell0, Cell cell1, Cell cell2, Cell cell3, Cell cell4, double DISCOUNT){

        double U1, U2, U3, U4;
        double UP, DOWN, LEFT, RIGHT;

        //if the cell is a wall, return the utility of the main cell
        if(cell1.getState() == Cell.State.WALL){
            UP = cell0.getUtility();
        }else{
            UP = cell1.getUtility();
        }

        if(cell2.getState() == Cell.State.WALL){
            DOWN = cell0.getUtility();
        }else{
            DOWN = cell2.getUtility();
        }

        if(cell3.getState() == Cell.State.WALL){
            LEFT = cell0.getUtility();
        }else{
            LEFT = cell3.getUtility();
        }

        if(cell4.getState() == Cell.State.WALL){
            RIGHT = cell0.getUtility();
        }else{
            RIGHT = cell4.getUtility();
        }

        // calculate the ulities of different action
        U1 = 0.8*UP + 0.1*LEFT + 0.1*RIGHT;
        U2 = 0.8*DOWN + 0.1*LEFT + 0.1*RIGHT;
        U3 = 0.8*LEFT + 0.1*UP + 0.1*DOWN;
        U4 = 0.8*RIGHT + 0.1*UP + 0.1*DOWN;

        //take the max utility
        double temp1 = Math.max(U1,U2);
        double temp2 = Math.max(U3,U4);
        double finaltemp = Math.max(temp1,temp2);

        //set policy
        if(finaltemp == U1){
            cell0.setTempPolicy(Cell.Policy._UP_);
        }

        if(finaltemp == U2){
            cell0.setTempPolicy(Cell.Policy.DOWN);
        }

        if(finaltemp == U3){
            cell0.setTempPolicy(Cell.Policy.LEFT);
        }

        if(finaltemp == U4){
            cell0.setTempPolicy(Cell.Policy.RIGHT);
        }

        //return utility
        double tempUtility = DISCOUNT*finaltemp + cell0.getReward();
        cell0.setTempUtility(tempUtility);

    }

    static public void policyEvaluation(Cell cell0, Cell cell1, Cell cell2, Cell cell3, Cell cell4, double DISCOUNT){

        double U1, U2, U3, U4;
        double UP, DOWN, LEFT, RIGHT;

        //if the cell is a wall, return the utility of the main cell
        if(cell1.getState() == Cell.State.WALL){
            UP = cell0.getUtility();
        }else{
            UP = cell1.getUtility();
        }

        if(cell2.getState() == Cell.State.WALL){
            DOWN = cell0.getUtility();
        }else{
            DOWN = cell2.getUtility();
        }

        if(cell3.getState() == Cell.State.WALL){
            LEFT = cell0.getUtility();
        }else{
            LEFT = cell3.getUtility();
        }

        if(cell4.getState() == Cell.State.WALL){
            RIGHT = cell0.getUtility();
        }else{
            RIGHT = cell4.getUtility();
        }

        //utility of each action
        U1 = 0.8*UP + 0.1*LEFT + 0.1*RIGHT;
        U2 = 0.8*DOWN + 0.1*LEFT + 0.1*RIGHT;
        U3 = 0.8*LEFT + 0.1*UP + 0.1*DOWN;
        U4 = 0.8*RIGHT + 0.1*UP + 0.1*DOWN;

        switch(cell0.getPolicy()){
            case _UP_:
                cell0.setTempUtility(DISCOUNT*U1 + cell0.getReward());
                break;
            case DOWN:
                cell0.setTempUtility(DISCOUNT*U2 + cell0.getReward());
                break;
            case LEFT:
                cell0.setTempUtility(DISCOUNT*U3 + cell0.getReward());
                break;
            case RIGHT:
                cell0.setTempUtility(DISCOUNT*U4 + cell0.getReward());
                break;
        }
    }

    static public void policyIteration(Cell cell0, Cell cell1, Cell cell2, Cell cell3, Cell cell4, double DISCOUNT){

        double U1, U2, U3, U4;
        double UP, DOWN, LEFT, RIGHT;

        //if the cell is a wall, return the utility of the main cell
        if(cell1.getState() == Cell.State.WALL){
            UP = cell0.getUtility();
        }else{
            UP = cell1.getUtility();
        }

        if(cell2.getState() == Cell.State.WALL){
            DOWN = cell0.getUtility();
        }else{
            DOWN = cell2.getUtility();
        }

        if(cell3.getState() == Cell.State.WALL){
            LEFT = cell0.getUtility();
        }else{
            LEFT = cell3.getUtility();
        }

        if(cell4.getState() == Cell.State.WALL){
            RIGHT = cell0.getUtility();
        }else{
            RIGHT = cell4.getUtility();
        }

        //utility of each action
        U1 = 0.8*UP + 0.1*LEFT + 0.1*RIGHT;
        U2 = 0.8*DOWN + 0.1*LEFT + 0.1*RIGHT;
        U3 = 0.8*LEFT + 0.1*UP + 0.1*DOWN;
        U4 = 0.8*RIGHT + 0.1*UP + 0.1*DOWN;

        double temp1 = Math.max(U1,U2);
        double temp2 = Math.max(U3,U4);
        double finaltemp = Math.max(temp1,temp2);

        //return policy with the max utility
        if(finaltemp == U1){
            cell0.setTempPolicy(Cell.Policy._UP_);
        }

        else if(finaltemp == U2){
            cell0.setTempPolicy(Cell.Policy.DOWN);
        }

        else if(finaltemp == U3){
            cell0.setTempPolicy(Cell.Policy.LEFT);
        }

        else if(finaltemp == U4){
            cell0.setTempPolicy(Cell.Policy.RIGHT);
        }

    }
}
