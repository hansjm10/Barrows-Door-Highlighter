package com.BarrowsPlus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;

import net.runelite.api.*;
import net.runelite.client.game.npcoverlay.HighlightedNpc;
import net.runelite.client.ui.overlay.*;

class BarrowsPlusOverlay extends Overlay
{
    private final Client client;
    private final BarrowsPlusPlugin plugin;
    private final BarrowsPlusConfig config;
    @Inject
    private BarrowsPlusOverlay(Client client, BarrowsPlusPlugin plugin, BarrowsPlusConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }
    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getPlane() == 0 && config.highlightDoors() != BarrowsPlusConfig.HighlightDoors.NEITHER)
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

            if (impostor == null) {
                continue;
            }

            final Shape polygon;
            final boolean isUnlockedDoor = impostor.getActions()[0] != null;
            final Color color = isUnlockedDoor ? Color.GREEN : Color.RED;

            if ((config.highlightDoors() == BarrowsPlusConfig.HighlightDoors.UNLOCKED && !isUnlockedDoor)
                    || (config.highlightDoors() == BarrowsPlusConfig.HighlightDoors.LOCKED && isUnlockedDoor)) {
                continue;
            }

            polygon = door.getConvexHull();

            if (polygon != null) {
                OverlayUtil.renderPolygon(graphics, polygon, color);
            }
        }
    }
}