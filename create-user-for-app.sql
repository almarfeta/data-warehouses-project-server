-- Run as SYSTEM

-- Change the container to the root one (Default)
ALTER SESSION SET CONTAINER = CDB$ROOT;

-- See if you are on the root container or not
SELECT sys_context('USERENV', 'CON_NAME') AS current_container FROM dual;

-- See all the PDBs
SELECT name FROM v$pdbs;

-- Connect to a PDB
ALTER SESSION SET CONTAINER = ORCLPDB1;

-- Create a Local User on that PDB and give him privileges to create stuff
CREATE USER app_oltp_user IDENTIFIED BY Parola1234;
ALTER USER app_oltp_user QUOTA UNLIMITED ON USERS;
GRANT CONNECT, RESOURCE TO app_oltp_user;

-- Verify
SELECT username, common FROM dba_users WHERE username = 'APP_OLTP_USER';