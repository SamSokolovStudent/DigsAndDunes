package net.soko.digs_and_dunes.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.soko.digs_and_dunes.client.screen.component.GuiSliderButton;
import net.soko.digs_and_dunes.common.menu.PotteryTableMenu;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PotteryTableScreen extends AbstractContainerScreen<PotteryTableMenu> implements ContainerListener {

    public static final ResourceLocation POTTERY_LOCATION = new ResourceLocation(DigsAndDunes.MOD_ID, "textures/gui/container/pottery_table.png");
    private static final Object[] NO_ARGS = new Object[0];

    private static final int SCROLLER_WIDTH = 72;
    private static final int SCROLLER_HEIGHT = 8;
    private float scrolOffs;

    private static final Component MISSING_POT_OR_CLAY = Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_pot_or_clay", NO_ARGS).append("\n").append(Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_pot_or_clay.tooltip", NO_ARGS).withStyle(ChatFormatting.GRAY));
    private static final Component MISSING_DYE = Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_dye", NO_ARGS).append("\n").append(Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_dye.tooltip", NO_ARGS).withStyle(ChatFormatting.GRAY));
    private static final Component MISSING_SHERD_OR_BRICK = Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_sherd_or_brick", NO_ARGS).append("\n").append(Component.translatable("screen." + DigsAndDunes.MOD_ID + ".pottery_table.missing_sherd_or_brick.tooltip", NO_ARGS).withStyle(ChatFormatting.GRAY));

    private ItemStack potOrClayStack = ItemStack.EMPTY;
    private ItemStack dyeStack = ItemStack.EMPTY;
    private ItemStack sherd1Stack = ItemStack.EMPTY;
    private ItemStack sherd2Stack = ItemStack.EMPTY;
    private ItemStack sherd3Stack = ItemStack.EMPTY;
    private ItemStack sherd4Stack = ItemStack.EMPTY;
    private boolean validRecipe;
    private boolean displayPot;
    private boolean displayDye;
    private boolean enableSherds;
    private boolean enableScrolling;

    private DecoratedPotBlockEntity potEntity;
    private GuiSliderButton sliderButton;

    public PotteryTableScreen(PotteryTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 185;
    }


    @Override
    protected void init() {
        super.init();
        this.menu.addSlotListener(this);
        this.titleLabelX = 8;
        this.inventoryLabelY = 90;
        this.potEntity = new DecoratedPotBlockEntity(BlockPos.ZERO, Blocks.DECORATED_POT.defaultBlockState());
        sliderButton = addRenderableWidget(new GuiSliderButton(this.leftPos + 97, this.topPos + 81, SCROLLER_WIDTH, SCROLLER_HEIGHT));
    }

    @Override
    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        ItemStack potOrClayStack = this.menu.getInputSlot().getItem();
        ItemStack dyeStack = this.menu.getDyeSlot().getItem();

        if (potOrClayStack.isEmpty()) {
            this.displayPot = false;
            this.displayDye = true;
            this.enableSherds = true;
            this.sliderButton.active = false;
            this.validRecipe = false;
        } else if (potOrClayStack.is(Items.CLAY_BALL)) {
            this.displayPot = true;
            this.displayDye = false;
            this.enableSherds = true;
            this.sliderButton.active = true;
        } else if (potOrClayStack.is(Blocks.DECORATED_POT.asItem())) {
            this.displayPot = true;
            this.displayDye = true;
            this.enableSherds = false;
            this.sliderButton.active = true;
        }

        if (potOrClayStack.is(Items.CLAY_BALL) && dyeStack.isEmpty()) {
            this.validRecipe = true;
        } else if (potOrClayStack.is(Blocks.DECORATED_POT.asItem()) && allSherdStacksEmpty() && !dyeStack.isEmpty()) {
            this.validRecipe = true;
        } else {
            this.validRecipe = false;
        }


        int i = this.leftPos;
        int j = this.topPos;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, POTTERY_LOCATION);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(POTTERY_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(sliderButton != null ? (float) (sliderButton.getProgress() * 360f) : 0));
        poseStack.translate(i + 116, j + 12, 1);
        poseStack.scale(3.25F, 3.25F, 1.0F);
        guiGraphics.renderFakeItem(this.menu.getInputSlot().getItem(), 0, 0);
        poseStack.popPose();


        if (!validRecipe) {
            guiGraphics.blit(POTTERY_LOCATION, i + 36, j + 33, 176, 0, 26, 21);
        }
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        List<? extends FormattedCharSequence> failureToolTip = this.getFailureToolTip();
        if (this.isHovering(41, 36, 15, 15, x, y) && !failureToolTip.isEmpty()) {
            guiGraphics.renderTooltip(font, failureToolTip, x, y);
        }
    }

    public List<? extends FormattedCharSequence> getFailureToolTip() {
        if (this.potOrClayStack.isEmpty()) {
            return this.font.split(MISSING_POT_OR_CLAY, 200);
        }

        if (this.dyeStack.isEmpty() && this.potOrClayStack.is(Blocks.DECORATED_POT.asItem())) {
            return this.font.split(MISSING_DYE, 200);
        }

        if (this.sherd1Stack.isEmpty() || this.sherd2Stack.isEmpty() || this.sherd3Stack.isEmpty() || this.sherd4Stack.isEmpty() && this.potOrClayStack.is(Items.CLAY_BALL)) {
            return this.font.split(MISSING_SHERD_OR_BRICK, 200);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        for (GuiEventListener guiEventListener : children()) {
            if (guiEventListener.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
                return true;
            }
        }
        return false;
    }

    private boolean allSherdStacksEmpty() {
        ItemStack sherdStack1 = this.menu.getSherdSlot1().getItem();
        ItemStack sherdStack2 = this.menu.getSherdSlot2().getItem();
        ItemStack sherdStack3 = this.menu.getSherdSlot3().getItem();
        ItemStack sherdStack4 = this.menu.getSherdSlot4().getItem();

        return sherdStack1.isEmpty() && sherdStack2.isEmpty() && sherdStack3.isEmpty() && sherdStack4.isEmpty();
    }

    @Override
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {

    }

    @Override
    public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {

    }
}
