package dataloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import domain.entity.Access;
import domain.entity.Truck;
import domain.entity.Port;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.TruckUtils;

public class AccessLoader {
  private static final String ENDPOINT_URL = "http://localhost:8080/access";

    public static void insert(int numberOfAccesss) throws JsonProcessingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(ENDPOINT_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(""));

		String stringTrucks = TruckLoader.findAll();
		String stringPorts = PortLoader.findAll();
		JSONArray trucks = new JSONObject(stringTrucks).getJSONArray("content");
		JSONArray ports = new JSONObject(stringPorts).getJSONArray("content");
        for (int i = 0; i < numberOfAccesss; i++) {
            Access access = Access.random();
        	Truck truck = TruckLoader.deserialize(trucks.getJSONObject(i));
        	Port port = PortLoader.deserialize(ports.getJSONObject(i));

            String json = "{\"idAccess\":\"" + access.getId().toString() + "\", " +
        		"\"idTruck\":\"" + truck.getId().toString() + "\", " +
            "\"plate\":\"" + truck.getPlate().getPlate() + "\", " +
            "\"brand\":\"" + truck.getBrand().getBrand() + "\", " +
            "\"driver\":\"" + truck.getDriver().getDriver() + "\", " +
            "\"unload\":" + truck.isUnload() + ", " +
        		"\"idPort\":\"" + port.getId().toString() + "\", " +
        		"\"name\":\"" + port.getName().getPortName() + "\", " +
        		"\"coordinates\":\"" + port.getCoordinate().toString() + "\", " +
        		"\"entryDate\":\"" + TruckUtils.localDateTimeToString(access.getEntryDate()) + "\", " +
        		"\"exitDate\":\"" + TruckUtils.localDateTimeToString(access.getExitDate()) + "\", " +
        		"\"entryPoint\":\"" + access.getEntryPoint().toString() + "\", " +
        		"\"exitPoint\":\"" + access.getExitPoint().toString() + "\", " +
        		"\"identificationType\":\"" + access.getIdentificationType().toString() + "\"}\"";

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

	public static void findAll() {
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
}