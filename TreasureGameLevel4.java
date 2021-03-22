import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class QItem {
    int row;
    int col;
    int dist;
    public QItem(int row, int col, int dist){
        this.row = row;
        this.col = col;
        this.dist = dist;
    }
}

public class TreasureGameLevel4 {

    static char[][] grid;
    static Scanner scanner = new Scanner(System.in);

    private static int minDistance(char src){
        QItem source = new QItem(0, 0, 0);
        firstLoop:
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == src) {
                    source.row = i;
                    source.col = j;
                    break firstLoop;
                }
            }
        }
        
        Queue<QItem> queue = new LinkedList<>();
        queue.add(new QItem(source.row, source.col, 0));

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[source.row][source.col] = true;

        while (queue.isEmpty() == false) {
            QItem p = queue.remove();
            
   
            if (grid[p.row][p.col] == 'T')
                return p.dist;

            if (isValid(p.row - 1, p.col, grid, visited, src)) {
                queue.add(new QItem(p.row - 1, p.col,
                                    p.dist + 1));
                visited[p.row - 1][p.col] = true;
            }

        
            if (isValid(p.row + 1, p.col, grid, visited, src)) {
                queue.add(new QItem(p.row + 1, p.col,
                                    p.dist + 1));
                visited[p.row + 1][p.col] = true;
            }

         
            if (isValid(p.row, p.col - 1, grid, visited, src)) {
                queue.add(new QItem(p.row, p.col - 1,
                                    p.dist + 1));
                visited[p.row][p.col - 1] = true;
            }

          
            if (isValid(p.row - 1, p.col + 1, grid,
                        visited, src)) {
                queue.add(new QItem(p.row, p.col + 1,
                                    p.dist + 1));
                visited[p.row][p.col + 1] = true;
            }
        }
        return -1;
    }

   
    private static boolean isValid(int x, int y, char[][] grid, boolean[][] visited, char src){
        if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && (grid[x][y] != 'P' || src == 'D') && visited[x][y] == false) {
            return true;
        }
        return false;
    }

    static void getInput(){
        System.out.print("Enter maze size: ");
        int noofcol = scanner.nextInt(), noofrow = scanner.nextInt();
        grid = new char[noofrow][noofcol];
        
        System.out.print("position of miner: ");
        int posOfMinerX = scanner.nextInt(), posOfMinerY = scanner.nextInt();
        if(posOfMinerX > noofcol || posOfMinerY > noofrow){
            System.out.println("out of position");
            System.exit(0);
        }

        System.out.print("position of treasure: ");
        int posOfTreasureX = scanner.nextInt(), posOfTreasureY = scanner.nextInt();
        if(posOfTreasureX > noofcol || posOfTreasureY > noofrow){
            System.out.println("out of position");
            System.exit(0);
        }

        System.out.print("position of dragon: ");
        int posOfDragonX = scanner.nextInt(), posOfDragonY = scanner.nextInt();
        if(posOfDragonX > noofcol || posOfDragonY > noofrow){
            System.out.println("out of position");
            System.exit(0);
        }

        if(posOfMinerX == posOfTreasureX && posOfMinerY == posOfTreasureY){
            System.out.println("same position miner and treasure");
            System.exit(0);
        }

        if(posOfMinerX == posOfDragonX && posOfMinerY == posOfDragonY){
            System.out.println("same position miner and dragon, miner will die");
            System.exit(0);
        }

        if(posOfDragonX == posOfTreasureX && posOfDragonY == posOfTreasureY){
            System.out.println("same position dragon and treasure, dragon reaches first with 0 steps");
            System.exit(0);
        }

      
        for(int i = 0; i < noofrow; i++){
            for(int j = 0; j < noofcol; j++){
                grid[i][j] = '*';
            }
        }
        grid[posOfMinerY - 1][posOfMinerX - 1] = 'M';
        grid[posOfTreasureY - 1][posOfTreasureX - 1] = 'T';
        grid[posOfDragonY - 1][posOfDragonX - 1] = 'D';
        System.out.print("No of Pit Holes: ");
        int noOfPit = scanner.nextInt();
        for(int i = 0; i < noOfPit; i++){
            System.out.print((i + 1) + " Pit Hole: ");
            int pitX = scanner.nextInt(), pitY = scanner.nextInt();
            if(posOfMinerX == pitX && posOfMinerY == pitY){
                System.out.println("same position miner and pit" + (i + 1));
                System.exit(0);
            }
            if(posOfTreasureX == pitX && posOfTreasureY == pitY){
                System.out.println("same position treasure and pit" + (i + 1));
                System.exit(0);
            }
            grid[pitY - 1][pitX - 1] = 'P';
        }
        printMaze();
    }

    static void printMaze(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        getInput();
        int minerStep = minDistance('M'), dragonStep = minDistance('D');
        if(dragonStep > minerStep){
            if(minerStep > 1){
                System.out.println("Miner wins by taking the shortest route of " + minerStep + " steps");
            }else{
                System.out.println("No possible way for the miner to reach the treasure");
            }
        }else{
            System.out.println("Dragon reaches first and the minner dies");
        }
    }
}