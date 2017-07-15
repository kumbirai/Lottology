/*
 com.kumbirai.lottology.LottoNumberGenerator<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kumbirai.common.io.FileUtils;

/**
 * <p><b>Purpose:</b><br>
 * <br>
 *
 * <p><b>Title:</b> LottoNumberGenerator<br>
 * <b>Description:</b> </p>
 *
 * @author Kumbirai 'Coach' Mundangepfupfu<br>
 * @date 08 Jul 2017<br>
 * @version 1.0<br>
 *
 * <b>Revision:</b>
 *					
 */
public class LottoNumberGenerator
{
	private static final Logger LOGGER = LogManager.getLogger(LottoNumberGenerator.class.getName());
	private static Random random = new SecureRandom();
	private static final String OUTPUT_FOLDER = "./output";
	private static final String LINE_SEPARATOR = "line.separator";

	/**
	 * Constructor:
	 */
	public LottoNumberGenerator()
	{
		super();
	}

	/**
	 * Purpose:
	 * <br>
	 * generateWeightedGameNumbers<br>
	 * <br>
	 * @param resultsMap
	 * @param game<br>
	 */
	public void generateWeightedGameNumbers(SortedMap<Integer, List<Integer>> resultsMap, Game game)
	{
		List<Integer> allNumbers = new ArrayList<>();
		List<Integer> powerballs = resultsMap.get(Integer.valueOf(6));
		for (int i = 1; i <= game.getColumns(); i++)
		{
			allNumbers.addAll(resultsMap.get(Integer.valueOf(i)));
			Collections.shuffle(allNumbers);
		}
		for (int i = 1; i <= 750; i++)
		{
			Set<Integer> rowNumbers = new TreeSet<>();
			while (rowNumbers.size() < game.getColumns())
			{
				rowNumbers.add(getWeightedRandomNumber(allNumbers));
			}
			String line;
			switch (game)
			{
			case POWERBALL:
				line = String.format("(%s) %s [%s]", String.valueOf(i), rowNumbers, getWeightedRandomNumber(powerballs));
				break;
			case LOTTO:
				line = String.format("(%s) %s", String.valueOf(i), rowNumbers);
				break;
			default:
				line = "";
			}

			LOGGER.info(line);
			if (i % 19 == 0)
			{
				FileUtils.writeToFile(OUTPUT_FOLDER, game.getPredictionsFile(), line, null, true);
			}
		}
		writeStatistics(allNumbers, game);
		if (game == Game.POWERBALL)
			writeStatistics(powerballs, game);
		FileUtils.writeToFile(OUTPUT_FOLDER, game.getPredictionsFile(), allNumbers.toString(), null, true);
	}

	/**
	 * Purpose:
	 * <br>
	 * getWeightedRandomNumber<br>
	 * <br>
	 * @param list
	 * @return<br>
	 */
	private Integer getWeightedRandomNumber(List<Integer> list)
	{
		return list.get(random.nextInt(list.size()));
	}

	/**
	 * Purpose:
	 * <br>
	 * writeStatistics<br>
	 * <br>
	 * @param numbers
	 * @param game<br>
	 */
	private void writeStatistics(List<Integer> numbers, Game game)
	{
		Map<Integer, Integer> stats = new TreeMap<>();
		for (Integer num : numbers)
		{
			Integer count = stats.get(num);
			if (count == null)
				count = Integer.valueOf(0);
			count++;
			stats.put(num, count);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty(LINE_SEPARATOR)).append("---").append(System.getProperty(LINE_SEPARATOR));
		DecimalFormat twoDigitIntFormat = new DecimalFormat("00");
		for (Map.Entry<Integer, Integer> entry : stats.entrySet())
		{
			sb.append(twoDigitIntFormat.format(entry.getKey())).append(" : ").append(entry.getValue()).append(System.getProperty(LINE_SEPARATOR));
		}
		FileUtils.writeToFile(OUTPUT_FOLDER, game.getPredictionsFile(), sb.toString(), null, true);
	}
}