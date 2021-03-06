/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 * <p>
 * Grimore Of Alice is Open Source and distributed under the
 * Grimore Of Alice license: https://github.com/ArekkuusuJerii/Grimore-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item.masks;

import arekkuusu.grimoireofalice.client.render.model.ModelKokorosMasks;
import arekkuusu.grimoireofalice.client.render.model.ModelMask;
import arekkuusu.grimoireofalice.client.util.ResourceLibrary;
import arekkuusu.grimoireofalice.common.lib.LibName;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.stream.Collectors;

public class ItemKokorosMasks extends ItemModMask {

	@SideOnly(Side.CLIENT)
	private ModelBiped model;

	private static final String OWNER_TAG = "GrimoireOwner";

	public ItemKokorosMasks(ArmorMaterial material, int dmg) {
		super(material, dmg, LibName.KOKORO_MASKS);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setUniqueId(OWNER_TAG, player.getUniqueID());
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.onUpdate(stack, world, entity, slot, selected);

		if(selected && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound compound = stack.getTagCompound();
			//noinspection ConstantConditions
			if(!compound.hasUniqueId(OWNER_TAG)) {
				compound.setUniqueId(OWNER_TAG, player.getUniqueID());
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(!armor.hasTagCompound()) {
			return;
		}

		if(player.getUniqueID().equals(armor.getTagCompound().getUniqueId(OWNER_TAG))) {
			if(player.moveForward > 0) {
				player.moveRelative(0F, 0F, 2F, 0.085F);
			}
			List<PotionEffect> badPotions = player.getActivePotionEffects().stream()
					.filter(potionEffect -> potionEffect.getPotion().isBadEffect()).collect(Collectors.toList());
			badPotions.forEach(potionEffect -> player.removePotionEffect(potionEffect.getPotion()));

			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 0, 3));
			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 0, 4));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 0, 4));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 0, 3));
		}
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(100, 100, 100);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return damageReduceAmount;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped imodel) {
		//noinspection ConstantConditions
		if(itemStack.hasTagCompound() && entityLiving.getUniqueID().equals(itemStack.getTagCompound().getUniqueId(OWNER_TAG))) {
			if(model == null || model instanceof ModelMask) {
				model = new ModelKokorosMasks();
			}
		} else if(model == null || model instanceof ModelKokorosMasks) {
			model = new ModelMask();
		}
		model.setModelAttributes(imodel);
		return model;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(!stack.hasTagCompound()) {
			return ResourceLibrary.MASK_OF_HOPE.toString();
		}

		//noinspection ConstantConditions
		if(entity.getUniqueID().equals(stack.getTagCompound().getUniqueId(OWNER_TAG))) {
			return ResourceLibrary.KOKOROS_MASKS.toString();
		} else {
			return ResourceLibrary.MASK_OF_HOPE.toString();
		}
	}
}
