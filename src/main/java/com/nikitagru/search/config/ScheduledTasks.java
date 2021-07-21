package com.nikitagru.search.config;

import com.nikitagru.search.search.Searcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Scheduled(fixedRate = 5000)
    public void findAirports() {
        Searcher searcher = new Searcher();
        searcher.showAirports();
    }
}
