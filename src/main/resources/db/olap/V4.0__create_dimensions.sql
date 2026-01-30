CREATE DIMENSION time_dim
    LEVEL day_lvl IS (dim_time.time_key)
    LEVEL month_lvl IS (dim_time.month, dim_time.year)
    LEVEL year_lvl IS (dim_time.year)
    HIERARCHY h_calendar (
        day_lvl CHILD OF
        month_lvl CHILD OF
        year_lvl
        )
    ATTRIBUTE day_lvl DETERMINES (dim_time.date_value, dim_time.day, dim_time.week_day)
    ATTRIBUTE month_lvl DETERMINES (dim_time.month_name);

CREATE DIMENSION location_dim
    LEVEL city_lvl IS (dim_location.city, dim_location.region, dim_location.country)
    LEVEL region_lvl IS (dim_location.region, dim_location.country)
    LEVEL country_lvl IS (dim_location.country)
    HIERARCHY h_geography (
        city_lvl CHILD OF
        region_lvl CHILD OF
        country_lvl
        );

-- Validate dimensions
BEGIN
    DBMS_DIMENSION.VALIDATE_DIMENSION('time_dim', FALSE, TRUE, 'validation_1');
    DBMS_DIMENSION.VALIDATE_DIMENSION('location_dim', FALSE, TRUE, 'validation_2');
END;
/
-- See validation results
SELECT * FROM DIMENSION_EXCEPTIONS;

-- Helper command to list all dimensions
select * from USER_DIMENSIONS;

-- Compile dimension if needed
ALTER DIMENSION location_dim COMPILE;
