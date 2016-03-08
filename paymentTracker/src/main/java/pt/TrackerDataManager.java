package pt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * It holds currency and actual sum of all amounts.
 * 
 * Thread safe singleton. 
 *
 */
public final class TrackerDataManager {
  
  private static class Holder {
    static final TrackerDataManager INSTANCE = new TrackerDataManager();
  }

  public static TrackerDataManager getInstance() {
    return Holder.INSTANCE;
  }
  
  /**
   * It is not necessary to by thread safe
   * Every currency has actual sum of all amounts
   */
  private Map<String, BigDecimal> currencyMap = new HashMap<>();
  
  /**
   * Add new amount to currency.
   * @param currency
   * @param amount
   */
  public synchronized void add(String currency, BigDecimal amount) {
    BigDecimal restAmount = currencyMap.get(currency);
    if (restAmount == null) {
      restAmount = BigDecimal.ZERO;
    }
    
    BigDecimal newAmount = restAmount.add(amount);
    currencyMap.put(currency, newAmount);
  }
  
  /**
   * Create currency output string 
   * @return
   */
  public synchronized String recalculate() {
    StringBuilder sb = new StringBuilder();
    ExchangeRateManager exRate = ExchangeRateManager.getInstance();
    for (Entry<String, BigDecimal> entry : currencyMap.entrySet()) {
      if (BigDecimal.ZERO.compareTo(entry.getValue()) == 0) {
        // amount is 0, not displayed
        continue;
      }
      sb.append(entry.getKey()).append(" ").append(entry.getValue());
      // try convert to other currencies
      sb.append(exRate.convertToOtherCurrencies(entry.getKey(), entry.getValue()));
      sb.append("\n");
    }
    // clear map for new data 
    // currencyMap.clear();

    return sb.toString();
  }
  
  /**
   * Only for testing
   * @return
   */
  protected int getNumberOfCurrencies() {
    return currencyMap.size();
  }
  
  /**
   * Only for testing
   * @return
   */
  protected BigDecimal getTotalAmount() {
    BigDecimal result = BigDecimal.ZERO;
    for (Entry<String, BigDecimal> entry : currencyMap.entrySet()) {
      if (entry.getValue() != null) {
        result = result.add(entry.getValue());
      }
    }
    return result;
  }
  
  /**
   * Only for testing
   * @return
   */
  protected void cleanAll() {
    currencyMap.clear();
  }
}
