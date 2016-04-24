import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Toposort {
    static int n;
    static int root;
    static ArrayList<ArrayList<Integer>> nodes;

    public static void main(String[] args){
        readInput("test.txt");
    }

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
}
