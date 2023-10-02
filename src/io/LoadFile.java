package io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 
public class LoadFile {

    String sCurrentLine;
    String returnStr = "";
 
	public LoadFile(String fileName){

		try {

            File file = new File(fileName);
            FileReader reader = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(reader);

			while ((sCurrentLine = br.readLine()) != null) {
                returnStr += sCurrentLine + "\n";
			}

            if (br != null)br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public String returnString() {
        return returnStr;
    }
}
