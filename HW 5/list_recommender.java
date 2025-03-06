// referenced: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/

import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashMap;

public class list_recommender{
    public static void main(String[] args) throws Exception{
        if(args.length == 1){
            System.out.println("----List Recommender----");
            // open file and create scanner
            File list_file = new File (args[0]);
            Scanner file_reader = new Scanner(list_file);

            // get the nodes (size of array) and edges 
            String line = file_reader.nextLine();
            String[] intro = line.split(" ");          
            int nodes = Integer.parseInt(intro[3]);

            // initialize the array of linked lists and hashmaps
            @SuppressWarnings({"unchecked"})
            LinkedList<Integer>[] input_list = new LinkedList[nodes];
            @SuppressWarnings({"unchecked"})
            HashMap<Integer, Integer>[] output_list = new HashMap[nodes];
            for(int i = 0; i < nodes; i++){
                input_list[i] = new LinkedList<>();
                output_list[i] = new HashMap<Integer, Integer>();
            }

            // fill array of linked lists with file data
            while(file_reader.hasNext()){
                int curr = file_reader.nextInt();
                int element = file_reader.nextInt();
                input_list[curr-1].add(element);
            }
            
            // print input list
            System.out.println("Follower Input List:");
            print_list(input_list);

            // call BFS for each node (O(n))
            long start = System.nanoTime();
            for (int i = 1; i <= input_list.length; i++){
                BFS(input_list, output_list, i);
            }
            long stop = System.nanoTime();

            // print output map
            System.out.println("\nFollower Output Map:");
            print_map(output_list);

            // get follower reccomendatons
            System.out.println("\nFollower Recommendations:");
            get_recomendations(output_list, input_list);

            // print time
            System.out.println("\nTime: " + (stop-start));
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
    public static void get_recomendations(HashMap<Integer, Integer>[] list_array, LinkedList<Integer>[] input){
        for (int i = 0; i < list_array.length; i++) {
            // initialize map and variables
            System.out.print((i+1) + " should follow ");
            Integer minKey = 0;
            Integer maxValue = 0;
            HashMap<Integer, Integer> map = list_array[i];

            // remove self and if already following
            map.remove(i+1);
            for (Integer element : input[i]) { map.remove(element); }

            // determine best recommendation
            for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                if (value > maxValue || (value >= maxValue && key < minKey && key != 0)){
                    // add statement to check if already follows
                    minKey = key;
                    maxValue = value;
                }
            }
            System.out.print(minKey);
            System.out.println();
        }
    }

    // BFS for depth 2
    public static void BFS(LinkedList<Integer>[] input_list, HashMap<Integer, Integer>[] output_list, int start) {
        // initialize queue
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        queue.add(start-1);
        int depth = 0;
        int curr_nodes = 1;
        int next_nodes = 0;

        // go through neighbors until depth > 2 (O(d*d))
        while (!queue.isEmpty() && depth <= 2) {
            int current = queue.poll();

            // if at depth 2, add entry to output_list hashmap 
            if(depth == 2){
                if(output_list[start-1].get(current+1) == null){ output_list[start-1].put(current+1, 1);
                }else{ output_list[start-1].put(current+1, (output_list[start-1].get(current+1)+1)); }
            }
            curr_nodes--;

            // add each neighbor of current node to queue
            for (int neighbor : input_list[current]) {
                neighbor = neighbor - 1;
                queue.add(neighbor);
                next_nodes++;
            }

            // update depth in BFS tree
            if (curr_nodes == 0){
                curr_nodes = next_nodes;
                next_nodes = 0;
                depth++;
            }
        }
    } 
}