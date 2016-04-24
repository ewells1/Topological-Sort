import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Toposort {
    static int n;
    static int root;
    static ArrayList<ArrayList<Integer>> nodes; //TODO: make arraylist<hashset<integer>>

    public static void main(String[] args){
        readInput("Test.txt");
        ArrayList<Integer> sorted = kahn();
        for (int i : sorted)
            System.out.println(i);
    }

    public static ArrayList<Integer> kahn() {
        ArrayList<Integer> L = new ArrayList<>();
        LinkedHashSet<Integer> SHash = new LinkedHashSet<>();

        //Gather set SHash
        HashSet<Integer> temp = new HashSet<>(); //gather opposite set
        for (ArrayList<Integer> i : nodes) {
            temp.addAll(i);
        }
        for (int i = 0; i < n; i++) { //Set subtract to nodes w/o incoming edges
            if (!temp.contains(i))
                SHash.add(i);
        }

        //Main Loop
        Iterator<Integer> S = SHash.iterator();
        while (SHash.size() != 0) { //While S is non-empty
            while (S.hasNext()) { //remove a node n from S
                Integer n = S.next();
                L.add(n); //add n to tail of L

                //ArrayList<Integer> m_i = new ArrayList<>( nodes.get(n) ); //Deep copy edges for n
                Iterator<Integer> m_i = nodes.get(n).iterator(); //edges for n
                //for (Integer m : m_i) { //for each m with edge from n->m
                while (m_i.hasNext()) {
                    Integer m = m_i.next();
                    m_i.remove(); //remove edge from graph //TODO ensure that removing Object not index
                    if (!hasIncomingEdges(m)) //if no other edges ->m
                        SHash.add(m); //add m to S
                }
            }
        }

        return L;
    }

    private static boolean hasIncomingEdges(Integer node) {
        //TODO: refactor with hashes
        for (ArrayList<Integer> n : nodes) {
            if (n.contains(node))
                return true;
        }
        return false;
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
