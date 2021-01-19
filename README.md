# incident-reporting

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
