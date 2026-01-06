package mcjty.meecreeps.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class GuiSelfDestruct extends GuiScreen {
    private GuiTextField secondsField;
    private ItemStack gunStack;

    public GuiSelfDestruct(ItemStack stack) {
        this.gunStack = stack;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        secondsField = new GuiTextField(0, fontRenderer, width / 2 - 50, height / 2 - 10, 100, 20);
        secondsField.setFocused(true);
        secondsField.setText("10");
        buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 + 20, 100, 20, "Готово"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            try {
                int seconds = Integer.parseInt(secondsField.getText());
                if (seconds > 0 && seconds <= 60) {
                    NBTTagCompound nbt = gunStack.getOrCreateTagCompound();
                    nbt.setInteger("selfDestructTicks", seconds * 20);
                }
            } catch (NumberFormatException e) {}
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Введите секунды для самоуничтожения:", width / 2, height / 2 - 30, 0xFFFFFF);
        secondsField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (secondsField.isFocused()) {
            secondsField.textboxKeyTyped(typedChar, keyCode);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        secondsField.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
