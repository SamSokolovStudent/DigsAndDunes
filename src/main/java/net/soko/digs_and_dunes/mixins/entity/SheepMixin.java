package net.soko.digs_and_dunes.mixins.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.util.ModDyeColors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.EnumMap;
import java.util.Objects;

import static net.minecraft.network.syncher.SynchedEntityData.defineId;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal {

    @Inject(method = "lambda$static$0", at = @At("HEAD"), remap = false)
    private static void digsAndDunes$addNewWoolTypes(EnumMap<DyeColor, ItemLike> woolMap, CallbackInfo ci) {
        woolMap.put(ModDyeColors.digsAndDunes$MAROON, ModBlocks.MAROON_WOOL.get());
        woolMap.put(ModDyeColors.digsAndDunes$OCHRE, ModBlocks.OCHRE_WOOL.get());
    }

    protected SheepMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract DyeColor getColor();

    @Final
    @Shadow
    private static final EntityDataAccessor<Byte> DATA_WOOL_ID = defineId(SheepMixin.class, EntityDataSerializers.BYTE);

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getColor", at = @At(shift = At.Shift.BEFORE, value = "RETURN"), cancellable = true
    )
    private void digsAndDunes$getColor(CallbackInfoReturnable<DyeColor> cir) {
        cir.setReturnValue(DyeColor.byId(this.entityData.get(DATA_WOOL_ID)));
    }

    @Shadow
    public abstract boolean isSheared();

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getDefaultLootTable", at = @At(value = "HEAD"), cancellable = true)
    private void digsAndDunes$getDefaultLootTable(CallbackInfoReturnable<ResourceLocation> cir) {
        if (!this.isSheared()) {
            if (Objects.requireNonNull(this.getColor()) == ModDyeColors.digsAndDunes$MAROON) {
                cir.setReturnValue(new ResourceLocation(DigsAndDunes.MOD_ID, "entities/sheep/maroon"));
            }
            if (Objects.requireNonNull(this.getColor()) == ModDyeColors.digsAndDunes$OCHRE) {
                cir.setReturnValue(new ResourceLocation(DigsAndDunes.MOD_ID, "entities/sheep/ochre"));
            }
        }
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "setColor", at = @At(value = "HEAD"), cancellable = true)
    public void digsAndDunes$setColor(DyeColor p_29856_, CallbackInfo ci) {
        byte b0 = this.entityData.get(DATA_WOOL_ID);
        byte data = (byte) p_29856_.getId();
        this.entityData.set(DATA_WOOL_ID, data);
        ci.cancel();
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "isSheared", at = @At(shift = At.Shift.BEFORE, value = "RETURN"), cancellable = true)
    private void digsAndDunes$isSheared(CallbackInfoReturnable<Boolean> cir) {
        int data = (this.entityData.get(DATA_WOOL_ID) & 64);
        cir.setReturnValue(data != 0);
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "setSheared", at = @At(value = "HEAD"), cancellable = true)
    private void digsAndDunes$setSheared(boolean pSheared, CallbackInfo ci) {
        byte b0 = this.entityData.get(DATA_WOOL_ID);
        if (pSheared) {
            this.entityData.set(DATA_WOOL_ID, (byte) (b0 | 64));
            ci.cancel();
        } else {
            this.entityData.set(DATA_WOOL_ID, (byte) (b0 & -(64 + 1)));
            ci.cancel();
        }
    }
}
