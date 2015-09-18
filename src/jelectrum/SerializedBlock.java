package jelectrum;

import com.google.bitcoin.core.Block;
import com.google.bitcoin.core.NetworkParameters;


public class SerializedBlock implements java.io.Serializable
{
  public static final long serialVersionUID = 1280282305786765588L;

    private transient Block tx;

    private byte[] bytes;

    public SerializedBlock(Block tx)
    {
        this.tx = tx;
        bytes = tx.bitcoinSerialize();
    }

    public Block getBlock(NetworkParameters params)
    {
        if (tx != null) return tx;
        tx = new Block(params, bytes);
        return tx;
        
    }
    public byte[] getBytes()
    {
      return bytes;
    }


}
