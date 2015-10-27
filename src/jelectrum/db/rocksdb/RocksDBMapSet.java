package jelectrum.db.rocksdb;

import jelectrum.db.DBMapSet;
import jelectrum.db.DBTooManyResultsException;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import jelectrum.TimeRecord;

import org.bitcoinj.core.Sha256Hash;


public class RocksDBMapSet extends DBMapSet
{ 
  RocksDB db;
  String name;

  public RocksDBMapSet(RocksDB db, String name)
  { 
    this.db = db;
    this.name = name;
  }


  public void add(String key, Sha256Hash hash)
  {
    String s = name + "/" + key + "/" + hash.toString();
    byte b[]=new byte[0];
    try
    {
      db.put(s.getBytes(), b);
    }
    catch(RocksDBException e)
    {
      throw new RuntimeException(e);
    }

  }


  public Set<Sha256Hash> getSet(String key, int max_reply)
  {
    String s = name + "/" + key + "/";

    HashSet<Sha256Hash> set = new HashSet<Sha256Hash>();
    int count = 0;

    //try
    {
      RocksIterator it = db.newIterator();
      it.seek(s.getBytes());

      while(it.isValid())
      { 
        String curr_key = new String(it.key());
        if (!curr_key.startsWith(s)) break;

        String hash_string = curr_key.substring(s.length());
        set.add(new Sha256Hash(hash_string));
        count++;

        if (count > max_reply) throw new DBTooManyResultsException();

        it.next();
              
      }

    }
    /*catch(RocksDBException e)
    { 
      throw new RuntimeException(e);
    }*/


    return set;

  }


}
