package es.ull.application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import es.ull.domain.entity.Access;
import es.ull.domain.entity.Truck;
import es.ull.domain.entity.Port;
import es.ull.utils.TruckUtils;
import es.ull.domain.entity.Timestamp;
import es.ull.domain.valueobject.Plate;
import es.ull.domain.valueobject.Brand;
import es.ull.domain.valueobject.Driver;
import es.ull.domain.valueobject.PortName;
import es.ull.domain.valueobject.Coordinate;

public class AccessGenerator {
    
    private int numberOfStartingAccesses;
    private LocalDateTime entryDate = LocalDateTime.now();
    private LocalDateTime exitDate = LocalDateTime.now();
    private String entryPoint;
    private String exitPoint;
    private String identificationType;
    private Access[] accesses;
    private Truck[] trucks;
    private Port[] ports;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ScheduledExecutorService executor;
    private int numberOfMessagesSent;
    private int period;
    private long timeFactor;
    private Timestamp startingTime;
    private Timestamp currentTime;
    private Timestamp endingTime;

    @Autowired
    private OutputPort outputPort;

    public AccessGenerator(
            int numberOfStartingAccesses,
            String entryDate,
            String exitDate,
            String entryPoint,
            String exitPoint, 
            String identificationType,
            int period,
            long timeFactor,
            String startingDateParam,
            String endingDateParam
            ) throws ParseException {

        this.numberOfStartingAccesses = numberOfStartingAccesses;
        this.entryDate = LocalDateTime.parse(entryDate, this.formatter);
        this.exitDate = LocalDateTime.parse(exitDate, this.formatter);
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        this.identificationType = identificationType;
        this.accesses = new Access[numberOfStartingAccesses];
        this.period = period;
        this.timeFactor = timeFactor;

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startingDate = dateFormat.parse(startingDateParam);
        Date endingDate = dateFormat.parse(endingDateParam);
        this.startingTime = new Timestamp(startingDate.getTime());
        this.endingTime = new Timestamp(endingDate.getTime());
        this.currentTime = (Timestamp) this.startingTime.clone();

        this.logStatus();

        this.executor = Executors.newScheduledThreadPool(1);
        
        Runnable task = () -> {
            this.currentTime.addSeconds(timeFactor);
            this.stopExecutionIfEndingTime();
            for (int i = 0; i < this.numberOfStartingAccesses; i++) {
                this.getAcceses();
                Truck truck = Truck.random();
                Port port = Port.random();
                String plate = truck.getPlate().getPlate().toString();
                String portName = port.getName().getPortName().toString();
                
                String entryDateString = TruckUtils.localDateTimeToString(accesses[i].getEntryDate());
                String exitDateString = TruckUtils.localDateTimeToString(accesses[i].getExitDate());
                String entryPointString = this.entryPoint;
                String exitPointString = this.exitPoint;
                String identificationTypeString = this.identificationType;

                String json = "{"
                    + "\"plate\": \"" + plate + "\","
                    + "\"portName\": \"" + portName + "\","
                    + "\"entryDate\": \"" + entryDateString + "\","
                    + "\"exitDate\": \"" + exitDateString + "\","
                    + "\"entryPoint\": \"" + entryPointString + "\","
                    + "\"exitPoint\": \"" + exitPointString + "\","
                    + "\"identificationType\": \"" + identificationTypeString + "\""
                    + "}";
                this.notifySubscribers(json);
            }
            this.logStatus();
        };
        executor.scheduleAtFixedRate(task, 0, this.period, TimeUnit.SECONDS);
    }

    private void getAcceses() {
        for (int i = 0; i < this.numberOfStartingAccesses; i++) {
            this.addnewRandomAccess(i);
        }
    }

    private Access addnewRandomAccess(int index) {        
        Access access = Access.random();
        return this.addnewRandomAccess(access, index);
    }

    private Access addnewRandomAccess(Access access, int index) {        
        this.accesses[index] = access;
        return access;
    }

    private void stopExecutionIfEndingTime() {
        if ((this.currentTime.getTime() - this.endingTime.getTime()) >= 0) {
            this.logStatus();
            System.out.println("Simulation finished because the ending time has been achieved");
            this.executor.shutdown();
        }
    }

    private void logStatus() {
        System.out.println("Current time: " + this.currentTime);
        System.out.println("Messages: " + this.numberOfMessagesSent);
    }

    private Truck getRandomTruck() {
        Random r = new Random();
        int index = r.nextInt(this.trucks.length);
        return this.trucks[index];
    }

    private Port getRandomPort() {
        Random r = new Random();
        int index = r.nextInt(this.ports.length);
        return this.ports[index];
    }

    private void notifySubscribers(String json) {
        if (Objects.isNull(this.outputPort)) {
            System.out.println("There is no output port to send the event");
            return;
        }
        try {
            outputPort.sendEvent(json);
            System.out.println(json);
            this.numberOfMessagesSent++;
        } catch (Exception e) {
            System.err.println("It was not possible to send event associated with truck=" + json);
            e.printStackTrace();
        }
    }
}
