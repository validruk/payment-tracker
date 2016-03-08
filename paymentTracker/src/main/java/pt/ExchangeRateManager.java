package pt;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExchangeRateManager {
  // currency1 currency2 buyRate sellRate
  //e.g USD CZK 25.12 28.14
  private static String ratePattern = "^[A-Z]{3}[\\s]{1}[A-Z]{3}[\\s]{1}[0-9]{1,10}[.]{1}[0-9]{2}[\\s]{1}[0-9]{1,10}[.]{1}[0-9]{2}$";
  
  private static class Holder {
    static final ExchangeRateManager INSTANCE = new ExchangeRateManager();
  }

  public static ExchangeRateManager getInstance() {
    return Holder.INSTANCE;
  }

  ConcurrentMap <String, ExchangeRateBean> rateMap = new ConcurrentHashMap<>();
  
  public void addRate(ExchangeRateBean rate) {
    String key = generateKey(rate.getCurrency1(), rate.getCurrency2());
    rateMap.put(key, rate);
  }
  
  public ExchangeRateBean getRate(String currency1, String currency2) {
    String key = generateKey(currency1, currency2);
    return rateMap.get(key);
  }

  public String generateKey(String currency1, String currency2) {
    return currency1 + "|" + currency2;
  }
  
  /**
   * Return amount in other currencies by buy rate
   * @param currency1
   * @param amount
   * @return
   */
  public String convertToOtherCurrencies(String currency1, BigDecimal amount) {
    StringBuilder sb = new StringBuilder();
    
    for (Entry<String, ExchangeRateBean> entry : rateMap.entrySet()) {
      if (entry.getKey().startsWith(currency1)) {
        sb.append(" (").append(entry.getValue().getCurrency2()).append(" ").append(entry.getValue().buy(amount)).append(")");
      }
    }
    return sb.toString();
  }
  
  /**
   * Load exchange rates from files
   * @param file
   * @throws Exception
   */
  public void loadCurrencieFromFile(String file) throws Exception {
    try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader((new java.io.FileInputStream(file))))) {
      String line = null;
      int counter = 0;
      while ((line = reader.readLine()) != null) {
        if (line.matches(ratePattern)) {
          String[] array = line.split(" ");
          ExchangeRateBean rate = new ExchangeRateBean.Builder(array[0], array[1]).rateBuy(new BigDecimal(array[2]))
              .rateSell(new BigDecimal(array[3])).build();
          addRate(rate);
          System.out.println("New exchange rate: " + rate.toString());
          counter++;
        } else {
          System.err.println("Rate item does'n match input patern");
        }
      }

      System.out.println("Read " + counter + " items from file " + file);
    } catch (Exception e) {
      System.err.println("Problem to process exchange rates from file " + file);
      throw e;
    }
  }
}