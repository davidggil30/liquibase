package com.liquibase.cucumber;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
	
	private Map<String, Object> scenarioContext;
	
	public ScenarioContext() {
		scenarioContext = new HashMap<>();
	}
	
	public void setProperty(String key, Object value) {
		scenarioContext.put(key, value);
	}
	
	public Object getProperty(String key) {
        return scenarioContext.get(key);
	}
	
}
