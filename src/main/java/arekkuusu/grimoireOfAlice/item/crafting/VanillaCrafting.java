/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireOfAlice.item.crafting;

import arekkuusu.grimoireOfAlice.block.GOABlock;
import arekkuusu.grimoireOfAlice.handler.ConfigHandler;
import arekkuusu.grimoireOfAlice.item.GOAItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class VanillaCrafting {

	public static void BooksAndStrings() {

		GameRegistry.addRecipe(new ItemStack(GOAItem.itemVolatileString, 16, 1), 
				"GSG", 
				"SAS", 
				"GSG", 
				'S', Items.string, 'G', Items.gunpowder, 'A', Items.firework_charge);

		GameRegistry.addRecipe(new ItemStack(GOAItem.itemShimenawaRope, 1, 2), 
				"SSS", 
				"GAG", 
				"SSS", 
				'S', GOAItem.itemVolatileString, 'G', Blocks.log, 'A', Items.leather /*^GOAItem.ItemVolatileString*/);

		GameRegistry.addRecipe(new ItemStack(GOAItem.itemAlicesDoll, 1, 3), 
				"GSG", 
				"SAS", 
				"GSG", 
				'S', Items.string, 'G', Items.dye, 'A', Items.iron_ingot);

		if(ConfigHandler.RecipesBook) {
			GameRegistry.addRecipe(new ItemStack(GOAItem.itemGrimoireBook, 1, 4),
					"GSG", 
					"SAS", 
					"GSG", 
					'S', Items.sugar, 'G', Items.cake, 'A', Items.nether_star);
		}

	}

	public static void Blocks() {

		/**GameRegistry.addRecipe(new ItemStack(GOABlock.blockHolyKeyStone, 1, 5), 
				"GSG", 
				"SAS", 
				"GSG", 
				'S', GOAItem.itemShimenawaRope, 'G', Items.flint, 'A', Blocks.stone);*/
	}

	public static void ThrowEntity() {

		GameRegistry.addShapelessRecipe(new ItemStack(GOAItem.itemThrowingExplosiveDoll, 1, 6), GOAItem.itemAlicesDoll, Items.fire_charge);
	}
}