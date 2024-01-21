package  io;
import model.FormulaElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SaveFile{


    public SaveFile(HashMap<String, FormulaElement> formulaStorage, String path)  {

        try{  // Catch errors in I/O if necessary.
            String stuff = "Save this content to file.";
            File file = new File(path);
            // if file does not exist, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String key: formulaStorage.keySet()) {
                String formula = key + ":=("+ formulaStorage.get(key).toString() + ")\n";
                bw.write(formula);
            }

            bw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}