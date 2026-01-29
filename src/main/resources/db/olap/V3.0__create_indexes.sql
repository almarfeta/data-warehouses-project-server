CREATE BITMAP INDEX idx_month_year
    ON dim_time (month, year);

CREATE BITMAP INDEX idx_fs_status
    ON fact_sales (status);

EXPLAIN PLAN FOR
SELECT
    t.year,
    t.month_name,
    COUNT(*) AS number_of_sales,
    SUM(f.unit_price * f.quantity) AS total_sales
FROM fact_sales f
JOIN dim_time t ON f.time_key = t.time_key
WHERE t.year = 2026
  AND t.month = 1
  AND f.status = 'ACCEPTED'
GROUP BY t.year, t.month, t.month_name
ORDER BY t.year, t.month;

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);