package backend;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.io.*;

public class CampusParser {
    /**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "professor","course" pairs
	 * @param: id_name      The Map that stores parsed <building id,
	 *                      building name> pairs; usually an empty Map.
     * @param: name_id      The Map that stores parsed <building name,
	 *                      building id> pairs; usually an empty Map.
	 * @param: coordinate   The Set that stores parsed professors; usually an empty
	 *                      Set.
	 * @requires: filename != null && id_name != null && name_id != null && coordinate != null
	 * @modifies: id_name, name_id, coordinate
	 * @effects: adds parsed <building id, building name> pairs to Map
	 *           id_name; adds parsed <building name, building id> pairs to Map
	 *           name_id; adds parsed x and y coordinate to Map coordinate.
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
    public static void readNodeData(String filename, HashMap<String, String> id_name,HashMap<String, String> name_id, HashMap<String,Coordinate> coordinate)
            throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = null;
            while((line = reader.readLine()) != null)
            {
                String[] elements = line.split(",");
				if (elements.length != 4) {
					throw new IOException("File " + filename + " not a CSV (\"name\",\"id\",\"x\",\"y\") file.");
				}
                id_name.put(elements[1],elements[0]);
                if(!elements[0].equals(new String()))
                    name_id.put(elements[0], elements[1]);
				coordinate.put(elements[1],new Coordinate(elements[0],elements[1],Double.parseDouble(elements[2]), Double.parseDouble(elements[3])));
            }
        }
    }

    /**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "id" pairs(edges)
	 * @param: idPair       The List that stores parsed <building id,
	 *                      building id> pairs; usually an empty List.
	 * @requires: filename != null && idPair != null
	 * @modifies: idPair
	 * @effects: adds parsed <building id, building id> pairs to List
	 *           idPair
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
    public static void readEdgeData(String filename, LinkedList<Map.Entry<String,String>> idPair)
            throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = null;
            while((line = reader.readLine()) != null)
            {
                String[] elements = line.split(",");
				if (elements.length != 2) {
					throw new IOException("File " + filename + " not a CSV (\"id\",\"id\") file.");
				}
                idPair.add(new SimpleEntry<>(elements[0],elements[1]));
            }
        }
    }

}
