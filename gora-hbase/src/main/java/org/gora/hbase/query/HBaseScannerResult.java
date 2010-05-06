
package org.gora.hbase.query;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.gora.hbase.store.HBaseStore;
import org.gora.persistency.Persistent;

/**
 * Result of a query based on an HBase scanner.
 */
public class HBaseScannerResult<K, T extends Persistent> 
  extends HBaseResult<K, T> {

  private final ResultScanner scanner;
  
  public HBaseScannerResult(HBaseStore<K,T> dataStore, HBaseQuery<K, T> query, 
      ResultScanner scanner) {
    super(dataStore, query);
    this.scanner = scanner;
  }
  
  @Override
  public boolean next() throws IOException {
    if(isLimitReached()) { 
      return false;
    }
    
    Result result = scanner.next();
    if (result == null) {
      return false;
    }
    
    readNext(result);
    
    return true;
  }

  @Override
  public void close() throws IOException {
    scanner.close();
  }
  
  @Override
  public float getProgress() throws IOException {
    //TODO: if limit is set, we know how far we have gone 
    return 0;
  }
  
}
