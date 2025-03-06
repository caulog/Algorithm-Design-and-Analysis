//import java.io.File;
import java.util.Scanner;
import java.lang.Math;

/** 
 * Give an algorithm for computing how many bit strings of length n do not include the substring 111.
 * For example, there are 4 such bit strings of length 2, 7 such bit strings of length 3, and 13 such bit
 * strings of length 4. Justify the correctness of your algorithm and analyze the time complexity.
 */

public class bitstrings{
    public static void main(String[] args) throws Exception{
        int n = 5;
        System.out.println(find_substrings(n));
    }

    static int find_substrings(int n){
        // base case: n cannot have any substrings of 111
        int valid = (int)(Math.pow(2.0, n));
        if(n < 3){ return valid; }

        int substringn = 0, substring1 = 1, substring2 = 2, substring3 = 4;

        for(int i = 3; i <= n; i++){
            substringn = substring1 + substring2 + substring3;
            substring1 = substring2;
            substring2 = substring3;
            substring3 = substringn;
        }

        return substringn;
    }
}