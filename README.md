jelectrum
=========

An Electrum Server written in Java

This is designed as replacement for the python electrum server:
https://github.com/spesmilo/electrum-server

Reasons to exist
----------------

1) The inital blockchain sync is multithreaded and a good bit faster than the python implementation
(2 - 3 days with a fast system and SSD)

2) It is good to have multiple implementations of things in general


DB Options
----------

Not sure what to use?  Use slopbucket or rocksdb.

More details [DATABASE](DATABASE.md)

How to run
----------

Make your SSL cert:
./makekey.sh

Build:
ant jar

Config:
cp jelly.default.conf jelly.conf
edit jelly.conf as makes sense

Run:
java -Xmx3g -jar jar/Jelectrum.jar jelly.conf

My Instance
-----------

Feel free to connect to my instance which should be running the latest version.

```
b.1209k.com 50001 (tcp)
b.1209k.com 50002 (ssl)
h.1209k.com 50001 (tcp)
h.1209k.com 50002 (ssl)
```

What Doesn't Work
-----------------

1) HTTP/HTTPS.  I see no strong reason to support those over TCP and SSL+TCP.  I imagine it wouldn't be too hard
but I don't see the need.  If someone feels otherwise, let me know.

2) The following commands that clients don't seem to issue (yet):
```
blockchain.address.get_proof
blockchain.address.get_mempool
blockchain.utxo.get_address
```

(To check this list compare src/blockchain_processor.py from electrum-server to StratumConnection.java)

Monitoring
----------

If you are going to run it, monitor it!

I've made a monitoring tool that anyone to can use to monitor their electrum server (jelectrum or otherwise):

https://1209k.com/bitcoin-eye/ele.php

UTXO Troublehsooting
--------------------

[UTXO LOLWUT](UTXO-LOLWUT.md)

Mini Server Instructions
------------------------

[Mini Server Instructions](MINI-SERVER.md)


