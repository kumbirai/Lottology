/*
 com.kumbirai.lottology.ResultParser<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> ResultParser<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 * @date 08 Jul 2017<br>
 */
public class ResultParser
{
    private static final Logger LOGGER = LogManager.getLogger(ResultParser.class.getName());

    /**
     * Constructor:
     */
    public ResultParser()
    {
        super();
    }

    /**
     * Purpose:
     * <br>
     * parseResults<br>
     * <br>
     *
     * @param inputFile
     * @return<br>
     */
    public SortedMap<Integer, List<Integer>> parseResults(File inputFile)
    {
        SortedMap<Integer, List<Integer>> resultsMap = new TreeMap<>();
        ZipSecureFile.setMinInflateRatio(-1.0D);
        try (Workbook wb = new XSSFWorkbook(inputFile))
        {
            Sheet sheet = wb.getSheetAt(0);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
            for (Row row : sheet)
            {
                for (Cell cell : row)
                {
                    int columnIndex = cell.getColumnIndex();
                    if (columnIndex == 0)
                    {
                        LOGGER.info(String.format("Results for %s", sdf.format(cell.getDateCellValue())));
                    }
                    else
                    {
                        List<Integer> numbers = resultsMap.get(Integer.valueOf(columnIndex));
                        if (numbers == null)
                        {
                            numbers = new ArrayList<>();
                        }
                        numbers.add(Integer.valueOf((int) cell.getNumericCellValue()));
                        Collections.shuffle(numbers);
                        resultsMap.put(Integer.valueOf(columnIndex), numbers);
                    }
                }
            }
        }
        catch (InvalidFormatException ex)
        {
            LOGGER.error("[InvalidFormatException] has been caught.", ex);
        }
        catch (IOException ex)
        {
            LOGGER.error("[IOException] has been caught.", ex);
        }
        return resultsMap;
    }
}