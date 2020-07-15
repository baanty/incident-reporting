 
INSERT INTO ADDRESS (
	ID, 
	STREET_NAME, 
	CITY) VALUES
  (1, 'Eemnesserweg', 'Hilversum'),
  (2, 'Seinstraat', 'Hilversum'),
  (3, 'Rasselstraat', 'Bussum');

  
INSERT INTO CUSTOMER (
	ID, 
	FIRST_NAME, 
	LAST_NAME,
	AGE,
	ADDRESS_ID) VALUES
  (1, 'Pijush K', 'Das', 99, 1),
  (2, 'Mijndert', 'Rebel', 45, 2),
  (3, 'Paul', 'Stevens', 49, 3);
  
  
  
COMMIT;