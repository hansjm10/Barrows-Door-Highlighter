package com.BarrowsPlus;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BarrowsPlus")
public interface BarrowsPlusConfig extends Config
{
	enum HighlightDoors
	{
		LOCKED,
		UNLOCKED,
		BOTH,
		NEITHER,
	}
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}
	@ConfigItem(
			keyName = "highlightDoors",
			name = "Highlight doors",
			description = "Configure whether or not to highlight the doors",
			position = 7
	)
	default HighlightDoors highlightDoors()
	{
		return HighlightDoors.BOTH;
	}

}
