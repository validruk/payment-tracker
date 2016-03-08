package pt;

import java.math.BigDecimal;

/**
 * 
 * Exchange rate with Builder pattern
 *
 */
public class ExchangeRateBean {
  
  private String currency1;
  private String currency2;
  private BigDecimal rateBuy;
  private BigDecimal rateSell;

  public String getCurrency1() {
    return currency1;
  }

  public void setCurrency1(String currency1) {
    this.currency1 = currency1;
  }

  public String getCurrency2() {
    return currency2;
  }

  public void setCurrency2(String currency2) {
    this.currency2 = currency2;
  }

  public BigDecimal getRateBuy() {
    return rateBuy;
  }

  public void setRateBuy(BigDecimal rateBuy) {
    this.rateBuy = rateBuy;
  }

  public BigDecimal getRateSell() {
    return rateSell;
  }

  public void setRateSell(BigDecimal rateSell) {
    this.rateSell = rateSell;
  }

  public BigDecimal buy(BigDecimal amount) {
    return amount.multiply(rateBuy);
  }

  public BigDecimal sell(BigDecimal amount) {
    return amount.multiply(rateSell);
  }
    
  public static class Builder {
    // required parameters
    private final String currency1;
    private final String currency2;
    // optional parameters
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
    
    public Builder(String currency1, String currency2) {
      this.currency1 = currency1;
      this.currency2 = currency2;
    }
    
    public Builder rateBuy(BigDecimal rate) {
      rateBuy = rate;
      return this;
    }
    
    public Builder rateSell(BigDecimal rate) {
      rateSell = rate;
      return this;
    }
    
    public ExchangeRateBean build() {
      return new ExchangeRateBean(this);
    }
  }
  
  private ExchangeRateBean(Builder builder) {
    this.currency1 = builder.currency1;
    this.currency2 = builder.currency2;
    this.rateBuy = builder.rateBuy;
    this.rateSell = builder.rateSell;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ExchangeRate [currency1=");
    builder.append(currency1);
    builder.append(", currency2=");
    builder.append(currency2);
    builder.append(", rateBuy=");
    builder.append(rateBuy);
    builder.append(", rateSell=");
    builder.append(rateSell);
    builder.append("]");
    return builder.toString();
  }
}
