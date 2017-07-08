/*
 com.kumbirai.lottology.Game<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> Game<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @date 08 Jul 2017<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 *					
 */
public enum Game
{
	LOTTO("lotto.xlsx", "lottoPredictions.txt", "lotto", 6, 5), POWERBALL("powerball.xlsx", "powerballPredictions.txt", "powerball", 5, 5);
	private String fileName;
	private String predictionsFile;
	private String folder;
	private int columns;
	private int startRow;

	/**
	 * Constructor: @param fileName
	 * Constructor: @param predictionsFile
	 * Constructor: @param folder
	 * Constructor: @param columns
	 * Constructor: @param startRow
	 */
	private Game(String fileName, String predictionsFile, String folder, int columns, int startRow)
	{
		this.fileName = fileName;
		this.predictionsFile = predictionsFile;
		this.folder = folder;
		this.columns = columns;
		this.startRow = startRow;
	}

	/** Getter for the <code>fileName</code> attribute.<br>
	 * @return String - value of the attribute <code>fileName</code>.
	 */
	public String getFileName()
	{
		return this.fileName;
	}

	/** Getter for the <code>predictionsFile</code> attribute.<br>
	 * @return String - value of the attribute <code>predictionsFile</code>.
	 */
	public String getPredictionsFile()
	{
		return this.predictionsFile;
	}

	/** Getter for the <code>folder</code> attribute.<br>
	 * @return String - value of the attribute <code>folder</code>.
	 */
	public String getFolder()
	{
		return this.folder;
	}

	/** Getter for the <code>columns</code> attribute.<br>
	 * @return int - value of the attribute <code>columns</code>.
	 */
	public int getColumns()
	{
		return this.columns;
	}

	/** Getter for the <code>startRow</code> attribute.<br>
	 * @return int - value of the attribute <code>startRow</code>.
	 */
	public int getStartRow()
	{
		return this.startRow;
	}
}