public class Cell {

    private double reward;
    private double utility;
    private double tempUtility;
    private State state;
    private Policy policy;
    private Policy tempPolicy;

    enum State{
        NONE, GREEN, BROWN, WALL
    }

    enum Policy{
        NONE, _UP_, DOWN, LEFT, RIGHT
    }

    public Cell(State state){

        this.state = state;

        switch(state){
            case NONE:
                this.reward = -0.04;
                break;
            case GREEN:
                this.reward = +1;
                break;
            case BROWN:
                this.reward = -1;
                break;
            case WALL:
                this.reward = 0;
                break;
        }

        policy = Policy.NONE;
        tempPolicy = Policy.NONE;
        utility= 0;
        tempUtility = 0;

    }

    public State getState(){
        return state;
    }

    public double getReward(){
        return reward;
    }

    public double getUtility(){
        return utility;
    }

    public void setUtility(double x){
        this.utility = x;
    }

    public double getTempUtility(){
        return tempUtility;
    }

    public void setTempUtility(double x){
        this.tempUtility = x;
    }

    public Policy getPolicy(){
        return this.policy;
    }

    public void setPolicy(Policy policy){
        this.policy = policy;
    }

    public Policy getTempPolicy(){
        return this.tempPolicy;
    }

    public void setTempPolicy(Policy policy){
        this.tempPolicy = policy;
    }
}
