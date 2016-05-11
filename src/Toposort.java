import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Toposort {
    private static int n; //nodes
    private static int root;
    private static int[] marked; //Used for DFS
    private static ArrayList<ArrayList<Integer>> nodes; //TODO: make arraylist<hashset<integer>>, dont use a linkedhashset
    private static boolean verbose = false;

    public static void main(String[] args){
        //readInput("Test.txt");
        if (args.length == 0) {
            System.out.println("Use as 'java Toposort edges.txt [--verbose]'");
            System.out.println("Outputs \n #nodes \n #edges \n DFS nseconds \n Kahn nseconds ");
            System.exit(1);
        }
        if (args.length > 1)
            verbose = (args[1].equals("--verbose"));
        readInput(args[0]);

        System.out.println(n); //Print the number of nodes first
        int edges=0;
        for (ArrayList<Integer> node : nodes)
            edges+=node.size();
        System.out.println(edges); //Print number of edges

        if (verbose)
            System.out.println("DFS");
        long start = System.nanoTime();
        ArrayList<Integer> depthFirstSearch = depthFirst();
        long end = System.nanoTime();
        System.out.println(end-start); //print time consumed in nanoseconds
        if (verbose) {
            for (int i = n - 1; i >= 0; i--) {  // list is in reverse sorted order
                System.out.println(depthFirstSearch.get(i));  // print out list
            }
        }

        if (verbose)
            System.out.println("Kahn");
        start = System.nanoTime();
        ArrayList<Integer> sorted = kahn();
        end = System.nanoTime();
        System.out.println(end-start); //print time consumed in nanoseconds
        if (verbose)
            sorted.forEach(System.out::println);
    }

    public static ArrayList<Integer> kahn() {
        ArrayList<Integer> L = new ArrayList<>();
        HashSet<Integer> S = new HashSet<>();

        //Gather set S
        // TODO: stream api distinct() collect() filter()
        HashSet<Integer> temp = new HashSet<>(); //gather opposite set
        for (ArrayList<Integer> i : nodes) {
            temp.addAll(i);
        }
        for (int i = 0; i < n; i++) { //Set subtract to nodes w/o incoming edges
            if (!temp.contains(i))
                S.add(i);
        }

        //Main Loop
        while (!S.isEmpty()) { //While S is non-empty
            Iterator<Integer> STempIter = S.iterator();
            Integer n = STempIter.next();
            STempIter.remove(); //remove a node n from S
            L.add(n); //add n to tail of L

            Iterator<Integer> m_i = nodes.get(n).iterator(); //for each m with edge from n->m
            while (m_i.hasNext()) {
                Integer m = m_i.next();
                m_i.remove(); //remove edge to m from graph
                if (!hasIncomingEdges(m)) //if no other edges ->m
                    S.add(m); //add m to S
            }
        }
        return L;
    }

    private static boolean hasIncomingEdges(Integer node) {
        //TODO: refactor with hashes
        //TODO: streams
        for (ArrayList<Integer> n : nodes) {
            if (n.contains(node))
                return true;
        }
        return false;
    }


    // Reads information about a graph from a file and puts it into global variables
    // Takes file name as parameter
    public static void readInput(String f) {
        try {
            Scanner inp = new Scanner(new File(f));
            n = Integer.parseInt(inp.nextLine()); //number of nodes
            root = Integer.parseInt(inp.nextLine()); //root node

            nodes = new ArrayList<>(); //Setup nodes data structure
            for (int i = 0; i < n; i++)
                nodes.add(new ArrayList<>());

            String line; //Read in edges from file
            while (inp.hasNextLine()) {
                line = inp.nextLine();
                String[] edge = line.split(" ");
                int sourceNode = Integer.parseInt(edge[0]);
                int destinationNode = Integer.parseInt(edge[1]);
                nodes.get(sourceNode).add(destinationNode);
            }
        } catch (FileNotFoundException e) {
            System.out.println( "file not found");
        }
    }

    // Recursively visits nodes for DFS
    // When done visiting a node, appends it to list of sorted nodes
    // Takes index of node to visit and current list of sorted nodes as parameters
    // Outputs current list of sorted nodes
    private static ArrayList<Integer> visit(int i, ArrayList<Integer> ret){
        // if (verbose)
            // System.out.println("Called visit at index " + i);  // debug
        if (marked[i] == 2){  // if node is gray/temporarily marked
            System.out.println("Cycle detected.");
        } else if (marked[i] == 0){  // if node is white
            marked[i] = 2;  // make it gray/temporary mark
            for (int j = 0; j < nodes.get(i).size(); j++){  // visit adacent nodes
                ret = visit(nodes.get(i).get(j), ret);
            }
            marked[i] = 1;  // make node black/permanently marked
            ret.add(i);  // add to list
        }
        return ret;
    }

    // Uses depth-first search to sort nodes
    // Outputs solution
    public static ArrayList<Integer> depthFirst(){
        ArrayList<Integer> ret = new ArrayList<>(); // list of sorted nodes
        marked = new int[n];  // keeps track of whether nodes are unmarked (0), marked (1), or temporarily marked (2)
        for (int i = 0; i < n; i++){  // initialize all to unmarked
            marked[i] = 0;
        }
        for (int i = 0; i < n; i++){  // go through all unmarked nodes
            if (marked[i] == 0){
                ret = visit(i, ret);  // start recursive visit
            }
        }
        return ret;
    }
}
