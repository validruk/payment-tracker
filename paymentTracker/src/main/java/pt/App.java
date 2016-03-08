package pt;

import java.util.Timer;
import java.util.TimerTask;

public class App {

  private static String help = "-------------\n"
      + "Select your option:\n"
      + "USD 123 - sample input\n"
      + "f file.txt - data from file\n"
      + "r rateFile.txt - file with currency rate\n"
      + "q - quit\n"
      + "-------------";

  public static void main(String[] args) {  
    System.out.println(help);
    
    startOneMinuteReport();
    
    // process input file
    if (args.length > 1) {
      if ("-f".equals(args[0])) {
        String fileName = args[1];
        Processor processor = new FileProcessor(fileName);
        processor.process();
      }
    }
    
    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
    while (true) {
      try {
        String line = in.readLine();
        if (line == null || "q".equals(line)){
          break;
        }
        
        if (line.startsWith("f ")) {
          // input from file
          String[] fileArray = line.split(" ");
          if (fileArray.length == 2) {
            Processor processor = new FileProcessor(fileArray[1]);
            processor.process();
            continue;
          }
        }
        
        if (line.startsWith("r ")) {
          // load exchange rates from file
          String[] fileArray = line.split(" ");
          if (fileArray.length == 2) {
            ExchangeRateManager.getInstance().loadCurrencieFromFile(fileArray[1]);
            continue;
          }
        }
        
        //input from command line
        CommandLineProcessor processor = new CommandLineProcessor(line);
        processor.process();
   
      } catch (Exception e) {
        e.printStackTrace();
      } 
    }
  }

  /**
   * write every minute financial report
   */
  private static void startOneMinuteReport() {
    // start timer task to write currency amount
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        TrackerDataManager tdm = TrackerDataManager.getInstance();
        String output = tdm.recalculate();
        System.out.println("****************************************************************");
        System.out.println("***************** One minute report ****************************");
        System.out.println(output);
        System.out.println("****************************************************************");
      }
    };
  
    Timer timer = new Timer(true);
    timer.schedule(task, 60*1000, 60*1000);
  }

}
