package rate_limiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiting extends AbstractGatewayFilterFactory<Object> {

    private static final int CAPACITY = 20;
    private static final Duration REFILL_PERIOD = Duration.ofMinutes(1);
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimiting() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String ip = "";
            if (exchange.getRequest().getRemoteAddress() != null
                    && exchange.getRequest().getRemoteAddress().getAddress() != null) {
                ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            }
            Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);
            if (bucket.tryConsume(1)) {
                return chain.filter(exchange);
            }
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        };
    }

    private Bucket newBucket(String key) {
        Refill refill = Refill.intervally(CAPACITY, REFILL_PERIOD);
        Bandwidth limit = Bandwidth.classic(CAPACITY, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}