#Topological Sort
##Final project for CS375

Topological sort is used to order a directed graph in such a way that for all edges from _a_ to _b_, _b_ comes after _a_

Compile: javac Toposort.java
Run: java Toposort filename (--verbose)

Data Structures:
    DFS-Based Toposort:
        Puts nodes into an Arraylist as they are sorted
        Stores 'color' of each node as an array of size n
    Kahn's Algorithm:
        Maintain a HashSet of nodes without incoming edges
        Uses set iterators and ArrayLists for final sort

Analysis:
    DFS-Based:
        Time for DFS is O(|V| + |E|)
        Θ(1) to add vertices to list
        O(|V| + |E|) + Θ(1) simplifies to O(|V| + |E|)
    Kahn's:
        O(|V| + |E|)
        |E| to calculate incoming edges for all V
        |V| to then traverse vertices decrementing incoming edges



