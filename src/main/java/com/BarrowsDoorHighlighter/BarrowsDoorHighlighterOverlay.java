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

import net.runelite.api.Client;
import net.runelite.api.ObjectComposition;
import net.runelite.api.WallObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
class BarrowsDoorHighlighterOverlay extends Overlay
{
    private final Client client;
    private final BarrowsDoorHighlighter plugin;
    private final BarrowsDoorHighlighterConfig config;
    @Inject
    private BarrowsDoorHighlighterOverlay(Client client, BarrowsDoorHighlighter plugin, BarrowsDoorHighlighterConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }
    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getPlane() == 0 && config.highlightDoors() != BarrowsDoorHighlighterConfig.HighlightDoors.NEITHER)
        {
            renderDoors(graphics);
        }

        return null;
    }

    private void renderDoors(Graphics2D graphics)
    {
        for (WallObject door : plugin.getDoors())
        {
            ObjectComposition objectComp = client.getObjectDefinition(door.getId());
            ObjectComposition impostor = objectComp.getImpostorIds() != null ? objectComp.getImpostor() : null;

            if (impostor != null) {
                final Shape polygon;
                final boolean isUnlockedDoor = impostor.getActions()[0] != null;
                final Color color = isUnlockedDoor ? config.unlockedDoorColor() : config.lockedDoorColor();

                if ((config.highlightDoors() != BarrowsDoorHighlighterConfig.HighlightDoors.UNLOCKED && !isUnlockedDoor)
                        || (config.highlightDoors() != BarrowsDoorHighlighterConfig.HighlightDoors.LOCKED && isUnlockedDoor)) {

                    polygon = door.getConvexHull();

                    if (polygon != null) {
                        OverlayUtil.renderPolygon(graphics, polygon, color);
                    }
                }
            }
        }
    }
}