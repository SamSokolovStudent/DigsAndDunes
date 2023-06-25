package net.soko.digs_and_dunes.client.screen.component;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import org.jetbrains.annotations.NotNull;

public class GuiSliderButton extends AbstractWidget {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DigsAndDunes.MOD_ID, "textures/gui/container/scrollbar.png");
    private double progress;
    private boolean dragging;

    public GuiSliderButton(int x, int y, int width, int height) {
        super(x, y, width, height, CommonComponents.EMPTY);
    }


    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int thumbWidth = (int) (getWidth() * 15 / 70f);
        if (dragging) {
            if (active) {
                progress = Mth.clamp((mouseX - getX() - (thumbWidth / 2f)) / (float) (getWidth() - thumbWidth - 2), 0, 1);
            } else {
                dragging = false;
            }
        }
        graphics.blitNineSliced(TEXTURE, getX(), getY(), getWidth(), getHeight(), 1, 1, 1, 1, 256, 8, 0, 16);
        graphics.blitNineSliced(TEXTURE, getX() + 1 + (int) (progress * (getWidth() - thumbWidth - 2)), getY() + 1, thumbWidth, getHeight() - 2, 1, 1, 1, 1, 15, 6, !active ? 15 : 0, 0);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (isMouseOverThumb(pMouseX, pMouseY) && pButton == InputConstants.MOUSE_BUTTON_LEFT && active) {
            dragging = true;
            return true;
        }
        return false;
    }

    public boolean isMouseOverThumb(double mouseX, double mouseY) {
        int thumbWidth = (int) (getWidth() * 15 / 70f);
        return isMouseOver(mouseX, mouseY) && mouseX >= getX() + 1 + (int) (progress * (getWidth() - thumbWidth)) && mouseX <= getX() + 1 + (int) (progress * (getWidth() - thumbWidth)) + thumbWidth;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        dragging = false;
        return false;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }

    public double getProgress() {
        return progress;
    }
}
