import java.util.*;
import java.io.*;

public class D4 {
  public static void main(String[] args) {

    // Argument check
    if (args.length > 1) {
      System.out.println("Enter only one argument, the file to read.");
      System.exit(1);
    }
    if (args.length == 0) {
      System.out.println("Please enter the file to read as an argument.");
      System.exit(1);
    }

    String elementFile = "elements.txt";
    String inputFile = args[0];
    File file = new File(elementFile);
    File inFile = new File(inputFile);
    HashMap<String, String> map = new HashMap<String, String>();
    int track = 0;

    // populate map with elements
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String nextToken = sc.nextLine();
        String[] line = nextToken.split("[\\\":,]");
        map.put(line[1], line[4]);
      }

      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error with elements.txt");
      System.exit(1);
    }

    // read input based on element
    try {
      Scanner in = new Scanner(inFile);
      System.out.println();
      while (in.hasNextLine()) {
        String nextLine = in.nextLine();
        String[] line = nextLine.split(" ");
        StringBuilder toAdd = new StringBuilder();
        StringBuilder toCode = new StringBuilder();

        lineloop:
        for (int i = 0; i < line.length; i++) {
          for (int j = 0; j < line[i].length(); j++) {
            String c = Character.toString(line[i].charAt(j)).toUpperCase();
            String c2 = "";
            if (j + 1 < line[i].length())
              c2 = Character.toString(line[i].charAt(j+1)).toLowerCase();
            String con = c.concat(c2);
            if(map.containsKey(c)) {
              toAdd.append(c).append(" - ");
              toCode.append(map.get(c)).append(" - ");
            } else if (map.containsKey(con)) {
              toAdd.append(con).append(" - ");
              toCode.append(map.get(con)).append(" - ");
              j++;
            } else if (!map.containsKey(c) && !map.containsKey(con)) {
              System.out.print("Could not create name for \"");
              for (int k = 0; k < line.length; k++) {
                System.out.print(line[k] + " ");
              }
              System.out.print("\" out of elements.\n\n");
              toAdd.setLength(0);
              toCode.setLength(0);
              break lineloop;
            }
          }
        }
        if (toAdd.length() > 0 && toCode.length() > 0) {
          System.out.println(toAdd);
          System.out.println(toCode);
          System.out.println();
        }
      }

      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("File \"" + args[0] + "\" not found.");
      System.exit(1);
    }

  }
}
