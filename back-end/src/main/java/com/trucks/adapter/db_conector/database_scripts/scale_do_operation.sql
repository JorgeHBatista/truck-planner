CREATE TABLE IF NOT EXISTS scale_do_operation (
  id_scale varchar(36) NOT NULL,
  id_operation varchar(36) NOT NULL,
  PRIMARY KEY (id_scale, id_operation),
  FOREIGN KEY (id_scale) REFERENCES scale(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (id_operation) REFERENCES operation(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);