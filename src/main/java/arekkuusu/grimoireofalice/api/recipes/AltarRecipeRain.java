package arekkuusu.grimoireofalice.api.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AltarRecipeRain extends AltarRecipe {

	public AltarRecipeRain(ItemStack result, Object... inputs) {
		super(result, inputs);
	}

	@Override
	public boolean checkRecipe(List<ItemStack> usedItems, World world) {
		return (world.isThundering() || world.isRaining()) && super.checkRecipe(usedItems, world);
	}
}
