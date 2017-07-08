/*
 com.kumbirai.lottology.LottoNumberGenerator<br>

 Copyright (c) 2017 - Kumbirai 'Coach' Mundangepfupfu (www.kumbirai.com)

 All rights reserved.
 */
package com.kumbirai.lottology;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
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
		for (int i = 1; i <= 750; i++)
		{
			Set<Integer> rowNumbers = new TreeSet<>();
			while (rowNumbers.size() < game.getColumns())
			{
				rowNumbers.add(getWeightedRandomNumber(resultsMap.get(Integer.valueOf(rowNumbers.size() + 1))));
			}
			String line;
			switch (game)
			{
			case POWERBALL:
				line = String.format("(%s) %s [%s]", String.valueOf(i), rowNumbers,
						getWeightedRandomNumber(resultsMap.get(Integer.valueOf(rowNumbers.size() + 1))));
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
				FileUtils.writeToFile("./output", game.getPredictionsFile(), line, null, true);
			}
		}
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
}