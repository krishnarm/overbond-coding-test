package org.krishna;

import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws WrongBondTypeException {

        // read the command line args
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equals("-h")) {
                    System.out.println("This program is meant to determine which government bond is the best benchmark for a given bond and will calculate the yield curve.");
                    System.out.println("Once you are prompted to provide a file path, please provide a correct path to a properly formatted CSV. Otherwise the program will fail.");
                } else {
                    System.out.println("Command not supported. Please use -h for more info.");
                }

            }
        }
        // try to read the file
        try {
            System.out.print("Enter CSV File Path: ");
            Scanner scanner = new Scanner(System.in);
            File filePath = new File(scanner.nextLine());
            OverBondClient client = new OverBondClient(filePath);
            List<CSVRecord> corporateBondList = client.getCorporateBondList();
            List<CSVRecord> governmentBondList = client.getGovernmentBondList();

            System.out.println("bond,benchmark,spread_to_benchmark");
            List<String> spreadToBenchMarkOutput = client.getSpreadToBenchMark(corporateBondList, governmentBondList);
            for(String outputEntry: spreadToBenchMarkOutput)
            {
                System.out.println(outputEntry);
            }

            System.out.println("bond,spread_to_curve");
            List<String> spreadToCurveOutput = client.getSpreadToCurve(corporateBondList, governmentBondList);
            for(String outputEntry: spreadToCurveOutput)
            {
                System.out.println(outputEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
