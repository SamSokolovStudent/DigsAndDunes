package net.soko.digs_and_dunes.common.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.core.registry.ModItemTags;
import net.soko.digs_and_dunes.core.registry.ModMenuTypes;
import net.soko.digs_and_dunes.util.QuickMoveHelper;

import java.util.List;

//todo: implement recipes and make sherd sherd or brick, achievement idea: Don't be so groggy. Make a pot with sherds and clay. This is because grog is a material that is added to clay to make it stronger when incorporating old shards/sherds.
public class PotteryTableMenu extends AbstractContainerMenu {

    public static final ResourceLocation EMPTY_POT_OR_CLAY_SLOT = new ResourceLocation(DigsAndDunes.MOD_ID, "item/empty_pot_or_clay_slot");
    public static final ResourceLocation EMPTY_DYES_SLOT = new ResourceLocation(DigsAndDunes.MOD_ID, "item/empty_dyes_slot");
    public static final ResourceLocation EMPTY_SHERD_SLOT = new ResourceLocation(DigsAndDunes.MOD_ID, "item/empty_sherd_slot");

    private static final QuickMoveHelper MOVE_HELPER = new QuickMoveHelper().
            add(0, 7, 7, 36, true). // to Inventory
                    add(7, 36, 0, 6, false); // from Inventory

    private final ContainerLevelAccess access;
    private final Player player;
    private final Container craftSlots;
    private final PotteryResultContainer resultSlots = new PotteryResultContainer(1);
    private final Slot potOrClaySlot;
    private final Slot dyeSlot;
    private final Slot sherdSlot1;
    private final Slot sherdSlot2;
    private final Slot sherdSlot3;
    private final Slot sherdSlot4;
    private long lastSoundTime;
    private boolean pendingTake;

    public PotteryTableMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory);
    }

    public PotteryTableMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public PotteryTableMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.POTTERY_TABLE.get(), containerId);
        this.player = inventory.player;
        this.access = access;

        this.craftSlots = new SimpleContainer(6);

        this.potOrClaySlot = this.addSlot(new Slot(this.craftSlots, 0, 13, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.POTTERY_TABLE_INPUTS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_POT_OR_CLAY_SLOT);
            }
        });

        this.dyeSlot = this.addSlot(new Slot(this.craftSlots, 1, 13, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }

            @Override
            public int getMaxStackSize() {
                return 64;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_DYES_SLOT);
            }
        });

        this.sherdSlot1 = this.addSlot(new Slot(this.craftSlots, 2, 101, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTags.DECORATED_POT_INGREDIENTS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SHERD_SLOT);
            }
        });
        this.sherdSlot2 = this.addSlot(new Slot(this.craftSlots, 3, 101, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTags.DECORATED_POT_INGREDIENTS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SHERD_SLOT);
            }
        });
        this.sherdSlot3 = this.addSlot(new Slot(this.craftSlots, 4, 101, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTags.DECORATED_POT_INGREDIENTS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SHERD_SLOT);
            }
        });
        this.sherdSlot4 = this.addSlot(new Slot(this.craftSlots, 5, 101, 62) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTags.DECORATED_POT_INGREDIENTS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SHERD_SLOT);
            }
        });


        this.addSlot(new PotteryResultSlot(0, 69, 35));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }
    @Override
    public ItemStack quickMoveStack(Player playerIn, int slot) {
        return MOVE_HELPER.quickMoveStack(this, player, slot);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, ModBlocks.POTTERY_TABLE.get());
    }

    @Override
    public void addSlotListener(ContainerListener pListener) {
        super.addSlotListener(pListener);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((pLevel, pPos) -> this.clearContainer(pPlayer, this.craftSlots));
        if (this.pendingTake) {
            this.access.execute((pLevel, pPos) -> this.clearContainer(pPlayer, this.resultSlots));
        }
    }



    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 103 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 161));
        }
    }

    private void createResult() {
        if (this.craftSlots.getItem(0).isEmpty() || this.craftSlots.getItem(1).isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            ItemStack result = new ItemStack(Items.DECORATED_POT);
            result.setTag(new CompoundTag());
            result.getTag().putInt("Color", this.craftSlots.getItem(1).getTag().getInt("Color"));
            this.resultSlots.setItem(0, result);
        }
    }

    public Slot getInputSlot() {
        return this.slots.get(0);
    }

    public Slot getDyeSlot() {
        return this.slots.get(1);
    }

    public Slot getSherdSlot1() {
        return this.slots.get(2);
    }

    public Slot getSherdSlot2() {
        return this.slots.get(3);
    }

    public Slot getSherdSlot3() {
        return this.slots.get(4);
    }

    public Slot getSherdSlot4() {
        return this.slots.get(5);
    }

    public Slot getResultSlot() {
        return this.slots.get(6);
    }

    class PotteryResultSlot extends Slot {
        private int removeCount;

        public PotteryResultSlot(int index, int x, int y) {
            super(PotteryTableMenu.this.craftSlots, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }

        @Override
        public ItemStack remove(int pAmount) {
            if (this.hasItem())
                this.removeCount += Math.min(pAmount, this.getItem().getCount());

            return super.remove(pAmount);
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            if (PotteryTableMenu.this.pendingTake) {
                if (PotteryTableMenu.this.resultSlots.isEmpty()) {
                    this.setChanged();
                    PotteryTableMenu.this.pendingTake = false;
                    if (!player.level().isClientSide())
                        PotteryTableMenu.this.slotsChanged(PotteryTableMenu.this.craftSlots);
                }
            }

            if (PotteryTableMenu.this.resultSlots.getItem(0).isEmpty())
                PotteryTableMenu.this.pendingTake = true;
            PotteryTableMenu.this.potOrClaySlot.remove(1);
            //todo: only remove dye if there is a pot in potOrClaySlot and a dye in dyeSlot
            PotteryTableMenu.this.dyeSlot.remove(1);

            this.checkTakeAchievements(stack);
            super.onTake(player, stack);

            PotteryTableMenu.this.access.execute((level, pos) -> {
                long l = level.getGameTime();
                if (PotteryTableMenu.this.lastSoundTime != l) {
                    //todo: if player crafts pot play pot sound, if player crafts glazed pot play glazed pot sound
                    level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    PotteryTableMenu.this.lastSoundTime = l;
                }
            });
        }

        @Override
        protected void onQuickCraft(ItemStack pStack, int pAmount) {
            this.removeCount += pAmount;
            this.checkTakeAchievements(pStack);
        }

        @Override
        protected void checkTakeAchievements(ItemStack pStack) {
            if (this.removeCount > 0)
                pStack.onCraftedBy(PotteryTableMenu.this.player.level(), PotteryTableMenu.this.player, this.removeCount);

            if (this.container instanceof RecipeHolder)
                ((RecipeHolder) this.container).awardUsedRecipes(PotteryTableMenu.this.player, List.of(pStack));

            this.removeCount = 0;
        }
    }
}
