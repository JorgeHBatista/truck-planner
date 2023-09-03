package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import domain.entity.Vessel;
import domain.entity.Scale;
import domain.entity.Port;
import domain.entity.Operation;

import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.TruckUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VesselLoader {

  	private static final String ENDPOINT_URL = "http://localhost:8080/vessels";

  	public static void insert(int numberOfVessels) throws JsonProcessingException {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
    	    .uri(URI.create(ENDPOINT_URL))
    	    .header("Content-Type", "application/json")
    	    .POST(HttpRequest.BodyPublishers.ofString(""));

		String stringScales = ScaleLoader.findAll();
		JSONArray scalesJSON = new JSONObject(stringScales).getJSONArray("content");

    	for (int i = 0; i < numberOfVessels; i++) {
    	    Vessel vessel = Vessel.random();
			List<Scale> scales = new ArrayList<>();
			int nScales = 3;
			for (int j = 0; j < nScales; j++) {
				scales.add(ScaleLoader.deserialize(scalesJSON.getJSONObject((i + j) % numberOfVessels)));
			}
			
			String scalesJson = scales.stream()
			    .map(scaleJSON -> {
			        // Generate the necessary properties for each scale
			        Port port = Port.random();
			        String operationsJson = scaleJSON.getOperations().stream()
			            .map(operation -> {
			                // Generate the necessary properties for each operation
			                String operationJson = "{\"idOperation\":\"" + operation.getId().toString() + "\", " +
			                        "\"cargo\":\"" + operation.getCargo().toString() + "\", " +
			                        "\"operationType\":\"" + operation.getType().toString() + "\", " +
			                        "\"quantity\":\"" + operation.getQuantity().getValue() + "\"}";
						
			                return operationJson;
			            })
			            .collect(Collectors.joining(", "));
					
			        String scaleJson = "{\"idScale\":\"" + scaleJSON.getId().toString() + "\", " +
			                "\"idVessel\":\"" + vessel.getId().toString() + "\", " +
			                "\"startingTime\":\"" + TruckUtils.localDateTimeToString(scaleJSON.getStartingTime()) + "\", " +
			                "\"finishingTime\":\"" + TruckUtils.localDateTimeToString(scaleJSON.getFinishingTime()) + "\", " +
			                "\"operations\":[" + operationsJson + "]}";
					
			        return scaleJson;
			    })
			    .collect(Collectors.joining(", "));
			
			String json = "{\"idVessel\":\"" + vessel.getId().toString() + "\", " +
			        "\"scales\":[" + scalesJson + "]}";
					
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

	public static String find(UUID id) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL + "/" + id))
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

	public static void update(UUID id) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL + "/" + id))
    	    	.header("Content-Type", "application/json")
    	    	.PUT(HttpRequest.BodyPublishers.ofString(""));

		String stringScales = ScaleLoader.findAll();
		JSONArray scalesJSON = new JSONObject(stringScales).getJSONArray("content");

    	Vessel vessel = Vessel.random();
		List<Scale> scales = new ArrayList<>();
		int nScales = TruckUtils.generateRandomNumber(1);
		Random random = new Random();
		int randomIndex = random.nextInt(Math.abs(scalesJSON.length() - nScales));
		for (int j = 0; j < nScales; j++) {
			scales.add(ScaleLoader.deserialize(scalesJSON.getJSONObject(randomIndex + j)));
		}
		
		String scalesJson = scales.stream()
		    .map(scaleJSON -> {
		        // Generate the necessary properties for each scale
		        Port port = Port.random();
		        String operationsJson = scaleJSON.getOperations().stream()
		            .map(operation -> {
		                // Generate the necessary properties for each operation
		                String operationJson = "{\"idOperation\":\"" + operation.getId().toString() + "\", " +
		                        "\"cargo\":\"" + operation.getCargo().toString() + "\", " +
		                        "\"operationType\":\"" + operation.getType().toString() + "\", " +
		                        "\"quantity\":\"" + operation.getQuantity().getValue() + "\"}";
					
		                return operationJson;
		            })
		            .collect(Collectors.joining(", "));
				
		        String scaleJson = "{\"idScale\":\"" + scaleJSON.getId().toString() + "\", " +
		                "\"idVessel\":\"" + id.toString() + "\", " +
		                "\"startingTime\":\"" + TruckUtils.localDateTimeToString(scaleJSON.getStartingTime()) + "\", " +
		                "\"finishingTime\":\"" + TruckUtils.localDateTimeToString(scaleJSON.getFinishingTime()) + "\", " +
		                "\"operations\":[" + operationsJson + "]}";
				
		        return scaleJson;
		    })
		    .collect(Collectors.joining(", "));
		
		String json = "{\"idVessel\":\"" + id.toString() + "\", " +
		        "\"scales\":[" + scalesJson + "]}";

		HttpRequest request = requestBuilder.copy()
				.method("PUT", HttpRequest.BodyPublishers.ofString(json))
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

	public static void deleteOne(UUID id) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL + "/" + id))
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

	public static Vessel deserialize(JSONObject json) {
		Vessel vessel = new Vessel();
		UUID id = UUID.fromString(json.getString("idVessel"));
		vessel.setId(id);
		JSONArray scalesJSON = json.getJSONArray("scales");
		List<Scale> scales = new ArrayList<>();
		for (int i = 0; i < scalesJSON.length(); i++) {
			Scale scale = ScaleLoader.deserialize(scalesJSON.getJSONObject(i));
			scales.add(scale);
		}
		vessel.setScales(scales);
		return vessel;
	}
}
