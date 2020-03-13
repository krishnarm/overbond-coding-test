package org.krishna;

import org.apache.commons.csv.CSVRecord;

import java.util.Comparator;
public class BondDateComparator implements Comparator<CSVRecord>{
    @Override
    public int compare(CSVRecord o1, CSVRecord o2) {
        String[] splitYearStringo1 = o1.get(2).split(" ");
        String[] splitYearStringo2 = o2.get(2).split(" ");
        Double o1Years = Double.parseDouble(splitYearStringo1[0]);
        Double o2Years = Double.parseDouble(splitYearStringo2[0]);
        if(o1Years > o2Years)
        {
            return 1;
        }
        else if (o1Years == o2Years)
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }
}
