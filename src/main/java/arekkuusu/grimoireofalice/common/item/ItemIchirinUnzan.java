/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.common.item;

import arekkuusu.grimoireofalice.client.render.model.ModelIchirinUnzan;
import arekkuusu.grimoireofalice.client.util.ResourceLibrary;
import arekkuusu.grimoireofalice.common.entity.EntityUnzanFist;
import arekkuusu.grimoireofalice.common.lib.LibName;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIchirinUnzan extends ItemBaseArmor implements ISpecialArmor {

	@SideOnly(Side.CLIENT)
	private ModelIchirinUnzan model;

	public ItemIchirinUnzan(ArmorMaterial material, int dmg) {
		super(material, dmg, LibName.ICHIRIN_UNZAN, EntityEquipmentSlot.CHEST);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return damageReduceAmount;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		stack.damageItem(damage, entity);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!world.isRemote && ((player.ticksExisted % 4) == 0) && player.getCooldownTracker().hasCooldown(this)) {
			spawnFist(world, player, 45F);
			spawnFist(world, player, -45F);

			player.motionX = 0;
			player.motionY = 0;
			player.motionZ = 0;
			if(player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				playerMP.setPositionAndUpdate(playerMP.prevPosX, playerMP.posY, playerMP.prevPosZ);
			}
		}
	}

	private static void spawnFist(World world, EntityLivingBase player, float yaw) {
		Vec3d look = player.getLookVec().rotateYaw(yaw);
		float distance = 5F;
		double x = player.posX + look.x * distance;
		double y = player.posY + 1 + look.y * distance;
		double z = player.posZ + look.z * distance;

		EntityUnzanFist fist = new EntityUnzanFist(world, player);
		fist.setPosition(x, y, z);
		world.spawnEntity(fist);
		fist.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 2F, 50);
	}

	private static boolean isHoldingRight(EntityLivingBase player) {
		ItemStack main = player.getHeldItemMainhand();
		return !main.isEmpty() && main.getItem() == ModItems.ICHIRIN_RING;
	}

	private static boolean isHoldingLeft(EntityLivingBase player) {
		ItemStack off = player.getHeldItemOffhand();
		return !off.isEmpty() && off.getItem() == ModItems.ICHIRIN_RING;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped imodel) {
		if(model == null) {
			model = new ModelIchirinUnzan(0.05F);
		}
		model.setModelAttributes(imodel);
		model.setRenderRight(isHoldingRight(entityLiving));
		model.setRenderLeft(isHoldingLeft(entityLiving));
		return model;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ResourceLibrary.ICHIRIN_UNZAN.toString();
	}
}
