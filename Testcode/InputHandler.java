import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InputHandler {
    private ArrayList<String> _labels;
    private Map<String, ArrayList<Integer>> _values;
    private Map<String, ArrayList<Integer>> _valuesUnique;

    private String _filename;

    public InputHandler (String filename){
        _filename = filename;
    }

    public void parseFile() {
        String line;
        String splitToken = ",";
        
        _labels = new ArrayList<String>();
        _values = new HashMap<String, ArrayList<Integer>>();
        _valuesUnique= new HashMap<String, ArrayList<Integer>>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(_filename));

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
            
            // Get unique values for each feature
            for (String key : _labels)
                _valuesUnique.put(key, new ArrayList<Integer>(_values.get(key).stream().distinct().collect(Collectors.toList())));

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
        return _valuesUnique;
    }
}