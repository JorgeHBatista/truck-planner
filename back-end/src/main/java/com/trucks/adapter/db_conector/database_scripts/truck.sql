CREATE TABLE IF NOT EXISTS truck (
  id varchar(36) NOT NULL,
  plate varchar(7),
  brand varchar(20),
  driver varchar(50),
  is_unload boolean,
  PRIMARY KEY (id)
);
