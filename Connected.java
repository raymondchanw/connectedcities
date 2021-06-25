import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Connected {
    public static void main (String[] args) throws Exception {
        // Expect correct inputs, no further checking and handling for input args
        String fileName = args[0];
        String targetCity1 = args[1];
        String targetCity2 = args[2];
        
        // 0. Preprocess
        Integer cid = 0;
        // 0.1. Assign a Integer Id for each city
        // with presumption that there is no more than 2,147,483,647 cities
        Map<String, Integer> citiesId = new HashMap<String, Integer>();
        // 0.2. Map for storing connected cities by each cities
        Map<Integer, Set<Integer>> citiesEdge = new HashMap<Integer, Set<Integer>>();
        // 0.3. Read the file and parse cities connections
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String connectedCities[] = line.split(",");
                String _city1 = connectedCities[0].trim();
                String _city2 = connectedCities[1].trim();
                if (!citiesId.containsKey(_city1)) {
                    citiesId.put(_city1, cid);
                    citiesEdge.put(cid, new HashSet<Integer>());
                    cid++;
                }
                if (!citiesId.containsKey(_city2)) {
                    citiesId.put(_city2, cid);
                    citiesEdge.put(cid, new HashSet<Integer>());
                    cid++;
                }
                citiesEdge.get(citiesId.get(_city1)).add(citiesId.get(_city2));
                citiesEdge.get(citiesId.get(_city2)).add(citiesId.get(_city1));
            }
        } finally {
            if (br != null) br.close();
        }
        
        // 1. Check if two specified cities are in the list
        Integer targetId1 = citiesId.get(targetCity1);
        Integer targetId2 = citiesId.get(targetCity2);
        if (targetId1 == null || targetId2 == null) {
            System.out.println("no");
            return;
        }
        
        // 2. BFS
        Set<Integer> tempNext = null;
        Set<Integer> nextCities = new HashSet<Integer>();
        nextCities.add(targetId1);

        while (nextCities.size() > 0) {
            if (nextCities.contains(targetId2)) {
                System.out.println("yes");
                return;
            }
            tempNext = new HashSet<Integer>();
            for (Integer c : nextCities) {
                if (citiesEdge.get(c).size() > 0) {
                    tempNext.addAll(citiesEdge.get(c));
                    citiesEdge.get(c).clear();
                }
            }
            //nextCities.clear();
            nextCities = tempNext;
        }
        System.out.println("no");
    }
}