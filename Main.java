//import javafx.scene.chart.XYChart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        final double DISCOUNT = 0.99;

        Cell[][] cells;
        cells = new Cell[8][8];

        cells[1][1] = new Cell(Cell.State.GREEN); //0,0
        cells[1][3] = new Cell(Cell.State.GREEN); //0.2
        cells[1][6] = new Cell(Cell.State.GREEN); //0.5
        cells[2][4] = new Cell(Cell.State.GREEN); //1,3
        cells[3][5] = new Cell(Cell.State.GREEN); //2,4
        cells[4][6] = new Cell(Cell.State.GREEN); //3,5

        cells[2][2] = new Cell(Cell.State.BROWN); //1,1
        cells[2][6] = new Cell(Cell.State.BROWN); //1,5
        cells[3][3] = new Cell(Cell.State.BROWN); //2,2
        cells[4][4] = new Cell(Cell.State.BROWN); //3,3
        cells[5][5] = new Cell(Cell.State.BROWN); //4,4

        cells[1][2] = new Cell(Cell.State.WALL); //0,1
        cells[2][5] = new Cell(Cell.State.WALL); //1,4
        cells[5][2] = new Cell(Cell.State.WALL); //4,1
        cells[5][3] = new Cell(Cell.State.WALL); //4,2
        cells[5][4] = new Cell(Cell.State.WALL); //4,3

        //build a walled-shell around the grid
        for(int i=0; i<8;i++){
            cells[0][i] = new Cell(Cell.State.WALL);
            cells[7][i] = new Cell(Cell.State.WALL);
            cells[i][0] = new Cell(Cell.State.WALL);
            cells[i][7] = new Cell(Cell.State.WALL);
        }

        //initialize all other empty cells
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                if(cells[i][j] == null){
                    cells[i][j] = new Cell(Cell.State.NONE);
                }
            }
        }

        //initialize all other empty cells
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                System.out.print("["+cells[i][j].getState()+"]");
            }
            System.out.println();
        }

        //VALUE ITERATION

        //for plotting
        double[] xData = new double[1148];
        double[] yData = new double[1148];

        double maxerr = 0.00002; //epsilon
        int count1 = 0;

        while(maxerr > 0.00001){
            maxerr = 0;
            for(int i=1; i<7; i++){
                for(int j=1; j<7; j++){
                    if(cells[i][j].getState() != Cell.State.WALL){

                        Policies.valueIteration(cells[i][j],cells[i-1][j],cells[i+1][j],cells[i][j-1], cells[i][j+1], DISCOUNT);

                        //compare old utility with new utility
                        double diff = cells[i][j].getTempUtility() - cells[i][j].getUtility();
                        if(diff > maxerr){
                            maxerr = diff;
                        }
                    }
                }
            }

            //transfer temp utility value to the main utility value
            for(int i=1; i<7; i++){
                for(int j=1; j<7; j++){
                    if(cells[i][j].getState() != Cell.State.WALL){
                        cells[i][j].setUtility(cells[i][j].getTempUtility());
                    }
                }
            }
            count1++;

            // data collection for plotting
            xData[count1] = count1;
            yData[count1] = cells[3][3].getUtility();
        }

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "number of iterations", "Utilities", "U(number of iterations)", xData, yData);
        //display chart in separate windows
        new SwingWrapper(chart).displayChart();


        //display the resulting grids
        System.out.println("\n\n------VALUE ITERATION----\n");
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                DecimalFormat formatter = new DecimalFormat( "#.00" );
                System.out.print("[" + formatter.format(cells[i][j].getUtility())+"]");
            }
            System.out.println();
        }

        System.out.println("number of value iteration: " + count1);
        System.out.println();

        //display policy in grid
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                cells[i][j].setPolicy(cells[i][j].getTempPolicy());
                System.out.print("[" + cells[i][j].getPolicy()+"]");
            }
            System.out.println();
        }

        //reinitializing for POLICY ITERATION
        System.out.println("\n\n Reinitiliazing for POLICY ITERATION: \n\n");
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++) {
                if (cells[i][j].getState() != Cell.State.WALL) {
                    cells[i][j].setUtility(0);
                    cells[i][j].setTempUtility(0);
                    DecimalFormat formatter = new DecimalFormat("#.00");
                    System.out.print("[" + formatter.format(cells[i][j].getUtility()) + "]");
                    //randomly assign policy
                    if (j % 4 == 0) {
                        cells[i][j].setPolicy(Cell.Policy._UP_);
                    } else if (j % 4 == 1) {
                        cells[i][j].setPolicy(Cell.Policy.DOWN);
                    } else if (j % 4 == 2) {
                        cells[i][j].setPolicy(Cell.Policy.LEFT);
                    } else if (j % 4 == 3) {
                        cells[i][j].setPolicy(Cell.Policy.RIGHT);
                    }
                }
            }
            System.out.println();
        }


        System.out.println();

        //display policy in grid
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                System.out.print("[" + cells[i][j].getPolicy()+"]");
            }
            System.out.println();
        }

        System.out.println();



        //POLICY ITERATION

        boolean unchanged = false; //keep track for any changes
        int count2 = 0;

        //for plotting
        double[] xData2 = new double[8];
        double[] yData2 = new double[8];

        while(unchanged == false){

            double maxerr2 = 0.00002; //epsilon
            int count3 = 0;
            while(maxerr2 > 0.00001) {

                maxerr2 = 0;
                for(int i=1; i<7; i++){
                    for(int j=1; j<7; j++){
                        if(cells[i][j].getState() != Cell.State.WALL) {

                            Policies.policyEvaluation(cells[i][j], cells[i - 1][j], cells[i + 1][j], cells[i][j - 1], cells[i][j + 1], DISCOUNT);

                            double diff = cells[i][j].getTempUtility() - cells[i][j].getUtility();
                            if (diff > maxerr2) {
                                maxerr2 = diff;
                            }
                        }
                    }
                }

                for(int i=1; i<7; i++){
                    for(int j=1; j<7; j++){
                        if(cells[i][j].getState() != Cell.State.WALL){

                            //transfer temp utility value to the main utility value
                            cells[i][j].setUtility(cells[i][j].getTempUtility());

                        }
                    }
                }
                count3++;
            }

            System.out.println("number of fixed-policy evaluation per iteration: " + count3);

            unchanged = true;

            for(int i=1; i<7; i++){
                for(int j=1; j<7; j++){
                    if(cells[i][j].getState() != Cell.State.WALL) {

                        Policies.policyIteration(cells[i][j],cells[i-1][j],cells[i+1][j],cells[i][j-1], cells[i][j+1], DISCOUNT);

                        //compare new policy with old policy
                        if(cells[i][j].getTempPolicy() != cells[i][j].getPolicy()){
                            cells[i][j].setPolicy(cells[i][j].getTempPolicy());
                            unchanged = false;
                        }
                    }
                }
            }
            count2++;
            //points collection for plotting
            xData2[count2] = count2;
            yData2[count2] = cells[3][3].getUtility();
        }

        // Create Chart
        XYChart chart1 = QuickChart.getChart("Sample Chart", "number of iterations", "Utilities", "U(number of iterations)", xData2, yData2);
        //display chart in separate windows
        new SwingWrapper(chart1).displayChart();

        System.out.println();

        //display policy in grid
        for(int i=1; i<7; i++){
            for(int j=1; j<7; j++){
                System.out.print("[" + cells[i][j].getPolicy()+"]");
            }
            System.out.println();
        }

        System.out.println("number of policy iteration: " + count2);

    }
}
