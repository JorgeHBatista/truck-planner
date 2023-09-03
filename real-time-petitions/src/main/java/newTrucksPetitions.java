package petitions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

public class newTrucksPetitions {
    
    private static final String ENDPOINT_URL = "http://localhost:8080/trucks";
    
    
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int delay = 0; // Tiempo de espera antes de la primera ejecución en segundos
        int interval = 2; // Intervalo entre ejecuciones en segundos

        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("Realizando petición...");
                try {
                    insert(1);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, delay, interval, TimeUnit.SECONDS);
    }

  	private static void insert(int numberOfTrucks) throws JsonProcessingException {
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
}