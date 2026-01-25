ALTER TABLE fact_sales
    MODIFY PARTITION BY RANGE (time_key) (
        PARTITION p_january_2026 VALUES LESS THAN (32), -- 32 = 2026-02-01
        PARTITION p_february_2026 VALUES LESS THAN (60), -- 60 = 2026-03-01
        PARTITION p_march_2026 VALUES LESS THAN (91), -- 91 = 2026-04-01
        PARTITION p_future VALUES LESS THAN (MAXVALUE)
        );

-- TODO: Maybe add another partition for another table