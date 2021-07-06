CREATE USER 'demo'@'%';
ALTER USER 'demo'@'%'
IDENTIFIED BY 'demo1234' ;
GRANT All ON demo.* TO 'demo'@'%';