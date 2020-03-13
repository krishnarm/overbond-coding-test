* Reason to choose Java
    1. Java suports the concepts of OOP and so it is easily readble. 
    2. I chose java as the given application is light-weight and can be finished easily using Java and it's libraries and it is platform independent.
    3. I've learnt java and been using Angular and ReactJS technologies these days. using Java for this challenge made me to remind all the functions that i was using while i was learning the program

* Running the program:

    1. The main loginc of the applicaiton lies in main.java and was developed using Visual stuido code.
    2. Click on the file, then click on 'Run' in the context menu to run the program. To debug, click on 'Debug' on the same.

Architecture:

* [src](./src)
    * [org.krishna](./src/org.krishna)
        * [test](./src/org.krishna/test) - folder containing unit Tests
        * BondDateComparator.java - this is used for comparing two Bond Dates for sorting
        * Main.java - the main Java interface file and driver
        * OverBondClient.java - the business logic
        * WrongBondTypeException.java - an exception class used in case a non standard bond type is presented
    

* The main logic is as follows:
1. Run the "getSpreadToBenchMark" function to get the calculate the yield spread (return) between a corporate bond and its government bond benchmark.
2. Run the "getSpreadToCurve" function to get the spread to curve percentage using linear interpolation.


* Future Improvements

1. In the future, it would be nice to add a different input types where the calues can also be encrypted to boost the security feature of the file. 
2. store bonds and querying into the cloud to save memory wastage.
3. If the data is very large, then we can use the CSV API with external sort to boost performance. 
4. Only very simple tests are written due to time constraint. If i had more time, the tests and the funcitonalities could've been better. 
