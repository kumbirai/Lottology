/*
 com.kumbirai.lottology.FolderParser<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import com.kumbirai.common.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> FolderParser<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 * @date 08 Jul 2017<br>
 */
public class FolderParser
{
    private static final Logger LOGGER = LogManager.getLogger(FolderParser.class.getName());
    private SortedMap<Date, Row> resultsRowMap = new TreeMap<>();
    private Game game;

    /**
     * Constructor: @param game
     */
    public FolderParser(Game game)
    {
        super();
        this.game = game;
    }

    /**
     * Purpose:
     * <br>
     * parseFolder<br>
     * <br><br>
     */
    public void parseFolder()
    {
        File folder = new File(game.getFolder());
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                SpreadsheetReader reader = new SpreadsheetReader(resultsRowMap, game);
                reader.readXSLXFile(listOfFiles[i]);
            }
            else if (listOfFiles[i].isDirectory())
            {
                LOGGER.debug("Directory " + listOfFiles[i].getName());
            }
        }
        try
        {
            buildAllResultsOutput();
        }
        catch (IOException ex)
        {
            LOGGER.error("[IOException] has been caught.", ex);
        }
    }

    /**
     * Purpose:
     * <br>
     * buildAllResultsOutput<br>
     * <br>
     *
     * @throws IOException<br>
     */
    private void buildAllResultsOutput() throws IOException
    {
        try (Workbook wb = new XSSFWorkbook())
        {
            CreationHelper creationHelper = wb.getCreationHelper();
            Sheet s = wb.createSheet("Results");
            int rownum = 0;
            for (Map.Entry<Date, Row> entry : resultsRowMap.entrySet())
            {
                Row r = s.createRow(rownum++);
                for (Cell cell : entry.getValue())
                {
                    int columnIndex = cell.getColumnIndex();
                    Cell c = r.createCell(columnIndex);
                    if (columnIndex == 0)
                    {
                        CellStyle style = wb.createCellStyle();
                        style.setDataFormat(creationHelper.createDataFormat()
                                                          .getFormat("d MMM yyyy"));
                        c.setCellValue(entry.getKey());
                        c.setCellStyle(style);
                    }
                    else
                    {
                        switch (cell.getCellTypeEnum())
                        {
                            case FORMULA:
                                c.setCellValue(cell.getCellFormula());
                                break;
                            case NUMERIC:
                                c.setCellValue(cell.getNumericCellValue());
                                break;
                            case STRING:
                                c.setCellValue(cell.getStringCellValue()
                                                   .trim());
                                break;
                            case BLANK:
                                c.setCellType(CellType.BLANK);
                                break;
                            case BOOLEAN:
                                c.setCellValue(cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                c.setCellValue(cell.getErrorCellValue());
                                break;
                            default:
                                c.setCellValue(cell.getStringCellValue()
                                                   .trim());
                        }
                    }
                }
            }
            FileOutputStream out = new FileOutputStream(FileUtils.getFile("./output", game.getFileName(), true));
            try
            {
                wb.write(out);
            }
            finally
            {
                out.close();
            }
        }
    }
}