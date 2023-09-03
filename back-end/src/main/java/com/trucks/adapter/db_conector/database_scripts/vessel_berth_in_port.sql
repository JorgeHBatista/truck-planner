CREATE TABLE IF NOT EXISTS vessel_berth_in_port_with_scale (
  id varchar(36),
  id_port varchar(36),
  id_vessel varchar(36),
  PRIMARY KEY (id, id_port, id_vessel),
  FOREIGN KEY (id_port) REFERENCES port(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (id_vessel) REFERENCES vessel(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);