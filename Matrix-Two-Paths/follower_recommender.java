import java.io.File;
import java.util.Scanner;

public class follower_recommender{
    public static void main(String[] args) throws Exception{
        // Small Unit Test
        System.out.println("----Small Unit Test----\nInput:");
        int[][] small_matrix = {{0, 1, 1, 0},{1, 0, 0, 1}, {1, 1, 0, 0}, {0, 0, 0, 0}};
        int[][] small_matrix_result = new int[4][4];
        print_matrix(small_matrix);
        small_matrix_result = multiply_matrix(small_matrix, small_matrix);
        System.out.println("\n(Input Matrix)^2:");
        print_matrix(small_matrix_result);

        // Large Unit Test -- accepts user input for arbitrary dimension
        System.out.println("\n\n----Large Unit Test----");
        Scanner user_scanner = new Scanner(System.in);
        System.out.print("Please provide an integer that is a power of 2 (e.g. 8, 16, 32...): ");
        int test = user_scanner.nextInt();
        user_scanner.close();
        int check = test;
        // make sure input is a power of 2
        while (check != 1) {
            if (check % 2 != 0){
                System.out.println(test + " is not a power of 2.");
                return;
            }
            check = check / 2;
        }
        System.out.println("\n" + test + "x" + test + " adjacency matrix:");
        int[][] large_matrix = new int[test][test];
        for(int i = 0; i < test; i++){
            for(int j = 0; j < test; j++){
                if (i == j){
                    large_matrix[i][j] = 0;
                } else{
                    large_matrix[i][j] = 1;
                }
            }
        }
        int[][] large_matrix_result = new int[2][2];
        print_matrix(large_matrix);
        large_matrix_result = multiply_matrix(large_matrix, large_matrix);
        System.out.println("\n(" + test + "x" + test + " Matrix)^2:");
        print_matrix(large_matrix_result);
        

        // File read matrices test
        if(args.length == 1){
            System.out.println("\n\n----Follower Recommender----");
            // fill the matrix
            File matrix_file = new File (args[0]);
            Scanner file_reader = new Scanner(matrix_file);
            int n = get_size(matrix_file, file_reader);
            file_reader.close();
            file_reader = new Scanner(matrix_file);
            int[][] matrix = get_matrix(n, matrix_file, file_reader);
            file_reader.close();

            //print input matrix:
            System.out.println("Follower Adjacency Matrix:");
            print_matrix(matrix);

            // call matrix multiply
            int[][] follower_recomendations = multiply_matrix(matrix, matrix);

            // print matrix
            System.out.println("\nFollower Recommendation Matrix:");
            print_matrix(follower_recomendations);

            // determine who to recommend
            System.out.println("\nFollower Recommendations:");
            for (int i = 0; i < n; i++){
                int max = follower_recomendations[i][0];
                int index = 0;
                if(max == 0){ index = 30; }
                for (int j = 0; j < n; j++){
                    if(follower_recomendations[i][j] > max && i != j && matrix[i][j] != 1){
                        max = follower_recomendations[i][j];
                        index = j;
                    }
                }
                char user1 = (char)(65+i);
                char user2 = (char)(65+index);
                System.out.println(user1 + " should follow " + user2);
            }

        } else if (args.length == 2){
            System.out.println("\n\n----User Input Test----");
            // fill matrix one
            File matrix_file1 = new File (args[0]);
            Scanner file_reader = new Scanner(matrix_file1);
            int n = get_size(matrix_file1, file_reader);
            file_reader.close();
            file_reader = new Scanner(matrix_file1);
            int[][] matrix1 = get_matrix(n, matrix_file1, file_reader);
            file_reader.close();
            //print input matrix:
            System.out.println("Input matrix 1:");
            print_matrix(matrix1);

            // fill matrix two
            File matrix_file2 = new File (args[1]);
            file_reader = new Scanner(matrix_file2);
            n = get_size(matrix_file2, file_reader);
            file_reader.close();
            file_reader = new Scanner(matrix_file2);
            int[][] matrix2 = get_matrix(n, matrix_file2, file_reader);
            file_reader.close();
            //print input matrix:
            System.out.println("\nInput matrix 2:");
            print_matrix(matrix2);

            // call matrix multiply
            int[][] result = multiply_matrix(matrix1, matrix2);

            // print matrix
            System.out.println("\nMatrix1 * Matrix2:");
            print_matrix(result);
        }else{
            // make sure there is an input file
            System.out.println("\nInvalid command line arguments.\n**Cannot find .txt file for matrix inputs**\n");
            return;
        }

    }
    
    // get matrix size
    public static int get_size(File matrix_file, Scanner file_reader){
        String line = file_reader.nextLine();
        String[] row1 = line.split(" ");
        return row1.length;
    }
    
    // get matrix elements
    public static int[][] get_matrix(int n, File matrix_file, Scanner file_reader){
        int[][] matrix = new int[n][n];
        // fill the matrix array
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrix[i][j] = file_reader.nextInt();
            }
        }
        file_reader.close();
        return matrix;
    }

    // print matrix
    public static void print_matrix(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // classic divide-and-conquer implementation
    public static int[][] multiply_matrix(int[][] A, int[][] B) {
        // get dimension and initialize result matrix
        int n = A.length;
        int[][] result = new int[n][n];
    
        // base case when dimension is 1
        // return the product
        if (n == 1) {
            result[0][0] = A[0][0] * B[0][0];
            return result;
        }
    
        // else: recursive multiplication
        // initializes quadrants
        int split = n / 2;
        int[][] A11 = new int[split][split];
        int[][] A12 = new int[split][split];
        int[][] A21 = new int[split][split];
        int[][] A22 = new int[split][split];
        int[][] B11 = new int[split][split];
        int[][] B12 = new int[split][split];
        int[][] B21 = new int[split][split];
        int[][] B22 = new int[split][split];
        // result quadrants
        int[][] C11 = new int[split][split];
        int[][] C12 = new int[split][split];
        int[][] C21 = new int[split][split];
        int[][] C22 = new int[split][split];
    
        // divide matrices into quadrants 
        for (int i = 0; i < split; i++) {
            for (int j = 0; j < split; j++) {
                // matrix A
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + split];
                A21[i][j] = A[i + split][j];
                A22[i][j] = A[i + split][j + split];
                // matrix B
                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + split];
                B21[i][j] = B[i + split][j];
                B22[i][j] = B[i + split][j + split];
            }
        }
    
        // recursive matrix multiplication
        C11 = add_matrix(multiply_matrix(A11, B11), multiply_matrix(A12, B21), split);
        C12 = add_matrix(multiply_matrix(A11, B12), multiply_matrix(A12, B22), split);
        C21 = add_matrix(multiply_matrix(A21, B11), multiply_matrix(A22, B21), split);
        C22 = add_matrix(multiply_matrix(A21, B12), multiply_matrix(A22, B22), split);
    
        // form the result matrix
        for (int i = 0; i < split; i++) {
            for (int j = 0; j < split; j++) {
                result[i][j] = C11[i][j];
                result[i][j + split] = C12[i][j];
                result[i + split][j] = C21[i][j];
                result[i + split][j + split] = C22[i][j];
            }
        }
        return result;
    }
    
    // completes matrix addition
    private static int[][] add_matrix(int[][] A, int[][] B, int n) {
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }
}