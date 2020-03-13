#Overbond Coding Challenge
##What is this?
This respository is for the Overbond coding challenge. The challenge contains two parts:

1. Calculating the spread between a corporate bond and it's government benchmark
2. Calculating the spread to the curve 

For more information about the challenge please visit the following Gist:
https://gist.github.com/apotapov/3118c573df2a4ac7a93f00cf39ea620a

##Understanding the Code
This challenge was written in Java. All the main logic is contained in the Main Java class. 
There was no need to split this up into several classes as the challenge mostly revolved around basic 
math functions. I think this challenge might actually lend it self well to a functional language, but 
for the sake of time I chose to write it all in Java for now. 

The code is structured as follows:
.
* [src](./src)
    * [org.rafikmatta](./src/org.rafikmatta)
        * [test](./src/org.rafikmatta/test) - folder containing unit Tests
        * BondDateComparator.java - this is used for comparing two Bond Dates for sorting
        * Main.java - the main Java interface file and driver
        * OverBondClient.java - the business logic
        * WrongBondTypeException.java - an exception class used in case a non standard bond type is presented
    

The main logic is as follows:
1. Ask the user to specify an input file via the command line
2. Parse the file and split it up into Corporate and Government Bonds
3. Sort the two respective lists (this use's Java Collections built-in sort which is modified merge-sort so it performs in O(nlogn) time. For mor information please see https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html)
4. Run the "getSpreadToBenchMark" function.
5. Run the "getSpreadToCurve" function.

The functions in 4 and 5 share some logic. What happens is that they both iterate through the 
corporate bond list and on each loop, they will add the corporate bond to the government bond list and 
be sorted again. After that the logic differs. 

## Future Improvements

At this time, the logic is based on the assumptions mentioned by the authors in the Gist.
In the future, it would be nice to add a validation layer for the CSV input file,
possibly add support for multiple input methods.

The total worst case runtime of this system should be O(nlogn) because of the sorting 
that needs to occur on the different lists. It would be ideal to come up
with an algorithm that comes closer to O(n) (if possible).

A final improvement might be to change the interface from a command line, 
or to further expand on the command line interface. 