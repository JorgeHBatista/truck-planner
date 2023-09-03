CREATE TABLE IF NOT EXISTS vessel_do_scale (
  id_vessel varchar(36) NOT NULL,
  id_scale varchar(36) NOT NULL,
  PRIMARY KEY (id_vessel, id_scale),
  FOREIGN KEY (id_vessel) REFERENCES vessel(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (id_scale) REFERENCES scale(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);