package org.krishna.test;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.krishna.OverBondClient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class TestMain {

    @Test
    public void calculateYieldTest() throws IOException {
        File filePath = new File("sample_input.csv");
        Charset charset = StandardCharsets.UTF_8;
        CSVParser parser = CSVParser.parse(filePath, charset, CSVFormat.RFC4180);

        List<CSVRecord> records = parser.getRecords();
        records.remove(0);

        assertEquals(0.5, OverBondClient.calculateYieldSpread(records.get(1), records.get(0)), 0.00001);

    }

    @Test
    public void calculateYearsTest() throws IOException {
        File filePath = new File("sample_input.csv");
        Charset charset = StandardCharsets.UTF_8;
        CSVParser parser = CSVParser.parse(filePath, charset, CSVFormat.RFC4180);

        List<CSVRecord> records = parser.getRecords();
        records.remove(0);

        assertEquals(0.7, OverBondClient.calculateYears(records.get(1), records.get(0)), 0.00001);

    }

    @Test
    public void calculateSlopeTest() throws IOException {

        assertEquals(1, OverBondClient.calculateSlope(1.1, 1.2, 0.4, 0.5), 0.00000001);

    }


    @Test
    public void calculateYInterceptTest() throws IOException {
        assertEquals(3.6, OverBondClient.calculateYIntercept(10.3,6.7,1), 0.00001);

    }

    @Test
    public void calculateSpreadToCurve() throws IOException {

        assertEquals(3.4, OverBondClient.calculateSpreadToCurve(10.3,3.3,3.6,1), 0.00001);

    }
}

