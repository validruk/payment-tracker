package pt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrackerDataManagerTest {
  
  private TrackerDataManager tdm;
  
  @Before
  public void setUp() {
    tdm = TrackerDataManager.getInstance();
    tdm.add("USD", new BigDecimal("5"));
    tdm.add("USD", new BigDecimal("8"));
    tdm.add("CZK", new BigDecimal("1"));
    tdm.add("CZK", new BigDecimal("-5"));
    tdm.add("HUF", new BigDecimal("21.00"));   
  }
  
  @After
  public void cleanUp(){
    tdm.cleanAll();
  }

  @Test
  public void testAdd() {
    // number of currencies
    assertEquals("5 records in 3 currencies must be 3", 3, tdm.getNumberOfCurrencies());
    // total amount
    assertEquals("total amount should be 30.00", new BigDecimal("30.00"), tdm.getTotalAmount());
  }

  @Test
  public void testRecalculate() {
    assertTrue("report should contain HUF currency", tdm.recalculate().contains("HUF"));
  }
  
  @Test
  public void testMultiThreadAdd(){
    // 10 threads
    for (int i = 0; i < 10; i++) {
      Thread thread = new Thread(){
        @Override
        public void run(){
          for (int j = 0; j < 100; j++) {
            TrackerDataManager t = TrackerDataManager.getInstance();
            t.add("CAD", new BigDecimal("1.00"));
            assertEquals("partial number of currencies in multithread enviroment", 4, t.getNumberOfCurrencies());
          }
        }
      };
      thread.start();
    }
    
    //wait 2 second to all threads finished
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // 3 currencies were created before this test
    assertEquals("number of currencies in multithread enviroment", 4, tdm.getNumberOfCurrencies());
    assertEquals("total amount in multithread enviroment should be 1030.00", new BigDecimal("1030.00"), tdm.getTotalAmount());
  }

}
