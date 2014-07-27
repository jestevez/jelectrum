package jelectrum;

import java.util.Map;
import java.util.HashSet;
import com.google.bitcoin.core.Sha256Hash;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.StoredBlock;
import com.google.bitcoin.core.Block;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.DBCollection;
import com.mongodb.DB;

public class JelectrumDBMongo extends JelectrumDB
{
    private MongoClient mc;
    private DB db;
    private Config conf;

    public JelectrumDBMongo(Config conf)
        throws Exception
    {
        super(conf);

        conf.require("mongo_db_host");
        conf.require("mongo_db_compression");
        conf.require("mongo_db_name");
        this.conf = conf;

        open();

   }

    public void open()
    {

        try
        {
            boolean compress = conf.getBoolean("mongo_db_compression");


            MongoClientOptions.Builder opts = MongoClientOptions.builder();
            opts.connectionsPerHost(5000);

            mc = new MongoClient(conf.get("mongo_db_host"), opts.build());
            db = mc.getDB(conf.get("mongo_db_name"));

            tx_map = new MongoMap<Sha256Hash, SerializedTransaction>(getCollection("tx_map"), compress);
            address_to_tx_map = new MongoMap<String, HashSet<Sha256Hash> >(getCollection("address_to_tx_map"), compress);
            block_store_map = new CacheMap<Sha256Hash, StoredBlock>(25000,new MongoMap<Sha256Hash, StoredBlock>(getCollection("block_store_map"),compress));
            special_block_store_map = new MongoMap<String, StoredBlock>(getCollection("special_block_store_map"),compress);
            block_map = new CacheMap<Sha256Hash, SerializedBlock>(240,new MongoMap<Sha256Hash, SerializedBlock>(getCollection("block_map"),compress));
            tx_to_block_map = new MongoMap<Sha256Hash, HashSet<Sha256Hash> >(getCollection("tx_to_block_map"),compress);
            txout_spent_by_map = new MongoMap<String, HashSet<Sha256Hash> >(getCollection("txout_spent_by_map"),compress);
            block_rescan_map = new MongoMap<Sha256Hash, String>(getCollection("block_rescan_map"),compress);
            special_object_map = new MongoMap<String, Object>(getCollection("special_object_map"),compress);
            header_chunk_map = new CacheMap<Integer, String>(200, new MongoMap<Integer, String>(getCollection("header_chunk_map"),compress));

        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }


    }

    private DBCollection getCollection(String name)
    {
        return db.getCollection(name);
    }

    public Map<Sha256Hash, StoredBlock> getBlockStoreMap()
    {   
        return block_store_map;
    }

    public Map<String, StoredBlock> getSpecialBlockStoreMap()
    {   
        return special_block_store_map;
    }

    public Map<Sha256Hash,SerializedTransaction> getTransactionMap()
    {   
        return tx_map;
    }
    public Map<Sha256Hash, SerializedBlock> getBlockMap()
    {   
        return block_map;
    }

    public Map<String, HashSet<Sha256Hash> > getAddressToTxMap()
    {   
        return address_to_tx_map;
    }

    public Map<Sha256Hash, HashSet<Sha256Hash> > getTxToBlockMap()
    {   
        return tx_to_block_map;
    }


    public Map<String, HashSet<Sha256Hash> > getTxOutSpentByMap()
    {
        return txout_spent_by_map;
    }

    public Map<Sha256Hash, String> getBlockRescanMap()
    {
        return block_rescan_map;
    }

    public Map<String, Object> getSpecialObjectMap()
    {
        return special_object_map;
    }

    public Map<Integer, String> getHeaderChunkMap()
    {
        return header_chunk_map;
    }

}