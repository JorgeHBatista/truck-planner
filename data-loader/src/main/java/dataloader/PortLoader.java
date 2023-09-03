package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

import domain.entity.Port;
import domain.valueobject.PortName;
import domain.valueobject.Coordinate;

public class PortLoader {

  private static final String ENDPOINT_URL = "http://localhost:8080/ports";

  	public static void insert(int numberOfPorts) throws JsonProcessingException {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
    	    .uri(URI.create(ENDPOINT_URL))
    	    .header("Content-Type", "application/json")
    	    .POST(HttpRequest.BodyPublishers.ofString(""));
    	for (int i = 0; i < numberOfPorts; i++) {
    	    Port port = Port.random();
					
    	    String json = "{\"idPort\":\"" + port.getId().toString() + "\", " +
					"\"name\":\"" + port.getName().getPortName() + "\", " +
					"\"coordinates\":\"" + port.getCoordinate().toString() + "\"}\"";
					
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

	public static void update(UUID id) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT_URL + "/" + id))
    	    	.header("Content-Type", "application/json")
    	    	.PUT(HttpRequest.BodyPublishers.ofString(""));

		Port port = Port.random();
    	String json = "{\"idPort\":\"" + port.getId().toString() + "\", " +
				"\"name\":\"" + port.getName().getPortName() + "\", " +
				"\"coordinates\":\"" + port.getCoordinate().toString() + "\"}\"";

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

	public static Port deserialize(JSONObject json) {
		UUID id = UUID.fromString(json.getString("idPort"));
		String name = json.getString("name");
        PortName portName = new PortName(name);
		String coordinate = json.getString("coordinates");
		double latitude = Double.parseDouble(coordinate.split(", ")[0]);
        double longitude = Double.parseDouble(coordinate.split(", ")[1]);
        Coordinate coord = new Coordinate(latitude, longitude);
		Port port = new Port();
		port.setId(id);
		port.setName(portName);
		port.setCoordinate(coord);
		return port;
	}
}