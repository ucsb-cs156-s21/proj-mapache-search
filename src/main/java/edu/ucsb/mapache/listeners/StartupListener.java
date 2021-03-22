package edu.ucsb.mapache.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import edu.ucsb.mapache.entities.Counter;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.services.CounterService;
import edu.ucsb.mapache.services.GoogleSearchService;

// https://reflectoring.io/spring-boot-execute-on-startup/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(0)
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    CounterService counterService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("StartupListener#onApplicationEvent()");
        initializeCounters();
    }

    public void initializeCounters() {
        int limit = GoogleSearchService.SEARCH_LIMIT;
        counterService.initializeCounter("globalGoogleSearchApiTokenUsesToday",limit);
        counterService.resetIfNeeded("globalGoogleSearchApiTokenUsesToday", limit, 24);
    }

}
