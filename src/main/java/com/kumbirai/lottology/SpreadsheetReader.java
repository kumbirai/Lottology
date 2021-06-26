/*
 com.kumbirai.lottology.SpreadsheetReader<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> SpreadsheetReader<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 * @date 08 Jul 2017<br>
 */
public class SpreadsheetReader
{
    private static final Logger LOGGER = LogManager.getLogger(SpreadsheetReader.class.getName());
    private SortedMap<Date, Row> resultsRowMap;
    private Game game;

    /**
     * Constructor: @param resultsRowMap Constructor: @param game
     */
    public SpreadsheetReader(SortedMap<Date, Row> resultsRowMap, Game game)
    {
        super();
        this.resultsRowMap = resultsRowMap;
        this.game = game;
    }

    /**
     * Purpose:
     * <br>
     * readXSLXFile<br>
     * <br>
     *
     * @param inputFile<br>
     */
    public void readXSLXFile(File inputFile)
    {
        try (Workbook fileWB = new XSSFWorkbook(inputFile))
        {
            parseFile(fileWB);
        }
        catch (InvalidFormatException ex)
        {
            LOGGER.error("[InvalidFormatException] has been caught.", ex);
        }
        catch (IOException ex)
        {
            LOGGER.error("[IOException] has been caught.", ex);
        }
    }

    /**
     * Purpose:
     * <br>
     * readXSLFile<br>
     * <br>
     *
     * @param inputFile<br>
     */
    public void readXSLFile(File inputFile)
    {
        try (Workbook fileWB = new HSSFWorkbook(new FileInputStream(inputFile)))
        {
            parseFile(fileWB);
        }
        catch (FileNotFoundException ex)
        {
            LOGGER.error("[FileNotFoundException] has been caught.", ex);
        }
        catch (IOException ex)
        {
            LOGGER.error("[IOException] has been caught.", ex);
        }
    }

    /**
     * Purpose:
     * <br>
     * parseFile<br>
     * <br>
     *
     * @param fileWB<br>
     */
    private void parseFile(Workbook fileWB)
    {
        Sheet sheet = fileWB.getSheetAt(0);
        LOGGER.info(sheet.getSheetName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        for (Row row : sheet)
        {
            StringBuilder sb = new StringBuilder();
            int rowNum = row.getRowNum();
            for (Cell cell : row)
            {
                String value = cell.toString()
                                   .trim();
                sb.append(String.format("(%s)", Integer.valueOf(cell.getColumnIndex())))
                  .append(value)
                  .append("|");
            }
            LOGGER.info(String.format("rownum: %s - %s", Integer.valueOf(rowNum), sb));
            if (rowNum >= game.getStartRow())
            {
                try
                {
                    Date drawDate = sdf.parse(row.getCell(0)
                                                 .toString()
                                                 .trim());
                    resultsRowMap.put(drawDate, row);
                }
                catch (ParseException ex)
                {
                    LOGGER.error("[ParseException] has been caught.", ex);
                }
            }
        }
    }
}