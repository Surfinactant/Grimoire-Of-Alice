package arekkuusu.grimoireofalice.common.entity;

import java.util.List;

import arekkuusu.grimoireofalice.common.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntityDragonJewel extends Entity {

	private EntityLivingBase host;

	public EntityDragonJewel(World worldIn) {
		super(worldIn);
	}

	public EntityDragonJewel(World worldIn, EntityLivingBase player) {
		super(worldIn);
		host = player;
		ignoreFrustumCheck = true;
		preventEntitySpawning = true;
	}

	@Override
	protected void entityInit() {}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!worldObj.isRemote) {
			if (host == null) {
				setDead();
			} else {
				if (ticksExisted > 10 && (host.isSneaking() || host.isHandActive())) {
					stopEntity();
				}
			}
			if (ticksExisted > 500) {
				stopEntity();
			}
		}
		getEntities();
		if (ticksExisted % 50 == 0) {
			worldObj.playSound(null, posX, posY, posZ, SoundEvents.AMBIENT_CAVE, SoundCategory.NEUTRAL, 0.5F, 1F);
			for (int u = 0; u < 10; u++) {
				worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + 0.5, posY, posZ + 0.5, rand.nextDouble(), -0.1,
						rand.nextDouble());
				worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + 0.5, posY, posZ + 0.5, -rand.nextDouble(), -0.1,
						-rand.nextDouble());
				worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + 0.5, posY, posZ + 0.5, rand.nextDouble(), -0.1,
						-rand.nextDouble());
				worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + 0.5, posY, posZ + 0.5, -rand.nextDouble(), -0.1,
						rand.nextDouble());
			}
		}
	}

	private void getEntities() {
		AxisAlignedBB axis = new AxisAlignedBB(getPosition());
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(host, axis.expandXyz(20.0D));
		list.stream().filter(mob -> mob instanceof EntityMob).map(mob -> (EntityMob) mob).forEach(mob -> {
			mob.setAttackTarget(null);
			mob.setRevengeTarget(null);
			if(mob.worldObj.isRemote) {
				for (int i = 0; i < 2; ++i)
					mob.worldObj.spawnParticle(EnumParticleTypes.PORTAL
							, mob.posX + (rand.nextDouble() - 0.5D) * (double) mob.width, mob.posY + rand.nextDouble() * (double) mob.height - 0.25D, mob.posZ
									+ (rand.nextDouble() - 0.5D) * (double) mob.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble()
							, (rand.nextDouble() - 0.5D) * 2.0D);
			}
			if (mob.getHealth() > 1) mob.setHealth(1);
		});
	}

	private void stopEntity() {
		if (!worldObj.isRemote) {
			if (host != null && host instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) host;
				if (player.capabilities.isCreativeMode) {
					setDead();
					return;
				}
				ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.DRAGON_JEWEL));
			} else {
				dropItem(ModItems.DRAGON_JEWEL, 1);
			}
			setDead();
		}
	}

	public int getTicksAlive() {
		return ticksExisted;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}
}