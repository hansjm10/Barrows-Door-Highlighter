/*
 * Copyright (c) 2022, Jordan Hans
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.BarrowsDoorHighlighter;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

@ConfigGroup("BarrowsDoorHighlighter")
public interface BarrowsDoorHighlighterConfig extends Config
{
	enum HighlightDoors
	{
		LOCKED,
		UNLOCKED,
		BOTH,
		NEITHER,
	}
	@ConfigItem(
			keyName = "highlightDoors",
			name = "Highlight Doors",
			description = "Select which type of doors to highlight.",
			position = 0
	)
	default HighlightDoors highlightDoors()
	{
		return HighlightDoors.BOTH;
	}
	@Alpha
	@ConfigItem(
			keyName = "unlockedDoorColor",
			name = "Unlocked Door Color",
			description = "Select the unlocked door color.",
			position = 1
	)
	default Color unlockedDoorColor()
	{
		return Color.GREEN;
	}
	@Alpha
	@ConfigItem(
			keyName = "lockedDoorColor",
			name = "Locked Door Color",
			description = "Select the locked door color.",
			position = 2
	)
	default Color lockedDoorColor()
	{
		return Color.RED;
	}
}
