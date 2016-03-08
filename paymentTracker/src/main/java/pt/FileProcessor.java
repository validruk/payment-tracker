package pt;

import java.io.BufferedReader;

/**
 * 
 * Enable to process data from file in format CURRENCY AMOUNT
 *
 */
public class FileProcessor extends Processor {
  
  private final String file;
  
  public FileProcessor(String file) {
   super();
   this.file = file;
  }

  @Override
  public void process() {
    try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader((new java.io.FileInputStream(file))))){
      String line = null;
      int counter = 0;
      while ((line = reader.readLine()) != null){
        parseAndProccesValue(line);
        counter++;
      }
      
      System.out.println("Read " + counter + " items from file " + file);
    } catch (Exception e) {
      processError("Problem to parse file", e);
    }
  }

}
