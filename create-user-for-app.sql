-- Run as SYSTEM

-- Change the container to the root one (Default)
ALTER SESSION SET CONTAINER = CDB$ROOT;

-- See if you are on the root container or not
SELECT sys_context('USERENV', 'CON_NAME') AS current_container FROM dual;

-- See all the PDBs
SELECT name FROM v$pdbs;

-- Connect to a PDB
ALTER SESSION SET CONTAINER = ORCLPDB1;

-- Create Local Users on that PDB and give them privileges to create stuff
CREATE USER app_oltp_user IDENTIFIED BY Parola1234;
ALTER USER app_oltp_user QUOTA UNLIMITED ON USERS;
GRANT CONNECT, RESOURCE TO app_oltp_user;

CREATE USER app_olap_user IDENTIFIED BY Parola1234;
ALTER USER app_olap_user QUOTA UNLIMITED ON USERS;
GRANT CONNECT, RESOURCE TO app_olap_user;

-- Verify
SELECT username, common, profile FROM dba_users WHERE username = 'APP_OLTP_USER' OR USERNAME = 'APP_OLAP_USER';

-- Utils if password expires/account is locked
ALTER USER app_oltp_user IDENTIFIED BY Parola1234;
ALTER USER app_olap_user IDENTIFIED BY Parola1234;
ALTER USER app_oltp_user ACCOUNT UNLOCK;
ALTER USER app_olap_user ACCOUNT UNLOCK;