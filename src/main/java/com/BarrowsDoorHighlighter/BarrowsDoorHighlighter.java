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

import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_A_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_A_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_B_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_B_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_C_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_C_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_D_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_D_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_E_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_E_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_F_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_F_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_G_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_G_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_H_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_H_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_I_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_I_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_J_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_J_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_K_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_K_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_L_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_L_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_M_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_M_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_N_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_N_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_O_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_O_R;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_P_L;
import static net.runelite.api.gameval.ObjectID.BARROWS_DOOR_P_R;

@Slf4j
@PluginDescriptor(
        name = "Barrows Door Highlighter"
)
public class BarrowsDoorHighlighter extends Plugin
 {
    static final ImmutableSet<Integer> DOOR_IDS = ImmutableSet.of(
            BARROWS_DOOR_A_R, BARROWS_DOOR_B_R, BARROWS_DOOR_C_R, BARROWS_DOOR_D_R,
            BARROWS_DOOR_E_R, BARROWS_DOOR_F_R, BARROWS_DOOR_G_R, BARROWS_DOOR_H_R,
            BARROWS_DOOR_I_R, BARROWS_DOOR_J_R, BARROWS_DOOR_K_R, BARROWS_DOOR_L_R,
            BARROWS_DOOR_M_R, BARROWS_DOOR_N_R, BARROWS_DOOR_O_R, BARROWS_DOOR_P_R,
            BARROWS_DOOR_A_L, BARROWS_DOOR_B_L, BARROWS_DOOR_C_L, BARROWS_DOOR_D_L,
            BARROWS_DOOR_E_L, BARROWS_DOOR_F_L, BARROWS_DOOR_G_L, BARROWS_DOOR_H_L,
            BARROWS_DOOR_I_L, BARROWS_DOOR_J_L, BARROWS_DOOR_K_L, BARROWS_DOOR_L_L,
            BARROWS_DOOR_M_L, BARROWS_DOOR_N_L, BARROWS_DOOR_O_L, BARROWS_DOOR_P_L
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
        overlayManager.remove(barrowsDoorHighlighterOverlay);
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
