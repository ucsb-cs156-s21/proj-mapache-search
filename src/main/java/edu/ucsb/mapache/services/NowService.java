package edu.ucsb.mapache.services;

import org.springframework.stereotype.Service;

/**
 * A service to get the time/date right now.  This wraps the 
 * normal java idiom of using java.util.Date now = new java.util.Date() so
 * that it can be mocked and stubbed for testing.
 * Compare: https://stackoverflow.com/a/11887958
 */

@Service
public class NowService {
    public  java.util.Date now() {
        return new java.util.Date();
    }

    public  long currentTime() {
        return now().getTime();
    }
}
