package mcjty.meecreeps.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import mcjty.meecreeps.items.PortalGunItem;
import mcjty.meecreeps.teleport.PortalDestination;

import java.util.List;

public class GuiWheel extends GuiScreen {
    private static final ResourceLocation WHEEL_TEX = new ResourceLocation("meecreeps:textures/gui/wheel.png");  // Твоя текстура для одного колеса (128x128?)
    private ItemStack gunStack;
    private EntityPlayer player;

    public GuiWheel(ItemStack stack, EntityPlayer player) {
        this.gunStack = stack;
        this.player = player;
    }

    @Override
    public void initGui() {
        // 4 колеса в 2x2 сетке
        int wheelSize = 80;  // Размер одного колеса
        int spacing = 10;    // Отступ
        int startX = (width - 2 * wheelSize - spacing) / 2;
        int startY = (height - 2 * wheelSize - spacing) / 2;

        for (int w = 0; w < 4; w++) {  // Колеса 0-3
            int baseSlot = w * 8;
            int wx = startX + (w % 2) * (wheelSize + spacing);
            int wy = startY + (w / 2) * (wheelSize + spacing);
            for (int i = 0; i < 8; i++) {  // 8 квадрантов на колесо
                // Рассчитай позицию кнопки в круге (пример: углы 45 градусов)
                double angle = Math.toRadians(i * 45);
                int btnX = wx + wheelSize / 2 + (int)(wheelSize / 3 * Math.cos(angle)) - 10;
                int btnY = wy + wheelSize / 2 - (int)(wheelSize / 3 * Math.sin(angle)) - 10;
                int slot = baseSlot + i;
                buttonList.add(new GuiButton(slot, btnX, btnY, 20, 20, "" + (i + 1)));
            }
        }
        buttonList.add(new GuiButton(32, width / 2 - 50, height / 2 + 120, 100, 20, "Закрыть"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 32) {
            mc.displayGuiScreen(null);
            return;
        }
        if (button.id >= 0 && button.id < 32) {
            int slot = button.id;
            // Открываем coord input для этого слота
            mc.displayGuiScreen(new GuiCustomCoordinates(gunStack, slot));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        mc.getTextureManager().bindTexture(WHEEL_TEX);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int wheelSize = 80;
        int spacing = 10;
        int startX = (width - 2 * wheelSize - spacing) / 2;
        int startY = (height - 2 * wheelSize - spacing) / 2;

        for (int w = 0; w < 4; w++) {
            int wx = startX + (w % 2) * (wheelSize + spacing);
            int wy = startY + (w / 2) * (wheelSize + spacing);
            // Рисуем текстуру колеса (предполагаем 128x128 текстура, масштабируем)
            drawScaledCustomSizeModalRect(wx, wy, 0, 0, 128, 128, wheelSize, wheelSize, 128, 128);
        }

        // Подписи слотов, подсветка
        List<PortalDestination> dests = ((PortalGunItem) gunStack.getItem()).getDestinations(gunStack);
        for (GuiButton btn : buttonList) {
            if (btn.id < 32) {
                PortalDestination dest = dests.get(btn.id);
                if (dest != null) {
                    btn.displayString = TextFormatting.GREEN + btn.displayString;
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
