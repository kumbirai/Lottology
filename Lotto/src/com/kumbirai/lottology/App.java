/*
 com.kumbirai.lottology.App<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import java.util.List;
import java.util.SortedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kumbirai.common.io.FileUtils;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> App<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @date 08 Jul 2017<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 *					
 */
public class App
{
	private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

	/**
	 * Constructor:
	 */
	public App()
	{
		super();
	}
	
	/**
	 * Purpose:
	 * <br>
	 * main<br>
	 * <br>
	 * @param args<br>
	 */
	public static void main(String... args)
	{
		App app = new App();
		app.process();
	}

	/**
	 * Purpose:
	 * <br>
	 * process<br>
	 * <br><br>
	 */
	private void process()
	{
		LOGGER.info("-- Begin --");
		generateNumbers(Game.LOTTO);
		generateNumbers(Game.POWERBALL);
	}

	/**
	 * Purpose:
	 * <br>
	 * generateNumbers<br>
	 * <br>
	 * @param game<br>
	 */
	private void generateNumbers(Game game)
	{
		new FolderParser(game).parseFolder();
		SortedMap<Integer, List<Integer>> resultsMap = new ResultParser().parseResults(FileUtils.getFile("./output", game.getFileName(), true));
		LOGGER.info(String.format("%s", resultsMap));
		new LottoNumberGenerator().generateWeightedGameNumbers(resultsMap, game);
	}
}