// referenced: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/

import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashMap;

public class list_recommender{
    public static void main(String[] args) throws Exception{
        // File read matrices test
        if(args.length == 1){
            System.out.println("----List Recommender----");
            // open file and create scanner
            File list_file = new File (args[0]);
            Scanner file_reader = new Scanner(list_file);

            // get the nodes (size of array) and edges 
            String line = file_reader.nextLine();
            String[] intro = line.split(" ");          
            int edges = Integer.parseInt(intro[1]);
            int nodes = Integer.parseInt(intro[3]);
            System.out.println("edges: " + edges + " nodes:" + nodes);

            // fill the array of linked lists
            @SuppressWarnings({"unchecked"})
            LinkedList<Integer>[] input_list = new LinkedList[nodes];
            @SuppressWarnings({"unchecked"})
            HashMap<Integer, Integer>[] output_list = new HashMap[nodes];


            // initialize array with linked list
            for(int i = 0; i < nodes; i++){
                input_list[i] = new LinkedList<>();
                output_list[i] = new HashMap<Integer, Integer>();
            }

            // fill array of linked lists
            while(file_reader.hasNext()){
                int curr = file_reader.nextInt();
                int element = file_reader.nextInt();
                input_list[curr-1].add(element);
            }
            
            // print iput list
            print_list(input_list);

            for (int i = 1; i <= input_list.length; i++){
                BFS(input_list, output_list, i);
            }

            // get followers
            get_reccomendations(output_list);

            System.out.println("");
            print_map(output_list);

        }else{
            // make sure there is an input file
            System.out.println("\nInvalid command line arguments.\n**Cannot find .txt file for input**\n");
            return;
        }
    }

    // print list
    public static void print_list(LinkedList<Integer>[] list_array){
        for (int i = 0; i < list_array.length; i++) {
            System.out.print("LinkedList " + (i+1) + ": ");
            for (Integer element : list_array[i]) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    // print map
    public static void print_map(HashMap<Integer, Integer>[] list_array){
        for (int i = 0; i < list_array.length; i++) {
            System.out.print("Map " + (i+1) + ": ");

            HashMap<Integer, Integer> map = list_array[i];
            for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
                System.out.print("(" + entry.getKey() + ", " + entry.getValue() + ")");
            }
            System.out.println();
        }
    }

    // get who to follow
    public static void get_reccomendations(HashMap<Integer, Integer>[] list_array){
        for (int i = 0; i < list_array.length; i++) {
            System.out.print((i+1) + ": ");
            Integer minKey = null;
            Integer maxValue = 0;

            HashMap<Integer, Integer> map = list_array[i];
            for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                if (value > maxValue || (value >= maxValue && key < minKey && key != 0)){
                    // add statement to check if already follows
                    minKey = key;
                    maxValue = value;
                }
            }
            System.out.print("(" + minKey + ", " + maxValue + ")");
            System.out.println();
        }
    }

    public static void BFS(LinkedList<Integer>[] input_list, HashMap<Integer, Integer>[] output_list, int start) {
        //System.out.println("\nBFS TREE " + start);
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        queue.add(start-1);
        int depth = 0;
        int curr_nodes = 1;
        int next_nodes = 0;

        while (!queue.isEmpty() && depth <= 2) {
            int current = queue.poll();

            //  && (current+1 != start)
            if(depth == 2){
                //System.out.print((current+1) + " ");
                if(output_list[start-1].get(current+1) == null){
                    output_list[start-1].put(current+1, 1);
                }else{
                    output_list[start-1].put(current+1, (output_list[start-1].get(current+1)+1));
                }
            }
            curr_nodes--;

            for (int neighbor : input_list[current]) {
                neighbor = neighbor - 1;
                queue.add(neighbor);
                next_nodes++;
            }

            if (curr_nodes == 0){
                curr_nodes = next_nodes;
                next_nodes = 0;
                depth++;
            }
        }
    } 
}