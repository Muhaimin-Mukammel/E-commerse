# Zestora (When Completed - A Vision into the Future)

Zestora is a high-performance, production-ready online marketplace built for multiple sellers. It uses a modern microservices setup where the buyer's shopping side (User-Mode) and the seller's management side (Merchant-Mode) are completely separated. This split design keeps both worlds independent, prevents system crashes from spreading, and allows the platform to handle massive growth easily.

## Once finished, the system will include these powerful features:

* Unified Shop Creation for Sellers: New merchants launch their account and their first digital storefront simultaneously through a single, seamless signup process. This instant setup ensures that every seller profile immediately owns an active store, with every newly added product permanently locked to its specific store ID behind the scenes.
* Global Browsing + Individual Store Search: Buyers can browse a massive catalog featuring products from all stores combined in one place. If they prefer, they can also visit a specific seller's shop to search and buy directly from them.
* Smart Search with Spell-Checking: The platform will have a built-in search engine that understands what users mean, even if they make typos or misspell a word. Whenever a seller updates a product, the search system updates instantly.
* Cost-Saving Database Sync via Kafka: To connect the separate buyer and seller databases without breaking the bank, Zestora will use Kafka data streaming. It automatically monitors and copies over only the exact data that needs to be shared, keeping both databases perfectly in sync.
* Ultra-Fast Handling of Traffic Spikes: The backend will use modern Java Virtual Threads to process thousands of customer actions at the exact same time. This prevents the server from freezing up during massive sales events.
* Smooth Performance Under Heavy Load: To stop database slowdowns, the platform will use clever tricks like database replicas, connection pooling, and a fast-track system that saves active transactions directly into a super-fast Redis cache.
* Automatic Sales Tracking & Tech Monitoring: Sellers get a secure digital ledger that tracks their revenue history and archives product prices over time. On top of that, the entire platform is stress-tested using k6 to simulate thousands of users, with live activity dashboards tracked on Grafana.
