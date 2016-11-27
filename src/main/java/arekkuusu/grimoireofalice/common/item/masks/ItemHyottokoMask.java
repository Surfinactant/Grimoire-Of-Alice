/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item.masks;

import java.util.List;

import arekkuusu.grimoireofalice.common.lib.LibItemName;
import arekkuusu.grimoireofalice.common.lib.LibMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHyottokoMask extends ItemModMask {

	public ItemHyottokoMask(ArmorMaterial material, int dmg) {
		super(material, dmg, LibItemName.HYOTTOKO_MASK);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
		list.add(TextFormatting.DARK_AQUA + I18n.format("grimoire.tooltip.hyottoko_mask_header.name"));

		if(player.experienceLevel >= 70) {
			list.add(TextFormatting.GOLD + I18n.format("grimoire.tooltip.hyottoko_mask_good_buff_one.name"));
		}

		list.add(TextFormatting.GOLD + I18n.format("grimoire.tooltip.hyottoko_mask_good_buff_two.name"));
		list.add(TextFormatting.DARK_PURPLE + I18n.format("grimoire.tooltip.hyottoko_mask_vulnerable.name"));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(player.experienceLevel <= 70) {
			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 0, 2));
		}
		else {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 4));
			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 0, 4));
		}
	}

	@Override
	public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(player instanceof EntityPlayer && source.isMagicDamage()) {
			player.attackEntityFrom(DamageSource.generic, (float)damage * 2);
		}
		return new ArmorProperties(1, 5, 10);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if(source.isMagicDamage()) {
			stack.damageItem(damage * 10, entity);
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return LibMod.MODID + ":textures/models/armor/hyottokomask.png";
	}
}