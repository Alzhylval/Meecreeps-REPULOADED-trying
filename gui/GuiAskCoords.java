package mcjty.meecreeps.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import mcjty.meecreeps.teleport.PortalDestination;
import mcjty.meecreeps.items.PortalGunItem;

import java.util.List;

public class GuiCustomCoordinates extends GuiScreen {
    private GuiTextField textField;
    private GuiButton safeButton;
    private boolean safeCoords = false;
    private ItemStack gunStack;
    private int slot;  // Выбранный слот для сохранения

    public GuiCustomCoordinates(ItemStack stack, int slot) {
        this.gunStack = stack;
        this.slot = slot;
    }

    @Override
    public void initGui() {
        textField = new GuiTextField(0, fontRenderer, width / 2 - 100, height / 2 - 10, 200, 20);
        textField.setFocused(true);
        buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 + 20, 100, 20, "Готово"));
        safeButton = new GuiButton(1, width / 2 - 50, height / 2 + 50, 100, 20, "Safe coordinates: OFF");
        buttonList.add(safeButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            safeCoords = !safeCoords;
            safeButton.displayString = "Safe coordinates: " + (safeCoords ? "ON" : "OFF");
        } else if (button.id == 0) {
            String input = textField.getText();
            String[] parts = input.split(";");
            if (parts.length >= 4) {
                int dim = Integer.parseInt(parts[0]);
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                EnumFacing facing = parseDirection(parts.length > 4 ? parts[4] : "");  // Твоя функция parse (N/S/E/W/U/D)

                if (safeCoords) {
                    WorldServer world = MinecraftServer.getServer().getWorld(dim);  // Или worldServerForDimension
                    if (world != null) {
                        BlockPos ground = world.getTopSolidOrLiquidBlock(new BlockPos((int)x, 0, (int)z));
                        int groundY = ground.getY();
                        boolean valid = true;
                        for (int dy = 1; dy <= 3; dy++) {
                            if (!world.isAirBlock(new BlockPos((int)x, groundY + dy, (int)z))) {
                                valid = false;
                                break;
                            }
                        }
                        if (valid && (groundY + 1 - groundY <= 3)) {
                            y = groundY + 1;
                        } else {
                            // Поиск рядом
                            outer: for (int dx = -5; dx <= 5; dx++) {
                                for (int dz = -5; dz <= 5; dz++) {
                                    ground = world.getTopSolidOrLiquidBlock(new BlockPos((int)x + dx, 0, (int)z + dz));
                                    groundY = ground.getY();
                                    valid = true;
                                    for (int dy = 1; dy <= 3; dy++) {
                                        if (!world.isAirBlock(new BlockPos((int)x + dx, groundY + dy, (int)z + dz))) {
                                            valid = false;
                                            break;
                                        }
                                    }
                                    if (valid && Math.abs((groundY + 1) - ground.getY()) <= 3) {
                                        x += dx;
                                        y = groundY + 1;
                                        z += dz;
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }

                BlockPos pos = new BlockPos((int) Math.round(x), (int) Math.round(y), (int) Math.round(z));
                PortalDestination dest = new PortalDestination(pos, facing, dim);

                List<PortalDestination> dests = ((PortalGunItem) gunStack.getItem()).getDestinations(gunStack);
                dests.set(slot, dest);
                ((PortalGunItem) gunStack.getItem()).saveDestinations(gunStack, dests);
            }
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Введите: dim;x;y;z;dir (dir: N/S/E/W/U/D)", width / 2, height / 2 - 30, 0xFFFFFF);
        textField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    // keyTyped и mouseClicked как в GuiSelfDestruct
    // Добавь parseDirection метод, если нет: e.g., switch на строку to EnumFacing
    private EnumFacing parseDirection(String dir) {
        // Реализуй: N -> NORTH, etc. Default HORIZONTAL.
        return EnumFacing.NORTH;  // Пример
    }
}
