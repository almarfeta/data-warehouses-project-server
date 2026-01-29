ALTER TABLE fact_sales
    MODIFY PARTITION BY RANGE (time_key) (
        PARTITION p_january_2026 VALUES LESS THAN (32), -- 32 = 2026-02-01
        PARTITION p_february_2026 VALUES LESS THAN (60), -- 60 = 2026-03-01
        PARTITION p_march_2026 VALUES LESS THAN (91), -- 91 = 2026-04-01
        PARTITION p_future VALUES LESS THAN (MAXVALUE)
        );

ALTER TABLE dim_location
    MODIFY PARTITION BY LIST (country) (
        PARTITION p_north_europe VALUES ('Finland'),
        PARTITION p_west_europe VALUES ('Belgium', 'France', 'Netherlands'),
        PARTITION p_central_europe VALUES ('Germany', 'Austria', 'Romania'),
        PARTITION p_south_europe VALUES ('Spain', 'Italy', 'Greece'),
        PARTITION p_america VALUES ('USA'),
        PARTITION p_other VALUES (DEFAULT)
        );

EXPLAIN PLAN FOR
SELECT
    l.country,
    t.month_name,
    t.year,
    SUM(f.unit_price * f.quantity) AS total_sales
FROM fact_sales f
JOIN dim_time t ON f.time_key = t.time_key
JOIN dim_location l ON f.location_key = l.location_key
WHERE f.time_key < 32
  AND l.country = 'Romania'
GROUP BY l.country, t.month_name, t.year;

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);