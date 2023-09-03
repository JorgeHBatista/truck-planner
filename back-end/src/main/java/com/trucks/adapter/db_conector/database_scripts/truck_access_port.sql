CREATE TABLE IF NOT EXISTS truck_access_port (
  id varchar(36) NOT NULL,
  id_truck varchar(36),
  id_port varchar(36),
  entry_date TIMESTAMP,
  exit_date TIMESTAMP,
  entry_point varchar(10),
  exit_point varchar(10),
  identification_type varchar(10),
  PRIMARY KEY (id, id_truck, id_port),
  FOREIGN KEY (id_truck) REFERENCES truck(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (id_port) REFERENCES port(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);