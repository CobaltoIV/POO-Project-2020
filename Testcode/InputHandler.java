import java.io.*;
import java.util.*;

public class InputHandler {
    public static void main(String[] args) {
        String line;
        String splitToken = ",";
        Map<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        ArrayList<String> labels = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            line = br.readLine();
            String[] tmpLabels = line.split(splitToken);
            for(String string : tmpLabels)
                labels.add(string);

            while ((line = br.readLine()) != null)
            {
                String[] instance = line.split(splitToken);
                for (int i = 0; i < labels.size(); i++){
                    if (map.get(tmpLabels[i]) == null)
                        map.put(tmpLabels[i], new ArrayList<Integer>());
                    map.get(tmpLabels[i]).add(Integer.parseInt(instance[i]));
                }
                
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String string : labels) {
            System.out.println(map.get(string));
        }
    }
}