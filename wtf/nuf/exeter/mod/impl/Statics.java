package wtf.nuf.exeter.mod.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.events.*;
import wtf.nuf.exeter.mcapi.eventsystem.EventManager;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mcapi.settings.*;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ModType;
import wtf.nuf.exeter.mod.ModValues;
import wtf.nuf.exeter.mod.impl.movement.Fly;
import wtf.nuf.exeter.printer.Printer;

import java.util.StringJoiner;

@ModValues(label = "Statics",
        aliases = {"statics", "static"},
        description = "Non-toggleable mod for non-toggleable implementations",
        visible = false,
        modType = ModType.MISCELLANEOUS)
public final class Statics extends Mod {
    private final ToggleableSetting hurtcam = new ToggleableSetting("Hurtcam", new String[]{"hurtcam", "hurt"}, false);
    private final ToggleableSetting fire = new ToggleableSetting("Fire", new String[]{"fire", "f"}, false);
    private final ToggleableSetting potions = new ToggleableSetting("Potions", new String[]{"potions", "potions"}, false);
    private final ToggleableSetting hand = new ToggleableSetting("Hand", new String[]{"hand", "h"}, true);
    private final ToggleableSetting pumpkins = new ToggleableSetting("Pumpkin", new String[]{"pumpkin", "p"}, false);

    public Statics() {
        settings.add(hurtcam);
        settings.add(pumpkins);
        settings.add(fire);
        settings.add(potions);
        settings.add(hand);
        EventManager.getInstance().register(this);
    }

    @Listener
    public void onPacket(PacketEvent event) {
        /**
         * this handles sending chat packets that start with the desired command prefix, then cancels the
         * sending of the packet if it starts with the prefix, then handles the command system
         * and the hardcoded settings commands >:)
         */
        if (event.getPacket() instanceof C01PacketChatMessage) {
            String message = ((C01PacketChatMessage) event.getPacket()).getMessage();
            if (message.startsWith(exeter.getSettings().commandPrefix.getValue())) {
                event.setCancelled(true);
                String[] input = message.trim().replace(exeter.getSettings().commandPrefix.getValue(), "").split(" ");

                Mod mod = Exeter.getInstance().getModManager().getMod(input[0]);
                if (mod != null) {
                    if (input[1] != null) {
                        Setting setting = mod.getSetting(input[1]);
                        if (setting != null) {
                            if (setting instanceof ToggleableSetting) {
                                ToggleableSetting toggleableSetting = (ToggleableSetting) setting;
                                toggleableSetting.toggle();
                                Printer.getPrinter().print(String.format("%s toggled %s%s%s for %s.",
                                        toggleableSetting.getLabel(),
                                        toggleableSetting.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED,
                                        toggleableSetting.isEnabled() ? "on" : "off",
                                        EnumChatFormatting.GRAY, mod.getLabel()));
                            } else if (setting instanceof StringSetting) {
                                StringSetting stringSetting = (StringSetting) setting;
                                if (input.length < 3) {
                                    Printer.getPrinter().print(String.format("%s%s %s \"value\"",
                                            exeter.getSettings().commandPrefix.getValue(), input[0], input[1]));
                                } else {
                                    String value = input[2];
                                    if (value.startsWith("\"") && value.endsWith("\"")) {
                                        value = value.replaceAll("\"", "");
                                        stringSetting.setValue(value);
                                        Printer.getPrinter().print(String.format("%s set to \"%s\" for %s.",
                                                stringSetting.getLabel(), value, mod.getLabel()));
                                    } else {
                                        Printer.getPrinter().print("Surround string values with quotes!");
                                    }
                                }
                            } else if (setting instanceof ValueSetting) {
                                ValueSetting valueSetting = (ValueSetting) setting;
                                if (input.length < 3) {
                                    Printer.getPrinter().print(String.format("%s%s %s <value>",
                                            exeter.getSettings().commandPrefix.getValue(), input[0], input[1]));
                                } else {
                                    if (valueSetting.getValue() instanceof Float) {
                                        valueSetting.setValue(Float.parseFloat(input[2]));
                                    } else if (valueSetting.getValue() instanceof Double) {
                                        valueSetting.setValue(Double.parseDouble(input[2]));
                                    } else if (valueSetting.getValue() instanceof Integer) {
                                        valueSetting.setValue(Integer.parseInt(input[2]));
                                    }
                                    Printer.getPrinter().print(String.format("%s set to %s for %s.",
                                            valueSetting.getLabel(), valueSetting.getValue(), mod.getLabel()));
                                }
                            } else if (setting instanceof ModeSetting) {
                                ModeSetting modeSetting = (ModeSetting) setting;
                                modeSetting.setValue(modeSetting.getMode(input[2]));
                                Printer.getPrinter().print(String.format("%s set to %s for %s.", modeSetting.getLabel(),
                                        modeSetting.getValue().name(), mod.getLabel()));
                            }
                        } else {
                            Printer.getPrinter().print(getSettings(mod));
                        }
                    } else {
                        Printer.getPrinter().print(getSettings(mod));
                    }
                } else {
                    Printer.getPrinter().print(exeter.getCommandManager().execute(input));
                }
            }
        } else if (event.getPacket() instanceof C13PacketPlayerAbilities) {
            /**
             * if fly is enabled this prevents you from telling the server that you are flying
             * (not tested)
             */
            C13PacketPlayerAbilities packetPlayerAbilities = (C13PacketPlayerAbilities) event.getPacket();
            Fly fly = (Fly) exeter.getModManager().getMod("fly");
            if (fly != null && fly.isEnabled()) {
                packetPlayerAbilities.setFlying(false);
            }
        } else if (event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            /**
             * prevents that snapping pitch shit when using kill aura
             * i.e. /watch?v=nJny9TooAJU @ 1:23
             */
            C03PacketPlayer.C06PacketPlayerPosLook packetPlayerPosLook = (C03PacketPlayer.C06PacketPlayerPosLook)
                    event.getPacket();
            if (minecraft.thePlayer.rotationYaw != -180F && minecraft.thePlayer.rotationPitch != 0F) {
                packetPlayerPosLook.setYaw(minecraft.thePlayer.rotationYaw);
                packetPlayerPosLook.setPitch(minecraft.thePlayer.rotationPitch);
            }
        } else if (event.getPacket() instanceof C14PacketTabComplete) {
            /**
             * apparently whenever you [tab] a players name into the chat whether it's for a server
             * command or a client command the server receives that command in full, this prevents that
             * (not tested)
             */
            C14PacketTabComplete packetTabComplete = (C14PacketTabComplete) event.getPacket();
            if (packetTabComplete.getMessage().startsWith(exeter.getSettings().commandPrefix.getValue())) {
                String[] message = packetTabComplete.getMessage().replace(exeter.getSettings().
                        commandPrefix.getValue(), "").split(" ");
                if (message.length > 1) {
                    packetTabComplete.setMessage(message[message.length - 1]);
                }
            }
        }
    }

