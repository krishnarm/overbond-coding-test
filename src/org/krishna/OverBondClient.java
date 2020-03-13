package org.krishna;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OverBondClient {

    private File inputFile;
    private List<CSVRecord> corporateBondList = new ArrayList<>();
    private List<CSVRecord> governmentBondList = new ArrayList<>();

    public OverBondClient(File filePath) throws WrongBondTypeException, IOException {

        inputFile = filePath;
        Charset charset = StandardCharsets.UTF_8;
        CSVParser parser = CSVParser.parse(inputFile, charset, CSVFormat.RFC4180);

        List<CSVRecord> records = parser.getRecords();
        records.remove(0);

        for (CSVRecord record : records) {
            if (record.get(1).equals("corporate")) {
                corporateBondList.add(record);
            } else if (record.get(1).equals("government")) {
                governmentBondList.add(record);
            } else {
                throw new WrongBondTypeException("This is not the right bond type. Either your CSV is formatted incorrectly or you have an incorrect datatype");
            }
        }

        Collections.sort(corporateBondList, new BondDateComparator()); // O(nlogn operation)
        Collections.sort(governmentBondList, new BondDateComparator()); // O(nlogn operation)

    }

    public List<CSVRecord> getCorporateBondList() {
        return corporateBondList;
    }

    public List<CSVRecord> getGovernmentBondList() {
        return governmentBondList;
    }

    public static List<String> getSpreadToBenchMark(List<CSVRecord> corporateBondList, List<CSVRecord> governmentBondList) {
        List<String> outputList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for (CSVRecord record : corporateBondList) {
            List<CSVRecord> newList = new ArrayList<CSVRecord>(governmentBondList);
            newList.add(record);
            newList.sort(new BondDateComparator());
            for (int i = 0; i < newList.size(); i++) {
                CSVRecord currentElement = newList.get(i);
                if (currentElement.get(1).equals("corporate")) {
                    if (i == 0) {
                        Double yieldSpread = calculateYieldSpread(currentElement, newList.get(i + 1));
                        String output = currentElement.get(0) + "," + newList.get(i + 1).get(0) + "," + df.format(yieldSpread) + "%";
                        outputList.add(output);
                    } else if (i == newList.size() - 1) {
                        Double yieldSpread = calculateYieldSpread(currentElement, newList.get(i - 1));
                        String output = currentElement.get(0) + "," + newList.get(i - 1).get(0) + "," + df.format(yieldSpread) + "%";
                        outputList.add(output);
                    } else {
                        Double differenceInYears1 = Math.abs(calculateYears(currentElement, newList.get(i + 1)));
                        Double differenceInYears2 = Math.abs(calculateYears(currentElement, newList.get(i - 1)));
                        if (differenceInYears1 > differenceInYears2) {
                            Double yieldSpread = calculateYieldSpread(currentElement, newList.get(i - 1));
                            String output = currentElement.get(0) + "," + newList.get(i - 1).get(0) + "," + df.format(yieldSpread) + "%";
                            outputList.add(output);
                        } else {
                            Double yieldSpread = calculateYieldSpread(currentElement, newList.get(i + 1));
                            String output = currentElement.get(0) + "," + newList.get(i + 1).get(0) + "," + df.format(yieldSpread) + "%";
                            outputList.add(output);
                        }
                    }
                }

            }


        }
        return outputList;
    }

    public static List<String> getSpreadToCurve(List<CSVRecord> corporateBondList, List<CSVRecord> governmentBondList) {
        List<String> outputList = new ArrayList<>();

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for (CSVRecord record : corporateBondList) {
            List<CSVRecord> newList = new ArrayList<CSVRecord>(governmentBondList);
            newList.add(record);
            newList.sort(new BondDateComparator());
            for (int i = 0; i < newList.size(); i++) {
                CSVRecord currentElement = newList.get(i);
                if (currentElement.get(1).equals("corporate")) {
                    CSVRecord previousElement = newList.get(i - 1);
                    CSVRecord nextElement = newList.get(i + 1);
                    double slope = calculateSlope(getYield(previousElement), getYield(nextElement), getYears(previousElement), getYears(nextElement));
                    double yIntercept = calculateYIntercept(getYield(previousElement), getYears(previousElement), slope);
                    double spreadToCurve = calculateSpreadToCurve(getYield(currentElement), getYears(currentElement), yIntercept, slope);
                    String output = currentElement.get(0) + "," + df.format(spreadToCurve) + "%";
                    outputList.add(output);
                }
            }


        }
        return outputList;
    }

    public static double calculateSpreadToCurve(double corpBondYield, double termInYears, double yIntercept, double slope) {
        return corpBondYield - (slope * termInYears + yIntercept);
    }

    public static double calculateSlope(double y1, double y2, double x1, double x2) {
        return (y2 - y1) / (x2 - x1);
    }

    public static double calculateYIntercept(double y, double x, double slope) {
        return y - (x * slope);
    }


    public static double calculateYieldSpread(CSVRecord o1, CSVRecord o2) {
        Double o1Yield = getYield(o1);
        Double o2Yield = getYield(o2);
        return o1Yield - o2Yield;
    }

    public static double calculateYears(CSVRecord o1, CSVRecord o2) {
        Double o1Years = getYears(o1);
        Double o2Years = getYears(o2);
        return o1Years - o2Years;
    }

    public static double getYears(CSVRecord record) {
        String[] splitYearString = record.get(2).split(" ");
        return Double.parseDouble(splitYearString[0]);
    }

    public static double getYield(CSVRecord record) {
        String[] splitYield = record.get(3).split("%");
        return Double.parseDouble(splitYield[0]);
    }
}
