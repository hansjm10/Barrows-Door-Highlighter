package com.BarrowsDoorHighlighter;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BarrowsDoorHighlighterTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BarrowsDoorHighlighter.class);
		RuneLite.main(args);
	}
}