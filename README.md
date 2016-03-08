# payment-tracker
simple payment tracker application

How to run:
#build project and run tests by Maven
mvn package 

#run application from folder /target
java -cp paymentTracker-1.0-SNAPSHOT.jar pt.App

#run application with loading file
java -cp paymentTracker-1.0-SNAPSHOT.jar pt.App -f input.txt

Usage:
USD 123 - sample input, pattern for currency with amount is ^[A-Z]{3}[\\s]{1}[+-]?[0-9]{1,10}[.]?[0-9]{0,6}$
f file.txt - data from file, see example file src\test\resources\input.txt
r rateFile.txt - file with currency rates, see example file src\test\resources\rateFile.txt
q - quit

Features:
-supported format of input is: USD -123.123456
-every minute application prints list of all currencies
-it supports load file with multiple exchange rates, see example file rateFile.txt. These rates are used in one minute report 
-currency with amount is possible to type into command line or load from file, see usage.
-thread safe
-simple controls
-simple junit tests
-patterns: Singleton, Builder, Beans, Method Chaining 

Screenshots:
One minute report
****************************************************************
****************************************************************
***************** One minute report ****************************
CZK 21
HUF 215
USD -122
GBP 2000

One minute report with exchange rates
****************************************************************
****************************************************************
***************** One minute report ****************************
CZK 21
HUF 215 (CZK 215.00)
USD -122 (CZK -3078.06) (HUF -1.22) (EUR -150.06)
GBP 2000

