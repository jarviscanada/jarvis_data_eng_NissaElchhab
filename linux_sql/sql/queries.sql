-- Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number, id, total_mem
FROM host_info
GROUP BY cpu_number, id
ORDER BY cpu_number ASC, total_mem DESC;

-- Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory).
-- helper functions
CREATE FUNCTION round_ts_5min(ts timestamp) RETURNS timestamp AS
    $$
BEGIN
RETURN date_trunc('hour', ts) + date_part('minute', ts)::int / 5 * INTERVAL '5 min';
END;
    $$
LANGUAGE PLPGSQL;
END;
CREATE FUNCTION used_memory_pcnt(total_mem bigint, free_mem integer) RETURNS real AS
    $$BEGIN RETURN ((total_mem::double precision - free_mem) / total_mem) * 100; END;$$
LANGUAGE PLPGSQL;
end;

SELECT host_id, hostname, timestamp_5min_intervals, avg_used_memory_percent
FROM (
         SELECT u.host_id, i.hostname,round_ts_5min(u.timestamp) AS timestamp_5min_intervals,
                avg( used_memory_pcnt(i.total_mem, u.memory_free*1024))
                OVER( PARTITION BY round_ts_5min(u.timestamp) ) as avg_used_memory_percent
         FROM host_usage u INNER JOIN host_info i ON u.host_id = i.id) AS time_interval_table
GROUP BY host_id, hostname, timestamp_5min_intervals, avg_used_memory_percent
ORDER BY host_id , timestamp_5min_intervals ;