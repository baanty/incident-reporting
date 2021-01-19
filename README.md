# incident-reporting

### Purpose of the Application
The project is a POC for ING internal asset detail extraction. This application is a batch job. It reads a csv from an incoming directory. That CSV file contains asset data. The directory can be a Kafka or XFB destination. For the time being, a local directory is used. 

After reading from the incoming directory the batch inserts the records in database tables. We used in memory database, the H2 for the time being. In reality, it should use a full fledged relational database, like Oracle.

There are two REST endpoints exposed. These two endpoints will let the user download the database asset records. There are two endpoints. One gives all the assets detail to the user. The other one gives the asset error detail to the user.

The last part of the Project is another batch Job. It extracts the daily daily extracted database data into a reformatted CSV file. Then sends the CSV to a certain directory, which is configurable. This directory, in real time will be an XFB destination or Kafka outbound message.  

### Technology Used
The Project is built on the below technology stack
1) Spring Boot - To expose REST endpoints and continue the back end process.
2) Spring Scheduler - To run the data extraction batch job at a scheduled interval. We would need to configure the application.properties file to schedule the data extraction.
3) Spring Data JPA - It is used to save the data from incoming CSV to database.
4) H2 Database - This is a persistent and formatted storage of the incoming CSV data. H2 is not an ideal solution for these scenarios. It is heavy transaction data. But for POC we used H2. In case of production implementation we will use Oracle. The underlying database vendor detail, driver and other things will be changed in configuration.
5) Spring RESTFul API - It is used to expose the endpoints to download the extracted data for a single day.


#### Downloading the CSV reports after deploying the application.
After the application is deployed, it can be downloaded from the below URLs. It is assumed that the application is deployed in localhost.

1) The asset report download URL will be - http://localhost:8081/reporting/findDailyAssets
2) The error report download URL will be - http://localhost:8081/reporting/findDailyErrors

In both the URLs, the below credential can be used for the time being.
	User Id - admin \n
	Password - password


### How Does it Work

This API will read from on input excel and reformat the
data and create an output file.

The REST API exposes an endpoint to download the Asset report for the current date.
The end point is <context-root>/findDailyAssets
  
There are two batch jobs which run and process the incoming data. It is assumed that the data will arrive at a
server directory. But the file arrival will not trigger the batch job. The batch job for processing the file has to be 
configured with the properties given below.

## This file location should be changed according to the
## incoming XFB or Kafka destination directory.
input.csv.name=E:\\software\\installation\\gitrepo\\cts\\incident-reporting\\src\\main\\resources\\input.csv
output.report.location=E:\\software\\installation\\gitrepo\\cts\\incident-reporting\\src\\main\\resources\\report.csv

## This is the name of the file which can be downloaded from the REST API.
user.download.file.name=output.cvs

## Header of the Output CSV file.
This is the header of the output CSV file. The Column headers may be changed. But
The position should not be changed.
input.file.header=Asset Name;Start Date;End Time ;Severity

## job configuration.
It is a CRON expression. The cron.expression.csv.load.job property stands for the configuration
of the input CSV parsing. And the cron.expression.report.generator.job property configures the 
schedule of the exported csv file.

cron.expression.csv.load.job=0 0 20 * * ?
cron.expression.report.generator.job=0 0 20 * * ?

## Error Job configuration
There is another job that generates the error report. To run the
job, please configure the cron expression cron.expression.error.report.generator.job .
The url, <context-root>/findDailyErrors gives the error report.

