package codeworld.projectjava.registration.service;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private static final long TIME_WINDOW = 60 * 1000; // 60 seconds
    private static final int MAX_REQUESTS = 100;

    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    public Filter rateLimitingFilter() {
        return (ServletRequest request, ServletResponse response, FilterChain chain) -> {
            String ip = ((HttpServletRequest) request).getRemoteAddr();
            long now = Instant.now().toEpochMilli();

            requestCounts.putIfAbsent(ip, new RequestCounter());
            RequestCounter counter = requestCounts.get(ip);

            synchronized (counter) {
                if (now - counter.timestamp > TIME_WINDOW) {
                    counter.timestamp = now;
                    counter.count = 0;
                }
                counter.count++;

                if (counter.count > MAX_REQUESTS) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Too many requests\"}");
                    response.getWriter().flush();
                    return;
                }
            }

            chain.doFilter(request, response);
        };
    }

    private static class RequestCounter {
        long timestamp = Instant.now().toEpochMilli();
        int count = 0;
    }
} 
