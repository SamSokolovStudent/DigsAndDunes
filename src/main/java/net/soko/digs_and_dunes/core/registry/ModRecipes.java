package net.soko.digs_and_dunes.core.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DigsAndDunes.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, DigsAndDunes.MOD_ID);

//    public static final Supplier<RecipeSerializer<?>> POTTERY = RECIPES.register("pottery", () -> new SimpleCraftingRecipeSerializer<>(PotteryRecipe::new));
}
