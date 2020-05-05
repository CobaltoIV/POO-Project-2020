import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InputHandler {
    private ArrayList<String> _labels;
    private Map<String, ArrayList<Integer>> _values;

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
            for(String string : tmpLabels)
                _labels.add(string);
            
            // Parse values
            while ((line = br.readLine()) != null) {
                String[] instance = line.split(splitToken);
                for (int i = 0; i < _labels.size(); i++){
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

    public ArrayList<String> getLabels (){
        return _labels;
    }

    public Map<String, ArrayList<Integer>> getValues (){
        return _values;
    }
    
    public Map<String, ArrayList<Integer>> getValuesUnique (){
        Map<String, ArrayList<Integer>> valuesUnique = new HashMap<String, ArrayList<Integer>>();
        for (String key : _labels)
            valuesUnique.put(key, new ArrayList<Integer>(_values.get(key).stream().distinct().collect(Collectors.toList())));
        return valuesUnique;
    }
}