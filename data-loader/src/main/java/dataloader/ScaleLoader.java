package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import domain.entity.Scale;
import domain.entity.Port;
import domain.entity.Operation;
import domain.entity.Vessel;
import domain.entity.Berth;
import domain.valueobject.Cargo;
import domain.valueobject.OperationType;
import domain.valueobject.Quantity;

import java.util.UUID;
import utils.TruckUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.time.format.DateTimeFormatter;

public class ScaleLoader {

  	private static final String ENDPOINT_URL = "http://localhost:8080/scales";

  	public static void insert(int numberOfScales) throws JsonProcessingException {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
    	    .uri(URI.create(ENDPOINT_URL))
    	    .header("Content-Type", "application/json")
    	    .POST(HttpRequest.BodyPublishers.ofString(""));

		String stringOperations = OperationLoader.findAll();
		JSONArray operationsJSON = new JSONObject(stringOperations).getJSONArray("content");
		
		for (int i = 0; i < numberOfScales; i++) {
			Scale scale = Scale.random();
			
          	int nOperations = 3;
          	List<Operation> operations = new ArrayList<>();
			for (int j = 0; j < nOperations; j++) {
				operations.add(OperationLoader.deserialize(operationsJSON.getJSONObject((i + j) % numberOfScales)));
			}
					
    		String operationsJson = operations.stream()
        	.map(operationJSON -> "{\"idOperation\":\"" + operationJSON.getId().toString() + "\", " +
        	        "\"cargo\":\"" + operationJSON.getCargo().toString() + "\", " +
        	        "\"operationType\":\"" + operationJSON.getType().toString() + "\", " +
        	        "\"quantity\":\"" + operationJSON.getQuantity().getValue() + "\"}")
        	.collect(Collectors.joining(", "));

			String json = "{\"idScale\":\"" + scale.getId().toString() + "\", " +
    		    "\"startingTime\":\"" + TruckUtils.localDateTimeToString(scale.getStartingTime()) + "\", " +
    		    "\"finishingTime\":\"" + TruckUtils.localDateTimeToString(scale.getFinishingTime()) + "\", " +
    		    "\"operations\": [" + operationsJson + "]" +
    		    "}";
					
    	    HttpRequest request = requestBuilder.copy()
    	        .method("POST", HttpRequest.BodyPublishers.ofString(json))
    	        .build();
    	    try {
    	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    	        System.out.println("Request #" + (i) + ":");
    	        System.out.println("\tstatus: " + response.statusCode());
    	        System.out.println("\tstatus: " + response.body());
    	    } catch (Exception e) {
    	        System.err.println("Request #" + (i) + " failed:");
    	        System.out.println("\tstatus: " + e.getMessage());
    	    }
    	}
  	}

	public static String findAll() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL))
				.GET();
		HttpRequest request = requestBuilder.copy()
				.method("GET", HttpRequest.BodyPublishers.ofString(""))
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Request #0:");
			System.out.println("\tstatus: " + response.statusCode());
			System.out.println("\tstatus: " + response.body());
			return response.body();
		} catch (Exception e) {
			System.err.println("Request #0 failed:");
			System.out.println("\tstatus: " + e.getMessage());
		}
		return "";
	}

	public static void deleteAll() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL))
				.DELETE();
		HttpRequest request = requestBuilder.copy()
				.method("DELETE", HttpRequest.BodyPublishers.ofString(""))
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Request #0:");
			System.out.println("\tstatus: " + response.statusCode());
			System.out.println("\tstatus: " + response.body());
		} catch (Exception e) {
			System.err.println("Request #0 failed:");
			System.out.println("\tstatus: " + e.getMessage());
		}
	}

	public static Scale deserialize(JSONObject json) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Scale scale = new Scale();
		scale.setId(UUID.fromString(json.getString("idScale")));
		scale.setStartingTime(LocalDateTime.parse(json.getString("startingTime"), formatter));
		scale.setFinishingTime(LocalDateTime.parse(json.getString("finishingTime"), formatter));
		JSONArray operations = json.getJSONArray("operations");
		List<Operation> operationsList = new ArrayList<>();
		for (int i = 0; i < operations.length(); i++) {
			Operation operation = new Operation();
			operation.setId(UUID.fromString(operations.getJSONObject(i).getString("idOperation")));
			operation.setCargo(Cargo.valueOf(operations.getJSONObject(i).getString("cargo")));
			operation.setType(OperationType.valueOf(operations.getJSONObject(i).getString("operationType")));
			operation.setQuantity(new Quantity(operations.getJSONObject(i).getInt("quantity")));
			operationsList.add(operation);
		}
		scale.setOperations(operationsList);
		return scale;
	}

	public static String getScaleJSON(List<Scale> scales) {
		String json = "";
		String stringOperations = OperationLoader.findAll();
		JSONArray operationsJSON = new JSONObject(stringOperations).getJSONArray("content");
		int counter = 0;
		for (Scale scale : scales) {
			Operation operation = OperationLoader.deserialize(operationsJSON.getJSONObject(counter));
			
          	int nOperations = 1;
          	List<Operation> operations = new ArrayList<>();
			for (int j = 0; j < nOperations; j++) {
				operations.add(operation);
			}
					
    		String operationsJson = operations.stream()
        	.map(operationJSON -> "{\"idOperation\":\"" + operationJSON.getId().toString() + "\", " +
        	        "\"cargo\":\"" + operationJSON.getCargo().toString() + "\", " +
        	        "\"operationType\":\"" + operationJSON.getType().toString() + "\", " +
        	        "\"quantity\":\"" + operationJSON.getQuantity().getValue() + "\"}")
        	.collect(Collectors.joining(", "));

			json += "{\"idScale\":\"" + scale.getId().toString() + "\", " +
    		    "\"startingTime\":\"" + TruckUtils.localDateTimeToString(scale.getStartingTime()) + "\", " +
    		    "\"finishingTime\":\"" + TruckUtils.localDateTimeToString(scale.getFinishingTime()) + "\", " +
    		    "\"operations\": [" + operationsJson + "]" +
    		    "},";
			counter++;
		}
		if (json.length() > 0)  
			return json.substring(0, json.length() - 1);
		return json;
	}
}