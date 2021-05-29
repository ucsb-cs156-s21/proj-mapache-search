package edu.ucsb.mapache.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * A service to get any of the property values that are defined, 
 * for example, in secrets-localhost.properties
 */

@Service
public class PropertiesService {
    
    @Value("${app.namespace}")
    private String namespace;
    
    public String getNamespace() { return namespace; }

}
