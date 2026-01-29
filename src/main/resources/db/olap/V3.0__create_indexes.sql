CREATE BITMAP INDEX idx_month_year
    ON dim_time (month, year);

CREATE BITMAP INDEX idx_fs_status
    ON fact_sales (status);

EXPLAIN PLAN FOR
SELECT /*+ INDEX_COMBINE(f idx_fs_status) */
    p.payment_method,
    COUNT(f.sales_key) AS number_of_sales,
    ROUND(AVG(f.unit_price * f.quantity), 2) AS sales_avg,
    SUM(f.unit_price * f.quantity) AS total_sales
FROM fact_sales f
JOIN dim_payment_method p ON f.payment_method_key = p.payment_method_key
JOIN dim_time t ON f.time_key = t.time_key
WHERE f.status = 'ACCEPTED'
  AND t.year = 2026
  AND t.month BETWEEN 1 AND 3
GROUP BY p.payment_method
ORDER BY total_sales DESC;

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);