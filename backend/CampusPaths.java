package backend;

import java.util.*;
import java.io.*;

public class CampusPaths
{
    public static void printAllBuildings(CampusGraph graph)
    {
        Iterator<String> buildings = graph.listAllBuildings();
        while (buildings.hasNext())
        {
            String building = buildings.next();
            System.out.println(building);
        }
    }
    public static void main(String[] args) {
        String menu = "b lists all buildings\n"
                    + "r prints directions for the shortest route between any two buildings\n"
                    + "q quits the program\n"
                    + "m prints a menu of all commands\n";
        CampusGraph graph = new CampusGraph();
        String nodeFilename = "data/RPI_map_data_Nodes.csv";
        String edgeFilename = "data/RPI_map_data_Edges.csv";
        graph.createGraph(nodeFilename, edgeFilename);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter command(Enter m for command menu): ");
        while(true)
        {
            String command = scanner.nextLine();
            if(command.equals("q"))
            {
                scanner.close();
                break;
            }
            else if(command.equals("m"))
            {
                System.out.println(menu);
            }
            else if(command.equals("r"))
            {
                System.out.print("First building id/name, followed by Enter: ");
                String PART1 = scanner.nextLine();
                System.out.print("Second building id/name, followed by Enter: ");
                String PART2 = scanner.nextLine();
                System.out.print(graph.findPath(PART1, PART2));
            }
            else if(command.equals("b"))
            {
                printAllBuildings(graph);
            }
            else
            {
                System.out.println("Unknown option");
            }
        }

    }
}
