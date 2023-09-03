CREATE TABLE IF NOT EXISTS operation (
  id varchar(36) NOT NULL,
  cargo varchar(50),
  type varchar(10),
  quantity int,
  PRIMARY KEY (id)
);