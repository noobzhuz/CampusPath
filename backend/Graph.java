package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.stream.Collectors;


/** <b>Graph</b> represents an <b>mutable</b> graph by using adjacency matrix.
    It includes all the nodes and edges in a graph. Parent and children nodes
    are used as index for locating edges.
    Multiple identical edges are allowed
    matrix:
    [node1:[node1:[e1,e2],node2:[e3]:,node3:[e4,e5,e6]   
     node2:[node1:[e1,e2],node2:[e3]:,node3:[e4,e5,e6]
     node3:[node1:[e1,e2],node2:[e3]:,node3:[e4,e5,e6]]
    @param <T1> Node name
    @param <T2> Edge label
 */



public class Graph<T1,T2> {
    HashMap< T1, HashMap<T1, Double> > matrix;

    
    /** @effects Constructs a new empty graph.
    */
    public Graph()
    {
        matrix = new HashMap<>();
        //checkRep();
    }

    /** @param n the instance to be identified
        @return a boolean variable, ture if Node n is in the Graph
        otherwise return false.
    */
    public boolean containsNode(T1 n)
    {
        return matrix.containsKey(n);
    }


    /** @param newNode the instance to be added into the graph
        @effects add a new node to the graph, return false if
        the node is already exist in the graph
        @modifies matrix
        @throws IllegalArgumentException if newNode is null
        @return a boolean variable, false if Node n is in the Graph
        otherwise return false.
    */

    public boolean addNodes(Set<T1> nodes)
    {
        if(nodes == null)
            throw new IllegalArgumentException("Node name should not be null");

        for(T1 parent: nodes)
        {
            matrix.put(parent,new HashMap<>());
        }
        //checkRep();
        return true;
    }

    /**
     * @param: node  The node to be added to this graph.
     *
     * @requires  node != null
     * @effects   If node is not already in the graph, adds node
     *            as a new node with no outgoing edges and returns true.
     *            If @code is already present, does not modify the graph
     *            and returns false.
     * @throws    IllegalArgumentException if node is null.
     * @returns   true if the node was newly added; false
     *            if it was already present.
     */
    public boolean addNode(T1 node)
    {
        if(node == null)
        {
            throw new IllegalArgumentException("Node name should not be null");
        }
        if(matrix.containsKey(node))
            return false;
        matrix.put(node,new HashMap<>());
        return true;
    }

    /** @param parent the parent node of the new Edge
        @param child the child node of the new Edge
        @param label the label of the new Edge
        @effects add a new edge to the graph, return false if
        the children or the parent node of the edge doesn't exist
        @modifies matrix
        @return a boolean variable, false if the children
        or the parent node of the edge doesn't exist
    */
    public <T3> boolean addEdge(T1 parent, T1 child, T3 label)
    {
        if(!matrix.containsKey(parent) || !matrix.containsKey(child))
            return false;
        HashMap<T1,Double> childs = matrix.get(parent);
        if(!childs.containsKey(child))
            childs.put(child,1.0);
        else
            childs.put(child, childs.get(child) + 1 );
        //checkRep();
        return true;
    }


    /**
     * @param: parent  The parent node of the new edge.
     * @param: child   The child node of the new edge.
     * @param: label   The label associated with this edge
     *
     * @requires  parent != null && child != null
     * @effects   Set label for edge between parent and child
     * @returns   true if the edge count was updated; false if
     *            either endpoint does not exist in the graph.
     */
    public boolean setEdge(T1 parent, T1 child, Double label)
    {
        if(!matrix.containsKey(parent) || !matrix.containsKey(child))
            return false;
        HashMap<T1,Double> childs = matrix.get(parent);
        childs.put(child, label);
        //checkRep();
        return true;
    }



    /** Returns an ArrayList which contains all the node in the graph
        @return a ArrayList<Node> contains all the node.
    */
    public Set<T1> getAllNodes()
    {
        return matrix.keySet();
    }

    public Double getEdge(T1 parent, T1 child)
    {
        if(parent == null || child == null)
            throw new IllegalArgumentException("parent and child should not be null");
        return matrix.get(parent).get(child);
    }

    /**
     * @param: parentNode  The node whose outgoing neighbors are to be returned.
     *
     * @requires  {@code parentNode != null} and {@code parentNode} is a node
     *            in this graph.
     * @modifies  None
     * @effects   Collects and returns all child nodes {@code c} such that there
     *            is an edge {@code parentNode -> c} whose associated weight
     *            is strictly greater than 0.
     * @returns   A {@code Set<T1>} containing all children of {@code parentNode}
     *            with positive edge weights.
     * @throws    IllegalArgumentException if {@code parentNode} is {@code null}.
     * @throws    RuntimeException if {@code parentNode} is not a node in this graph.
     */
    public Set<T1> getAllChildren(T1 parentNode)
    {
        Set<T1> allChildren = new HashSet<>();
        if(parentNode == null)
            throw new IllegalArgumentException("Node should not be null");

        if(!this.containsNode(parentNode))
            throw new RuntimeException(parentNode.toString()+" does'n exist");

        for(Map.Entry<T1, Double> entry: matrix.get(parentNode).entrySet())
        {
            if(entry.getValue() > 0)
                allChildren.add(entry.getKey());
        }
        return allChildren;
    }


}
