CREATE BITMAP INDEX idx_month_year
    ON dim_time (month, year);

CREATE BITMAP INDEX idx_fs_status
    ON fact_sales (status);

-- Verify TODO: Modify this query to also include the second index (if possible)
-- TODO: Afeter adding the partition, the result might change here, so verify and adjust
EXPLAIN PLAN FOR
SELECT
    fs.status,
    SUM(fs.unit_price * fs.quantity) AS total_sales
FROM fact_sales fs
WHERE fs.status = 'ACCEPTED'
GROUP BY fs.status;

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);