-- Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number, id, total_mem
FROM host_info
GROUP BY cpu_number, id
ORDER BY cpu_number ASC, total_mem DESC;

-- Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory).
-- helper functions
CREATE FUNCTION round_ts_5min(ts TIMESTAMP) RETURNS timestamp AS
    $$
BEGIN
RETURN date_trunc('hour', ts) + date_part('minute', ts)::INT / 5 * INTERVAL '5 min';
END;
    $$
LANGUAGE PLPGSQL;
END;

CREATE FUNCTION used_memory_pcnt(total_mem BIGINT, free_mem INTEGER) RETURNS REAL AS
    $$BEGIN RETURN ((total_mem::DOUBLE PRECISION - free_mem) / total_mem) * 100; END;$$
LANGUAGE PLPGSQL;
END;

SELECT time_interval_table.host_id host_id,
       time_interval_table.hostname hostname,
       time_interval_table.timestamp_5min_intervals "timestamp",
       time_interval_table.avg_used_memory_percent avg_used_memory_percent
FROM (
         SELECT u.host_id,
                i.hostname,
                round_ts_5min(u.timestamp) AS timestamp_5min_intervals,
                avg( used_memory_pcnt(i.total_mem, u.memory_free*1024))
                                              OVER( PARTITION BY u.host_id, round_ts_5min(u.timestamp) )
                    AS avg_used_memory_percent
         FROM host_usage u INNER JOIN host_info i ON u.host_id = i.id
     ) AS time_interval_table
GROUP BY host_id, hostname, "timestamp", avg_used_memory_percent
ORDER BY host_id , "timestamp";

-- The cron job is supposed to insert a new entry to the host_usage table every minute when the server is healthy.
-- We can assume that a server is failed when it inserts less than three data points within 5-min interval.
-- Please write a query to detect host failures.

SELECT data_points.host_id host_id, data_points.ts ts, data_points.num_data_points num_data_points
FROM (
         SELECT u.host_id host_id, round_ts_5min(u.timestamp) ts,
                count(*) OVER (PARTITION BY u.host_id, round_ts_5min(u.timestamp)) AS num_data_points
         FROM host_usage u
         ) AS data_points
GROUP BY host_id, ts, num_data_points
ORDER BY host_id, ts, num_data_points;
