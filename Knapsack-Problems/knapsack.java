/**
 * In the 0/1 Knapsack problem, you are given a knapsack of capacity W and n items with weights {wi}
    and values {vi}. The goal is to identify a subset of the items S with maximal value 
    subject to the constraint that the weights cannot outweigh W. 
    Each item may be included at most once (each is either included or not included in S).
    (a) Implement a dynamic programming solution to this problem that uses O(nW) space and runs in
        O(nW) time. In this problem, you do not have to compute the set S, only its value.
    (b) Implement a more memory-efficient dynamic programming solution to this problem that uses
        O(W) space and runs in O(nW) time. Again, you do not have to compute the set S, only its
        value.
    (c) Test your algorithm using input files small.txt, medium.txt, and large.txt. The correct values
        are 19, 632, and 55,636, respectively.
        • You will need to implement code to read the above input files.
        • The first line is the weight capacity of the knapsack formatted as (capital “W”): W 15
        • The second line is the total number of items formatted as: n 5
        • The next n lines are the weights and values of each item. Each line is formatted as: w 2 v 4
 * references: https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
*/

import java.io.File;
import java.util.Scanner;

public class knapsack{
    public static void main(String[] args) throws Exception{
        if(args.length == 1){
            
            // open file and create scanner
            File objects_file = new File (args[0]);
            Scanner file_reader = new Scanner(objects_file);

            // get max weight and number of items
            String get_weight = file_reader.nextLine();
            int W = Integer.parseInt(get_weight.substring(2));
            String get_n = file_reader.nextLine();
            int n = Integer.parseInt(get_n.substring(2));

            // create and fill a weight array and a value array
            int[] weight = new int[n];
            int[] value = new int[n];
            for (int i = 0; i < n; i++){
                String[] item = (file_reader.nextLine()).split(" ");
                weight[i] = Integer.parseInt(item[1]);
                value[i] = Integer.parseInt(item[3]);
            }

            System.out.println("----Knapsack Problem O(nW) Solution----");
            System.out.println(fill_knapsack_2Darr(W, weight, value));
            System.out.println("----Knapsack Problem O(W) Solution----");
            System.out.println(fill_knapsack_1Darr(W, weight, value));

            file_reader.close();
        }else{
            // make sure there is an input file
            System.out.println("\nInvalid command line arguments.\n**Cannot find .txt file for input**\n");
        }
        return;
    }

    // From notes:
    // K(w, j) = max{K(w, j - 1), K(w-wj, j-1) + vj}
    // --> K(w, 0) = K(0, j) = 0 and K(w, j) = K(w, j - 1) if wj > w
    static int fill_knapsack_2Darr(int W, int[] weight, int[] value){
        int n = value.length;
        int[][] K = new int[W+1][n+1];  // O(nW) space array

        for(int j = 0; j <= n; j++){    // O(nW) time for nested loops
            for(int w = 0; w <= W; w++){
                if(j == 0 || w == 0){
                    K[w][j] = 0;
                } else if (weight[j-1] > w){
                    K[w][j] = K[w][j-1];
                } else{
                    int a = K[w][j-1];
                    int b = K[w-weight[j-1]][j-1] + value[j-1];
                    K[w][j] = get_max(a, b);
                }
            }
        }
        return K[W][n];
    }

    // Bottom up approach
    static int fill_knapsack_1Darr(int W, int[] weight, int[] value){
        int n = value.length;
        int[] dp = new int[W+1];    // O(W) space array

        for(int j = 0; j < n; j++){ // O(nW) time for nested loops
            for(int w = W; w >= 0; w--){
                if(weight[j] <= w){
                    int a = dp[w];
                    int b = dp[w - weight[j]] + value[j];
                    dp[w] = get_max(a, b);
                }
            }
        }
        return dp[W];
    }

    // gets the maximum of two integers
    static int get_max(int a, int b){
        if(a > b){ return a; }
        return b;
    }
}