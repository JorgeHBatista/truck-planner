package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.TruckUtils;

import domain.entity.Berth;
import domain.entity.Port;
import domain.entity.Vessel;

public class BerthLoader {

  	private static final String ENDPOINT_URL = "http://localhost:8080/berths";

  	public static void insert(int numberOfBerths) throws JsonProcessingException {
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
    	    .uri(URI.create(ENDPOINT_URL))
    	    .header("Content-Type", "application/json")
    	    .POST(HttpRequest.BodyPublishers.ofString(""));

		String stringPorts = PortLoader.findAll();
		String stringVessels = VesselLoader.findAll();
		JSONArray ports = new JSONObject(stringPorts).getJSONArray("content");
		JSONArray vessels = new JSONObject(stringVessels).getJSONArray("content");
    	for (int i = 0; i < numberOfBerths; i++) {
    	    Berth berth = Berth.random();
			Port port = PortLoader.deserialize(ports.getJSONObject(i));
			Vessel vessel = VesselLoader.deserialize(vessels.getJSONObject(i));
					
    	    String json = "{\"idBerth\":\"" + berth.getId().toString() + "\", " +
					"\"idPort\":\"" + port.getId().toString() + "\", " +
					"\"idVessel\":\"" + vessel.getId().toString() + "\"}\"";
					
    	    HttpRequest request = requestBuilder.copy()
    	        .method("POST", HttpRequest.BodyPublishers.ofString(json))
    	        .build();
    	    try {
    	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    	        System.out.println("Request #" + (i) + ":");
				System.out.println("\t" + json);
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

		String stringPorts = PortLoader.findAll();
		String stringVessels = VesselLoader.findAll();
		JSONArray portsJSON = new JSONObject(stringPorts).getJSONArray("content");
		JSONArray vesselsJSON = new JSONObject(stringVessels).getJSONArray("content");

		int numberOfBerths = TruckUtils.generateRandomNumber(1);
		Random random = new Random();
		int randomIndexPorts = random.nextInt(Math.abs(portsJSON.length() - numberOfBerths));
		int randomIndexVessels = random.nextInt(Math.abs(vesselsJSON.length() - numberOfBerths));
		Berth berth = Berth.random();
		Port port = PortLoader.deserialize(portsJSON.getJSONObject(randomIndexPorts));
		Vessel vessel = VesselLoader.deserialize(vesselsJSON.getJSONObject(randomIndexVessels));
				
		String json = "{\"idBerth\":\"" + id.toString() + "\", " +
				"\"idPort\":\"" + port.getId().toString() + "\", " +
				"\"idVessel\":\"" + vessel.getId().toString() + "\"}\"";
				
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

	public static Berth deserialize(JSONObject json) {
		Berth berth = new Berth();
		berth.setId(UUID.fromString(json.getString("idBerth")));
		return berth;
	}
}
