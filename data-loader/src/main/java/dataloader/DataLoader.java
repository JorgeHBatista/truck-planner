package dataloader;

import java.util.Scanner;
import dataloader.TruckLoader;
import dataloader.PortLoader;
import dataloader.VesselLoader;
import dataloader.AccessLoader;
import dataloader.BerthLoader;
import dataloader.ScaleLoader;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DataLoader {

  	public static void main(String[] args) {
  	  while (true) {
  	    System.out.println("\nTruck planner: data loader"); 
  	    System.out.println("\tURL = http://localhost:8080/");
  	    System.out.println("Please select an option:");
		System.out.println("\t1. Insert data");
		System.out.println("\t2. Get data");
		System.out.println("\t3. Update data");
		System.out.println("\t4. Delete data");
		System.out.println("\t99. Exit");
		Scanner sc = new Scanner(System.in);
  	    int option = sc.nextInt();
		sc.nextLine();
  	    switch (option) {
  	      case 1:
			DataLoader.insertData();
  	        break;
  	      case 2:
  	        DataLoader.getData();
  	        break;
  	      case 3:
  	        DataLoader.updateData();
  	        break;
  	      case 4:
  	        DataLoader.deleteData();
  	        break;
  	      case 99:
  	        System.out.println("Closing the program...");
  	        System.exit(0);
  	        break;
  	      default:
  	        System.out.println("Invalid option not implemented");
  	        System.exit(-1);
  	        break;
  	    }
  	  }
  	}

  	private static void insertData() {
  	    System.out.println("\nPlease select an option:");
  	    System.out.println("\t1. Insert trucks");
  	    System.out.println("\t2. Insert ports");
  	    System.out.println("\t3. Insert operations");
  	    System.out.println("\t4. Insert scales");
  	    System.out.println("\t5. Insert accesses");
  	    System.out.println("\t6. Insert vessels");
  	    System.out.println("\t7. Insert berths");
  	    System.out.println("\t99. Exit");
  	    Scanner sc = new Scanner(System.in);
  	    int option = sc.nextInt();
		sc.nextLine();
		try {
  	    	switch (option) {
  	    	  case 1:
				int NUMBER_OF_TRUCKS = 50;
  	    	    System.out.println("Inserting " + NUMBER_OF_TRUCKS + " trucks");
  	    	    TruckLoader.insert(50);
  	    	    break;
  	    	  case 2:
				int NUMBER_OF_PORTS = 50;
  	        	System.out.println("Inserting " + NUMBER_OF_PORTS + " ports");
  	        	PortLoader.insert(50);
  	    	    break;
  	    	  case 3:
				int NUMBER_OF_OPERATIONS = 50;
  	        	System.out.println("Inserting " + NUMBER_OF_OPERATIONS + " operations");
  	        	OperationLoader.insert(50);
  	    	    break;
			  case 4:
				int NUMBER_OF_SCALES = 50;
				System.out.println("Inserting " + NUMBER_OF_SCALES + " scales");
				ScaleLoader.insert(50);
  	    	    break;
  	    	  case 5:
				int NUMBER_OF_ACCESSES = 50;
  	        	System.out.println("Inserting " + NUMBER_OF_ACCESSES + " accesses");
  	        	AccessLoader.insert(50);
  	    	    break;
  	    	  case 6:
				int NUMBER_OF_VESSELS = 50;
				System.out.println("Inserting " + NUMBER_OF_VESSELS + " vessels");
				VesselLoader.insert(50);
  	    	    break;
			  case 7:
				int NUMBER_OF_BERTHS = 50;
				System.out.println("Inserting " + NUMBER_OF_BERTHS + " berths");
				BerthLoader.insert(50);
  	    	    break;
  	    	  case 99:
  	    	    System.out.println("Going back to the menu...");
  	    	    return;
  	    	  default:
  	    	    System.out.println("Invalid option not implemented");
  	    	    System.out.println("Going back to the menu...");
  	    	    return;
  	    	}
		} catch (JsonProcessingException e) {
			System.out.println("Error: " + e.getMessage());
		}
  	}

	private static void getData() {
		System.out.println("\nPlease select an option:");
		System.out.println("\t1. GET a truck");
		System.out.println("\t2. GET all trucks");
		System.out.println("\t3. GET a port");
		System.out.println("\t4. GET all ports");
		System.out.println("\t5. GET a operation");
		System.out.println("\t6. GET all operations");
		System.out.println("\t7. GET all scales");
		System.out.println("\t8. GET all accesses");
		System.out.println("\t9. GET a vessel");
		System.out.println("\t10. GET all vessels");
		System.out.println("\t11. GET a berth");
		System.out.println("\t12. GET all berths");
		System.out.println("\t99. Exit");
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		sc.nextLine();
		String id = "";
		UUID uuid = null;
		switch (option) {
		  case 1:
			System.out.println("Get a truck, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			TruckLoader.find(uuid);
			break;
		  case 2:
			System.out.println("Getting all the trucks");
			TruckLoader.findAll();
			break;
		  case 3:
			System.out.println("Get a port, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			PortLoader.find(uuid);
			break;
		  case 4:
			System.out.println("Getting all the ports");
			PortLoader.findAll();
			break;
		  case 5:
			System.out.println("Get a operation, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			OperationLoader.find(uuid);
			break;
		  case 6:
			System.out.println("Getting all the operations");
			OperationLoader.findAll();
			break;
		  case 7:
		  	System.out.println("Getting all the scales");
		  	ScaleLoader.findAll();
			break;
		  case 8:
			System.out.println("Getting all the accesses");
			AccessLoader.findAll();
			break;
		  case 9:
		  	System.out.println("Get a vessel, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
		  	VesselLoader.find(uuid);
			break;
		  case 10:
		  	System.out.println("Getting all the vessels");
		  	VesselLoader.findAll();
			break;
		  case 11:
		  	System.out.println("Get a berth");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
		  	BerthLoader.find(uuid);
			break;
		  case 12:
		  	System.out.println("Getting all the berths");
		  	BerthLoader.findAll();
			break;
		  case 99:
			System.out.println("Going back to the menu...");
			return;
		  default:
			System.out.println("Invalid option not implemented");
			System.out.println("Going back to the menu...");
			return;
		}
	}

	private static void updateData() {
		System.out.println("\nPlease select an option:");
		System.out.println("\t1. UPDATE a truck");
		System.out.println("\t2. UPDATE a port");
		System.out.println("\t3. UPDATE a operation");
		System.out.println("\t4. UPDATE a vessel");
		System.out.println("\t5. UPDATE a berth");
		System.out.println("\t99. Exit");
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		sc.nextLine();
		String id = "";
		UUID uuid = null;
		switch (option) {
		  case 1:
			System.out.println("Updating a truck, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			TruckLoader.update(uuid);
			break;
		  case 2:
			System.out.println("Updating a port, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			PortLoader.update(uuid);
			break;
		  case 3:
			System.out.println("Updating a operation, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			OperationLoader.update(uuid);
			break;
		  case 4:
			System.out.println("Updating a vessel, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			VesselLoader.update(uuid);
			break;
		  case 5:
			System.out.println("Updating a berth, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			BerthLoader.update(uuid);
			break;
		  case 99:
			System.out.println("Going back to the menu...");
			return;
		  default:
			System.out.println("Invalid option not implemented");
			System.out.println("Going back to the menu...");
			return;
		}
	}

	private static void deleteData() {
		System.out.println("\nPlease select an option:");
		System.out.println("\t1. DELETE a truck");
		System.out.println("\t2. DELETE all trucks");
		System.out.println("\t3. DELETE a port");
		System.out.println("\t4. DELETE all ports");
		System.out.println("\t5. DELETE a operation");
		System.out.println("\t6. DELETE all operations");
		System.out.println("\t7. DELETE all scales");
		System.out.println("\t8. DELETE all accesses");
		System.out.println("\t9. DELETE a vessel");
		System.out.println("\t10. DELETE all vessels");
		System.out.println("\t11. DELETE a berth");
		System.out.println("\t12. DELETE all berths");
		System.out.println("\t99. Exit");
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		sc.nextLine();
		String id = "";
		UUID uuid = null;
		switch (option) {
		  case 1:
			System.out.println("Deleting a truck, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			TruckLoader.deleteOne(uuid);
			break;
		  case 2:
			System.out.println("Deleting all trucks");
			TruckLoader.deleteAll();
			break;
		  case 3:
			System.out.println("Deleting a port, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			PortLoader.deleteOne(uuid);
			break;
		  case 4:
			System.out.println("Deleting all ports");
			PortLoader.deleteAll();
			break;
		  case 5:
			System.out.println("Deleting a operation, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			OperationLoader.deleteOne(uuid);
			break;
		  case 6:
			System.out.println("Deleting all operations");
			OperationLoader.deleteAll();
			break;
		  case 7:
			System.out.println("Deleting all scales");
			ScaleLoader.deleteAll();
		    break;
		  case 8:
		    System.out.println("Deleting all accesses");
		    AccessLoader.deleteAll();
		    break;
		  case 9:
			System.out.println("Deleting a vessel, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			VesselLoader.deleteOne(uuid);
			break;
		  case 10:
		  	System.out.println("Deleting all vessels");
		  	VesselLoader.deleteAll();
			break;
		  case 11:
			System.out.println("Deleting a berth, please insert the id:");
			id = sc.nextLine();
			uuid = UUID.fromString(id);
			BerthLoader.deleteOne(uuid);
			break;
		  case 12:
		  	System.out.println("Deleting all berths");
		  	BerthLoader.deleteAll();
			break;

		  case 99:
			System.out.println("Going back to the menu...");
			return;
		  default:
			System.out.println("Invalid option not implemented");
			System.out.println("Going back to the menu...");
			return;
		}
	}
}