package net.adventureg147.giantpacman;

import net.adventureg147.giantpacman.block.ModBlocks;
import net.adventureg147.giantpacman.entity.ModEntityTypes;
import net.adventureg147.giantpacman.entity.render.GiantPacmanRenderer;
import net.adventureg147.giantpacman.item.ModItems;
import net.adventureg147.giantpacman.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GiantPacman.MOD_ID)
public class GiantPacman
{
    public static final String MOD_ID = "giantpacman";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public GiantPacman() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModBlocks.register(eventBus);
        ModSoundEvents.register(eventBus);

        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.PACMAN_TROPHY.get(), RenderType.cutout());
        });
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GIANT_PACMAN.get(), GiantPacmanRenderer::new);
    }
}
