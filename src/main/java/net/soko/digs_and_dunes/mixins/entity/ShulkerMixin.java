package net.soko.digs_and_dunes.mixins.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.spongepowered.asm.mixin.injection.callback.LocalCapture.CAPTURE_FAILHARD;

@Mixin(Shulker.class)
public class ShulkerMixin extends AbstractGolem {

    @Final
    @Shadow
    protected static final EntityDataAccessor<Byte> DATA_COLOR_ID = SynchedEntityData.defineId(ShulkerMixin.class, EntityDataSerializers.BYTE);

    protected ShulkerMixin(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(locals = CAPTURE_FAILHARD, method = "getColor", at = @At(shift = At.Shift.BEFORE, value = "RETURN"), cancellable = true)
    private void digsAndDunes$getColor(@NotNull CallbackInfoReturnable<DyeColor> cir, byte b0) {
        cir.setReturnValue(b0 <= 63 ? DyeColor.byId(b0) : null);
    }

    @Inject(locals = CAPTURE_FAILHARD, method = "defineSynchedData", at = @At(shift = At.Shift.BEFORE, value = "TAIL"))
    private void digsAndDunes$defineSynchedData(CallbackInfo ci) {
        this.entityData.set(DATA_COLOR_ID, (byte) 64);
    }
}
