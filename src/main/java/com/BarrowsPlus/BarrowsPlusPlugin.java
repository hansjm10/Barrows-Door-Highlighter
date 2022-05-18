package com.BarrowsPlus;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.npcoverlay.HighlightedNpc;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.npchighlight.NpcIndicatorsConfig;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import java.awt.*;
import java.util.*;
import java.util.function.Function;

import static net.runelite.api.NullObjectID.*;

@Slf4j
@PluginDescriptor(
	name = "BarrowsPlus"
)
public class BarrowsPlusPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private BarrowsPlusOverlay barrowsPlusOverlay;
	@Inject
	private ChatMessageManager chatMessageManager;
	@Inject
	private BarrowsPlusConfig config;

	@Inject
	NpcOverlayService npcOverlayService;
	@Getter final Set<NPC> npcs = new HashSet<>();
	static final ImmutableSet<Integer> DOOR_IDS = ImmutableSet.of(
			NULL_20681, NULL_20682, NULL_20683, NULL_20684, NULL_20685, NULL_20686, NULL_20687, NULL_20688,
			NULL_20689, NULL_20690, NULL_20691, NULL_20692, NULL_20693, NULL_20694, NULL_20695, NULL_20696,
			NULL_20700, NULL_20701, NULL_20702, NULL_20703, NULL_20704, NULL_20705, NULL_20706, NULL_20707,
			NULL_20708, NULL_20709, NULL_20710, NULL_20711, NULL_20712, NULL_20713, NULL_20714, NULL_20715
	);
	@Getter(AccessLevel.PACKAGE)
	private final Map<NPC, HighlightedNpc> highlightedNpcs = new HashMap<>();
	private final Function<NPC, HighlightedNpc> isHighlighted = highlightedNpcs::get;
	@Getter
	private final Set<WallObject> doors = new HashSet<>();
	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
		npcOverlayService.registerHighlighter(isHighlighted);
		overlayManager.add(barrowsPlusOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		doors.clear();
		npcs.clear();
		npcOverlayService.unregisterHighlighter(isHighlighted);
		log.info("Example stopped!");

	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
		if (gameStateChanged.getGameState() == GameState.LOADING)
		{
			doors.clear();
		}
	}
	@Subscribe
	public void onNpcSpawned(NpcSpawned npcSpawned)
	{
		NPC npc = npcSpawned.getNpc();
		npcs.add(npc);
		highlightedNpcs.put(npc, renderNPC(npc));
	}
	/*@Subscribe
	public void onGameTick(GameTick tick)
	{
		log.info("tick");
		var npcs = client.getNpcs();
		for(NPC npc : npcs)
		{
			if(npc == null) {
				return;
			}
			renderNPC(npc);
		}
	}*/

	@Provides
    BarrowsPlusConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BarrowsPlusConfig.class);
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
	public void onWallObjectChanged(WallObjectChanged event)
	{
		WallObject previous = event.getPrevious();
		WallObject wallObject = event.getWallObject();

		doors.remove(previous);
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
	public HighlightedNpc renderNPC(NPC npc)
	{
		return HighlightedNpc.builder()
				.npc(npc)
				.highlightColor(Color.red)
				.hull(false)
				.tile(true)
				.swTile(false)
				.outline(true)
				.name(false)
				.nameOnMinimap(false)
				.borderWidth(1.09f)
				.render(n -> !n.isDead())
				.build();
	}
}