    /**
     * middle click friends, check if you're hovering over something and then check if it's an entity,
     * if so and that entity is a player then add that player to our friends list >_>
     * @param event
     */
    @Listener
    public void onInput(InputEvent event) {
        if (event.getInputType() == InputEvent.InputType.MOUSE_MIDDLE_CLICK) {
            if (minecraft.objectMouseOver != null && minecraft.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                if (minecraft.objectMouseOver.entityHit instanceof EntityPlayer) {
                    EntityPlayer entityPlayer = (EntityPlayer) minecraft.objectMouseOver.entityHit;
                    if (exeter.getFriendManager().isFriend(entityPlayer.getName())) {
                        exeter.getFriendManager().unregister(entityPlayer.getName());
                        Printer.getPrinter().print(String.format("Removed %s from friends.", entityPlayer.getName()));
                    } else {
                        exeter.getFriendManager().register(entityPlayer.getName(), entityPlayer.getName());
                        Printer.getPrinter().print(String.format("Added %s as a friend.", entityPlayer.getName()));
                    }
                }
            }
        }
    }

    /**
     * rendering for the clients in-game gui, utilizes the click-gui api for this
     */
    @Listener
    public void onRender2D(Render2DEvent event) {
        if (minecraft.gameSettings.showDebugInfo) {
            return;
        }

        exeter.getOverlayManager().drawScreen(Mouse.getX(), Mouse.getY());
    }

    /**
     * these could be compiled into one event but fuck it i'm committed
     * @param event
     */
    @Listener
    public void onRenderHurtcam(RenderHurtcamEvent event) {
        if (!hurtcam.isEnabled()) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onRenderFire(RenderFireEvent event) {
        if (!fire.isEnabled()) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onRenderHand(RenderHandEvent event) {
        if (!hand.isEnabled()) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onRenderPotions(RenderPotionEvent event) {
        if (!potions.isEnabled()) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onRenderPumpkinOverlay(RenderPumpkinEvent event) {
        if (!pumpkins.isEnabled()) {
            event.setCancelled(true);
        }
    }

    /**
     * ugly but -_-
     * @param mod
     * @return formatted string with the mods settings
     */
    private String getSettings(Mod mod) {
        StringJoiner settingJoiner = new StringJoiner(", ");
        for (Setting setting1 : mod.getSettings()) {
            if (setting1 instanceof ToggleableSetting) {
                settingJoiner.add(String.format("%s[%s]", setting1.getLabel(),
                        ((ToggleableSetting) setting1).isEnabled()));
            } else if (setting1 instanceof StringSetting) {
                settingJoiner.add(String.format("%s[%s]", setting1.getLabel(),
                        ((StringSetting) setting1).getValue()));
            } else if (setting1 instanceof ModeSetting) {
                settingJoiner.add(String.format("%s[%s]", setting1.getLabel(),
                        ((ModeSetting) setting1).getValue().name()));
            } else if (setting1 instanceof ValueSetting) {
                settingJoiner.add(String.format("%s[%s]", setting1.getLabel(),
                        ((ValueSetting) setting1).getValue()));
            }
        }
        return String.format("%s Settings (%s) %s.", mod.getLabel(), mod.getSettings().size(), settingJoiner.toString());
    }
}
