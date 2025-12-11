package backend;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <b>PathFinder</b> provides a Dijkstra shortest-path implementation over a
 * Graph with node identifiers.
 */

public class PathFinder
{
    /**
     * @param: PART1   The starting node id (source) of the path search.
     * @param: PART2   The target node id (destination) of the path search.
     * @param: Graph   The graph on which Dijkstra's algorithm will be run.
     * @param: path    A map that will be populated with path information
     * @param: inverse If false, edge weights are used as-is.
     *                 If true, each edge weight is
     *                 replaced by 1.0/weight when computing distances.
     *
     * @requires  PART1 != null && PART2 != null && Graph != null
     *            && path != null}. Also requires that PART1 is a
     *            node in Graph
     *
     * @modifies  path
     *
     * @effects   Runs Dijkstra's algorithm to find the path from PART1 to PART2 on
     *            the given Graph
     *
     * @returns   A HashMap<String, Double> mapping each node id to
     *            its shortest-path distance from PART1.
     */
    public static HashMap<String,Double> dijkstra(String PART1, String PART2, Graph<String,?> Graph, HashMap<String,Object[]> path,boolean inverse)
    {
        Set<String> allNodes = Graph.getAllNodes();
        HashMap<String,Double> distance = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        PriorityQueue<String> nodeQueue = new PriorityQueue<>(Comparator.comparingDouble((String node) -> distance.get(node)).thenComparing(String::compareTo));
        
        for(String key: allNodes)
        {
            distance.put(key, Double.MAX_VALUE);
        }
        distance.put(PART1, 0.0);
        nodeQueue.add(PART1);
        while (!nodeQueue.isEmpty())
        {
            String currNode = nodeQueue.poll();

            if(!visited.add(currNode))
                continue;

            if(currNode.equals(PART2))
            {
                break;
            }

            for(String child: Graph.getAllChildren(currNode))
            {
                if(!visited.contains(child))
                {
                    Double edge = Graph.getEdge(currNode, child)*1.0;
                    if(inverse)
                        edge = 1.0/edge;
                    Double newDistance = distance.get(currNode) + edge;
                    if(newDistance.compareTo(distance.get(child)) < 0)
                    {
                        distance.put(child, newDistance);
                        nodeQueue.add(child);
                        path.put(child,new Object[]{currNode,edge});
                    }
                }
            }
        }
        return distance;
    }   
    
}
