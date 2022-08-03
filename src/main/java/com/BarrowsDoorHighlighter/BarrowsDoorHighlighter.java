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

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.api.WallObject;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static net.runelite.api.NullObjectID.NULL_20681;
import static net.runelite.api.NullObjectID.NULL_20682;
import static net.runelite.api.NullObjectID.NULL_20683;
import static net.runelite.api.NullObjectID.NULL_20684;
import static net.runelite.api.NullObjectID.NULL_20685;
import static net.runelite.api.NullObjectID.NULL_20686;
import static net.runelite.api.NullObjectID.NULL_20687;
import static net.runelite.api.NullObjectID.NULL_20688;
import static net.runelite.api.NullObjectID.NULL_20689;
import static net.runelite.api.NullObjectID.NULL_20690;
import static net.runelite.api.NullObjectID.NULL_20691;
import static net.runelite.api.NullObjectID.NULL_20692;
import static net.runelite.api.NullObjectID.NULL_20693;
import static net.runelite.api.NullObjectID.NULL_20694;
import static net.runelite.api.NullObjectID.NULL_20695;
import static net.runelite.api.NullObjectID.NULL_20696;
import static net.runelite.api.NullObjectID.NULL_20700;
import static net.runelite.api.NullObjectID.NULL_20701;
import static net.runelite.api.NullObjectID.NULL_20702;
import static net.runelite.api.NullObjectID.NULL_20703;
import static net.runelite.api.NullObjectID.NULL_20704;
import static net.runelite.api.NullObjectID.NULL_20705;
import static net.runelite.api.NullObjectID.NULL_20706;
import static net.runelite.api.NullObjectID.NULL_20707;
import static net.runelite.api.NullObjectID.NULL_20708;
import static net.runelite.api.NullObjectID.NULL_20709;
import static net.runelite.api.NullObjectID.NULL_20710;
import static net.runelite.api.NullObjectID.NULL_20711;
import static net.runelite.api.NullObjectID.NULL_20712;
import static net.runelite.api.NullObjectID.NULL_20713;
import static net.runelite.api.NullObjectID.NULL_20714;
import static net.runelite.api.NullObjectID.NULL_20715;


@Slf4j
@PluginDescriptor(
        name = "Barrows Door Highlighter"
)
public class BarrowsDoorHighlighter extends Plugin
 {
    static final ImmutableSet<Integer> DOOR_IDS = ImmutableSet.of(
            NULL_20681, NULL_20682, NULL_20683, NULL_20684, NULL_20685, NULL_20686, NULL_20687, NULL_20688,
            NULL_20689, NULL_20690, NULL_20691, NULL_20692, NULL_20693, NULL_20694, NULL_20695, NULL_20696,
            NULL_20700, NULL_20701, NULL_20702, NULL_20703, NULL_20704, NULL_20705, NULL_20706, NULL_20707,
            NULL_20708, NULL_20709, NULL_20710, NULL_20711, NULL_20712, NULL_20713, NULL_20714, NULL_20715
    );
    @Getter
    private final Set<WallObject> doors = new HashSet<>();
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private BarrowsDoorHighlighterOverlay barrowsDoorHighlighterOverlay;
    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(barrowsDoorHighlighterOverlay);
    }
    @Override
    protected void shutDown() throws Exception
    {
        doors.clear();
    }
    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.LOADING)
        {
            doors.clear();
        }
    }
    @Provides
    BarrowsDoorHighlighterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(BarrowsDoorHighlighterConfig.class);
    }
    @Subscribe
    public void onWallObjectSpawned(WallObjectSpawned event)
    {
        WallObject wallObject = event.getWallObject();
        if (DOOR_IDS.contains(wallObject.getId())) 
        {
            doors.add(wallObject);
        }
    }
    @Subscribe
    public void onWallObjectDespawned(WallObjectDespawned event)
    {
        WallObject wallObject = event.getWallObject();
        doors.remove(wallObject);
    }
}
