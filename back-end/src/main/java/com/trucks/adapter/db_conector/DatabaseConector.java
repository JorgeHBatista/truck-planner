package com.trucks.adapter.db_conector;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import org.apache.ibatis.jdbc.ScriptRunner;

@Component
public class DatabaseConector implements ApplicationRunner {
    
	private Connection conn; 

    @Override
    public void run(ApplicationArguments args) throws Exception{
		boolean created = checkConnection();
		if (created) System.out.println("Connection created succesfully!"); 
			if (openConnection()) {
				System.out.println("Connection established succesfully!"); 
				createTables();
				setTimeZone();
			}
    }

	private boolean checkConnection() {
		try {
			if (conn != null){
				System.out.println("Database alredy exists, connecting to it...");
				return false;
			}
			String connUrl = "jdbc:postgresql://timescaledb:5432/truckplanner?user=postgres&password=password"; 	
					DriverManager.getConnection(connUrl);
			
		} catch (SQLException e) {
			
			System.err.println("Database not found");
			System.err.println(e.getMessage());
			System.out.println("Creating database truckplanner");
			try {
				String db_url = "jdbc:postgresql://timescaledb:5432/";
				String user = "postgres";
				String password = "password";
				conn = DriverManager.getConnection(db_url, user, password);
				String createSensorTableQuery = "CREATE DATABASE truckplanner;";
        		try (Statement stmt = conn.createStatement()) {
            		stmt.execute(createSensorTableQuery);
					System.out.println("Database created succesfully!");
					closeConnection();
					return true;
        		} catch	(SQLException e3) {
					System.err.println("Not able to create database");
					System.err.println(e3.getMessage());
				}
			} catch (SQLException e2) {
				System.err.println("Unexpected SQL error");
				System.err.println(e2.getMessage());
			}
		}
		return false;
	}

	private boolean openConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				String connUrl = "jdbc:postgresql://timescaledb:5432/truckplanner?user=postgres&password=password";
        		conn = DriverManager.getConnection(connUrl);
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.err.println("Unable to connect to database");
			System.err.println(e.getMessage());
			return false;
		}
	}

	private boolean setTimeZone() {
		String createSensorTableQuery = "SET timezone = 'Atlantic/Canary';";
    	try (Statement stmt = conn.createStatement()) {
      		stmt.execute(createSensorTableQuery);
			System.out.println("Time zone setted succesfully!");
			return true;
    	} catch	(SQLException e3) {
			System.err.println("Not able to set time zone");
			System.err.println(e3.getMessage());
			return false;
		}
	}

	private boolean closeConnection() {
		try {
			if(conn != null) {
				conn.close();
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.err.println("Error when trying to close the connection");
			System.err.println(e.getMessage());
			return false;
		}
	}

	private void createTables() {
		createTableWithRoute("/database_scripts/truck.sql"); // truck
		createTableWithRoute("/database_scripts/port.sql"); // port
		createTableWithRoute("/database_scripts/operation.sql"); // operation
		createTableWithRoute("/database_scripts/truck_access_port.sql"); // truck_access_port
		createTableWithRoute("/database_scripts/vessel.sql"); // vessel
		createTableWithRoute("/database_scripts/vessel_berth_in_port.sql"); // vessel_berth_in_port_with_scale
		createTableWithRoute("/database_scripts/scale.sql"); // scale
		createTableWithRoute("/database_scripts/scale_do_operation.sql"); // scale_do_operation
		createTableWithRoute("/database_scripts/vessel_do_scale.sql"); // vessel_do_scale
	}

	private void createTableWithRoute(String route) {
		try {
			Reader reader = new BufferedReader(new FileReader(route));
			ScriptRunner sr = new ScriptRunner(conn);
			sr.runScript(reader);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.err.println(e.getMessage());
		} catch (Exception e2) {
			System.err.println("SQL error");
			System.err.println(e2.getMessage());
		}
	}
}