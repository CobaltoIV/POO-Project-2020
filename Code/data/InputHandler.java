package data;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import scoring.Edges;
import scoring.LL_edges;
import scoring.MDL_edges;

public class InputHandler {
    /**
     * ArrayList of the labels extracted from the first line of the CSV file
     * Calculated through {@link data.InputHandler#parseFile(String)}
     * Getter {@link data.InputHandler#getLabels()}
     */
    private ArrayList<String> _labels;

    /**
     * ArrayList for each feature of the values extracted from the CSV file
     * Calculated through {@link data.InputHandler#parseFile(String)}
     * Getter {@link data.InputHandler#getValues()}
     */
    private Map<String, ArrayList<Integer>> _values;

    /**
     * Parses input CSV file intro {@link data.InputHandler#_labels} and {@link data.InputHandler#_values} 
     * @param filename CSV File to be parsed
     */
    public void parseFile(String filename) {
        String line;
        String splitToken = ",";

        _labels = new ArrayList<String>();
        _values = new HashMap<String, ArrayList<Integer>>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            // Parse features/class line
            line = br.readLine();
            String[] tmpLabels = line.split(splitToken);
            for (String string : tmpLabels)
                _labels.add(string);

            // Parse values
            while ((line = br.readLine()) != null) {
                String[] instance = line.split(splitToken);
                for (int i = 0; i < _labels.size(); i++) {
                    if (_values.get(tmpLabels[i]) == null)
                        _values.put(tmpLabels[i], new ArrayList<Integer>());
                    _values.get(tmpLabels[i]).add(Integer.parseInt(instance[i]));
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @return _labels (see {@link data.InputHandler#_labels})
     */
    public ArrayList<String> getLabels() {
        return _labels;
    }

    /**
     * @return _values (see {@link data.InputHandler#_values})
     */
    public Map<String, ArrayList<Integer>> getValues() {
        return _values;
    }

    /**
     * 
     * @return Unique values for each feature
     */
    public Map<String, ArrayList<Integer>> getValuesUnique() {
        Map<String, ArrayList<Integer>> valuesUnique = new HashMap<String, ArrayList<Integer>>();
        for (String key : _labels)
            valuesUnique.put(key,
                    new ArrayList<Integer>(_values.get(key).stream().distinct().collect(Collectors.toList())));
        return valuesUnique;
    }

    /**
     * @param  mode criterion used for scoring
     * @return Edge type, based on the chosen score
     */
    public Edges decideType(String mode) {

        Edges e;

        ArrayList<String> Criterions = new ArrayList<String>();
        Criterions.add("LL");
        Criterions.add("MDL");
        // add more modes if needed

        int counter = 0;
        Iterator<String> modes = Criterions.iterator();
        while (modes.hasNext()) {

            if (mode.equals(modes.next())) {
                if (counter == 0)
                    return e = new LL_edges();

                if (counter == 1)
                    return e = new MDL_edges();


                // add if() with next counter value for nem criterion
            }

            counter++;

        }
        System.out.println("Invalid Mode");
        System.exit(0);
        return e = new MDL_edges();

    }
}