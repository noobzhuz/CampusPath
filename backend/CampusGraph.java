package backend;

import java.util.*;
import java.io.*;

public class CampusGraph {
    Graph<String,String> graph;
    HashMap<String,Coordinate> id_cordinate;
    HashMap<String,String> id_name;
    HashMap<String,String> name_id;


    public CampusGraph(){}


    /**
     * @param: building1  A building id or building name.
     * @param: building2  Another building id or building name.
     * @requires  CampusGraph have been populated
     * @returns   true if building1 and building2 refer to the same building
     *            false otherwise
     */
    private boolean sameBuilding(String building1, String building2)
    {
        if(building1.equals(building2))
            return true;
        if(id_name.containsKey(building1))
            return id_name.get(building1).equals(building2);
        if(id_name.containsKey(building2))
            return id_name.get(building2).equals(building1);
        return false;
    }

    /**
     * @param: nodeFilename  Path to the node data file (buildings/intersections).
     * @param: edgeFilename  Path to the edge data file (paths between nodes).
     *
     * @requires  nodeFilename != null && edgeFilename != null.
     *            Both files must exist and follow the expected input format.
     * @effects   Initializes this CampusGraph by reading all node 
     *            and edges for the campus map
     * @throws    RuntimeException if either file cannot be read or has an
     *            invalid format.
     */
    public void createGraph(String nodeFilename,String edgeFilename)
    {
        graph = new Graph<>();
        id_cordinate = new HashMap<>();
        id_name = new HashMap<>();
        name_id = new HashMap<>();
        LinkedList<Map.Entry<String,String>> idPairs = new LinkedList<>();
        // read node data
        try{
            CampusParser.readNodeData(nodeFilename, id_name, name_id ,id_cordinate);
        }catch(IOException e){
            throw new RuntimeException("file with a wrong format or a wrong directory");
        }
        // read edge data
        try{
            CampusParser.readEdgeData(edgeFilename, idPairs);
        }catch(IOException e){
            throw new RuntimeException("file with a wrong format or a wrong directory");
        }
        
        graph.addNodes(id_name.keySet());
        for(Map.Entry<String,String> entry: idPairs)
        {
            String parentId = entry.getKey();
            String childId = entry.getValue();
            Double distance = id_cordinate.get(parentId).distance(id_cordinate.get(childId));
            graph.setEdge(parentId, childId, distance);
            graph.setEdge(childId, parentId, distance);
        }
    }

    /**
     * @param: parent  The starting coordinate of the edge.
     * @param: child   The ending coordinate of the edge.
     * @requires  parent != null && child != null
     * @returns   A string describing the compass direction of travel from
     *            parent to child
     */
    private String walkDir(Coordinate parent, Coordinate child)
    {
        double angle = Coordinate.angle(parent, child);
        String dir;
        if (angle >= -22.5 && angle < 22.5) {
            dir = "East";
        } 
        else if (angle >= 22.5 && angle < 67.5) {
            dir = "SouthEast";
        } 
        else if (angle >= 67.5 && angle < 112.5) {
            dir = "South";
        }
        else if (angle >= 112.5 && angle < 157.5) {
            dir = "SouthWest";
        }
        else if (angle >= 157.5 || angle < -157.5) {
            dir = "West";
        }
        else if (angle >= -157.5 && angle < -112.5) {
            dir = "NorthWest";
        }
        else if (angle >= -112.5 && angle < -67.5) {
            dir = "North";
        }
        else{
            dir = "NorthEast";
        }
        return dir;
    }

    /**
     * @requires  name_id has been populated
     * @returns   An Iterator<String> over all buildings in the campus.
     *            Each element has the format "name,id" and the
     *            sequence is sorted lexicographically by building name.
     */
    public Iterator<String> listAllBuildings()
    {
        ArrayList<String> listBuildings = new ArrayList<>();
        for(String name: name_id.keySet())
            listBuildings.add(name+","+name_id.get(name));
        Collections.sort(listBuildings);
        return listBuildings.iterator();
    }

    

    /** @effects Find the shortest Path from PART1 to PART2
     *  @throws RuntimeException if the file path doesn't exist or the file has a wrong format
     *  @param PART1 the start point of the path(building id/name)
     *  @param PART2 the end point of the path(building id/name)
     *  @return a String that is a formatted out put of the
    */
    public String findPath(String PART1, String PART2)
    {
        if(name_id.containsKey(PART1))
            PART1 = name_id.get(PART1);
        if(name_id.containsKey(PART2))
            PART2 = name_id.get(PART2);
        String building1 = id_name.get(PART1);
        String building2 = id_name.get(PART2);
        String unknown = new String();
        boolean notFound = false;
        if(building1 == null || building1.equals(new String()))
        {
            unknown += "Unknown building: [" + PART1 + "]\n";
            notFound = true;
        }

        if(!sameBuilding(PART2, PART1) && (building2 == null || building2.equals(new String())))
        {
            unknown += "Unknown building: [" + PART2 + "]\n";
            notFound = true;
        }

        if(notFound)
            return unknown;
        
        HashMap<String,Object[]> path = new HashMap<>();
        HashMap<String,Double> distance = PathFinder.dijkstra(PART1, PART2, graph, path, false);
        String resultString = new String();
        LinkedList<String> result = new LinkedList<>();
        if(distance.get(PART2).equals(Double.MAX_VALUE))
            return resultString + "There is no path from "+ building1 +" to "+ building2 +".\n";
        else
        {
            resultString = "Path from "+ building1 +" to "+ building2 +":\n";
            String curr = PART2;
            String child;
            while(!curr.equals(PART1))
            {
                //PART1 to PART2 with weight w1
                child = curr;
                Object[] get = path.get(curr).clone();
                curr = (String)get[0];

                Coordinate parentCoor = id_cordinate.get(curr);
                Coordinate childCoor = id_cordinate.get(child);
                String dir = walkDir(parentCoor, childCoor);

                result.add("\tWalk "+ dir + " to (" + childCoor.printName() +")\n");
            }
        }
        ListIterator<String> iterator = result.listIterator(result.size());
        while(iterator.hasPrevious())
        {
            resultString += iterator.previous();
        }
        resultString += String.format("Total distance: %.3f pixel units.\n",distance.get(PART2));
        return resultString;
    }

}


