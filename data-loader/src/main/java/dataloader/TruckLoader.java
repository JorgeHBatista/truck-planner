package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

import domain.entity.Truck;
import domain.valueobject.Plate;
import domain.valueobject.Brand;
import domain.valueobject.Driver;

public class TruckLoader {

  	private static final String ENDPOINT_URL = "http://localhost:8080/trucks";

  	public static void insert(int numberOfTrucks) throws JsonProcessingException {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
    	    .uri(URI.create(ENDPOINT_URL))
    	    .header("Content-Type", "application/json")
    	    .POST(HttpRequest.BodyPublishers.ofString(""));
    	for (int i = 0; i < numberOfTrucks; i++) {
    	    Truck truck = Truck.random();
					
    	    String json = "{\"idTruck\":\"" + truck.getId().toString() + "\", " +
					"\"plate\":\"" + truck.getPlate().getPlate() + "\", " +
					"\"brand\":\"" + truck.getBrand().getBrand() + "\", " +
					"\"driver\":\"" + truck.getDriver().getDriver() + "\", " +
					"\"unload\":" + truck.isUnload() + "}\"";
					
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

		Truck truck = Truck.random();
    	String json = "{\"idTruck\":\"" + truck.getId().toString() + "\", " +
				"\"plate\":\"" + truck.getPlate().getPlate() + "\", " +
				"\"brand\":\"" + truck.getBrand().getBrand() + "\", " +
				"\"driver\":\"" + truck.getDriver().getDriver() + "\", " +
				"\"unload\":" + truck.isUnload() + "}\"";

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

	public static Truck deserialize(JSONObject json) {
		Truck truck = new Truck();
		truck.setId(UUID.fromString(json.getString("idTruck")));
		truck.setPlate(new Plate(json.getString("plate")));
		truck.setBrand(new Brand(json.getString("brand")));
		truck.setDriver(new Driver(json.getString("driver")));
		truck.setUnload(json.getBoolean("unload"));
		return truck;
	}
}
