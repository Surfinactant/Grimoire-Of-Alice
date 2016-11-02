/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.plugin.danmakucore.item;

import java.util.List;

import arekkuusu.grimoireofalice.entity.EntityMagicCircle;
import arekkuusu.grimoireofalice.item.ItemMod;
import arekkuusu.grimoireofalice.lib.LibItemName;
import net.katsstuff.danmakucore.entity.danmaku.DanmakuBuilder;
import net.katsstuff.danmakucore.helper.DanmakuCreationHelper;
import net.katsstuff.danmakucore.helper.DanmakuHelper;
import net.katsstuff.danmakucore.lib.LibColor;
import net.katsstuff.danmakucore.lib.data.LibShotData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShouLamp extends ItemMod {

	public ItemShouLamp() {
		super(LibItemName.SHOU_LAMP);
		setMaxStackSize(1);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
		list.add(TextFormatting.GOLD + I18n.format("grimoire.tooltip.shou_lamp_header.name"));
		if(GuiScreen.isShiftKeyDown()) {
			list.add(TextFormatting.ITALIC + I18n.format("grimoire.tooltip.shou_lamp_use.name"));
			list.add(TextFormatting.YELLOW + I18n.format("grimoire.tooltip.shou_lamp_goodbuff_others.name"));
			list.add(TextFormatting.YELLOW + I18n.format("grimoire.tooltip.shou_lamp_goodbuff_player.name"));
			list.add(TextFormatting.DARK_AQUA + I18n.format("grimoire.tooltip.shou_lamp_badbuff.name"));
		}
		else {
			list.add(TextFormatting.ITALIC + I18n.format("grimoire.tooltip.shou_lamp_shift.name"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(count % 5 == 0) {
			DanmakuHelper.playShotSound(player);
			DanmakuBuilder danmaku = DanmakuBuilder.builder()
					.setUser(player)
					.setMovementData(0.5D, 1.5D, 0.1D)
					.setShot(LibShotData.SHOT_LASER_LONG.setColor(LibColor.COLOR_SATURATED_YELLOW).setSizeZ(5))
					.build();

			DanmakuCreationHelper.createRandomRingShot(danmaku, 1, 5, 5);
		}
		player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 30, 0));
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entityLiving;
			int timeUsed = getMaxItemUseDuration(stack) - timeLeft;
			float convert = timeUsed * 6 / 20F;
			convert = (convert * convert + convert * 2.0F) / 3F;
			convert *= 1.5F;
			if(convert < 10F) {
				player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 125, 5));
				if(!worldIn.isRemote) {
					EntityMagicCircle circle = new EntityMagicCircle(worldIn, player, EntityMagicCircle.EnumTextures.BLUE_STAR, 125);
					worldIn.spawnEntityInWorld(circle);
				}
			}
			else {
				player.worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC, player.posX, player.posY, player.posZ, 0, 0, 0);
				List<EntityMob> list = worldIn.getEntitiesWithinAABB(EntityMob.class, player.getEntityBoundingBox().expandXyz(4.0D));
				for(EntityMob mob : list) {
					mob.addPotionEffect(new PotionEffect(MobEffects.LUCK, 125, 5));
					if(!mob.worldObj.isRemote) {
						EntityMagicCircle circle = new EntityMagicCircle(worldIn, mob, EntityMagicCircle.EnumTextures.GOLD_STAR_SMALL, 125);
						worldIn.spawnEntityInWorld(circle);
					}
				}
			}
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 500, 5));
		return false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
}
