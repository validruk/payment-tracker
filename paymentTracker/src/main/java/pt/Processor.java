package pt;

import java.math.BigDecimal;

public abstract class Processor {
  // pattern USD -123.123456
  private static String currencyPattern = "^[A-Z]{3}[\\s]{1}[+-]?[0-9]{1,10}[.]?[0-9]{0,6}$";
  
  public abstract void process();

  /**
   * Parse input and insert currency with amount
   * @param line
   * @throws Exception
   */
  protected void parseAndProccesValue(String line) throws Exception {
    if (line.matches(currencyPattern)) {
      //System.out.println("For input " + line + " is pattern OK.");
      String[] splitted = line.split(" ");
      if (splitted.length != 2) {
        throw new Exception("Input is not correctly parsed.");
      }
      String currency = splitted[0];
      BigDecimal amount = new BigDecimal(splitted[1]);
      TrackerDataManager tdm = TrackerDataManager.getInstance();
      tdm.add(currency, amount);
    } else {
      throw new Exception("Your input doesn't match input pattern (e.g. CZK 200)");
    }
  }
  
  /**
   * Process error
   * @param message
   * @param e
   */
  protected void processError(String message, Exception e) {
    System.err.println(message);
    System.err.println(e.getLocalizedMessage());
  }
}
