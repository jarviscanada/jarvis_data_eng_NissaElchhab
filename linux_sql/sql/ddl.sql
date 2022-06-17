-- Data Definition file for host_agent table

CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id               SERIAL       NOT NULL,
    hostname         VARCHAR(256) NOT NULL,
    cpu_number       INTEGER      NOT NULL,
    cpu_architecture VARCHAR      NOT NULL,
    cpu_model        VARCHAR      NOT NULL,
    cpu_mhz          REAL         NOT NULL,
    L2_cache         INTEGER      NOT NULL,
    total_mem        INTEGER      NOT NULL,
    "timestamp"      TIMESTAMP    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (hostname),
    CONSTRAINT valid_discrete_value CHECK ( cpu_number > 0 AND L2_cache >= 0 AND total_mem >= 0 ),
    CONSTRAINT valid_frequency CHECK ( cpu_mhz >= 0.0 AND cpu_mhz <= 10000.0 )
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    host_id        INTEGER   NOT NULL,
    memory_free    INTEGER   NOT NULL,
    cpu_idle       INTEGER   NOT NULL,
    cpu_kernel     INTEGER   NOT NULL,
    disk_io        INTEGER   NOT NULL,
    disk_available INTEGER   NOT NULL,
    "timestamp"    TIMESTAMP NOT NULL,
    FOREIGN KEY (host_id)
    REFERENCES host_info (id)
    ON DELETE SET NULL,
    CONSTRAINT valid_discrete_values CHECK ( memory_free >= 0 AND disk_io >= 0 AND disk_available >= 0),
    CONSTRAINT valid_percentage CHECK ( (cpu_idle BETWEEN 0 AND 100) AND (cpu_kernel BETWEEN 0 AND 100))
);
