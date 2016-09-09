/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.block;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import arekkuusu.grimoireofalice.lib.LibBlockName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHolyKeyStone extends BlockMod {
	
	BlockHolyKeyStone() {
		super(LibBlockName.HOLYKEY, Material.ROCK);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setSoundType(SoundType.STONE);
		setResistance(15.0F);
		setLightLevel(0.5F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
		list.add(TextFormatting.GOLD + "Heavy object made of black granite");
		list.add(TextFormatting.ITALIC + "Step on it to activate");
	}

	@Override
	public boolean getTickRandomly() {
        return true;
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		Optional<EntityPlayer> optPlayer = getPlayerInRange(world, pos);
		if(optPlayer.isPresent()) {
			EntityPlayer player = optPlayer.get();
			addPlayerEffect(player);
			ifNear(world, pos, rand);
		}
	}

	private void addPlayerEffect(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50, 3));
	}

	private void ifNear(World world, BlockPos pos, Random rand) {
		if(world.isRemote) {
			int xCoord = pos.getX();
			int yCoord = pos.getY();
			int zCoord = pos.getZ();
			double d0 = 0.0625D;
			for(int l = 0; l < 6; ++l) {
				double d1 = xCoord + rand.nextFloat();
				double d2 = yCoord + rand.nextFloat();
				double d3 = zCoord + rand.nextFloat();
				if(l == 0 && !(world.getBlockLightOpacity(new BlockPos(xCoord, yCoord + 1, zCoord))==0)) d2 = yCoord + 1 + d0;
				if(l == 1 && !(world.getBlockLightOpacity(new BlockPos(xCoord, yCoord - 1, zCoord))==0)) d2 = yCoord - d0;
				if(l == 2 && !(world.getBlockLightOpacity(new BlockPos(xCoord, yCoord, zCoord + 1))==0)) d3 = zCoord + 1 + d0;
				if(l == 3 && !(world.getBlockLightOpacity(new BlockPos(xCoord, yCoord - 1, zCoord))==0)) d3 = zCoord - d0;
				if(l == 4 && !(world.getBlockLightOpacity(new BlockPos(xCoord + 1, yCoord, zCoord))==0)) d1 = xCoord + 1 + d0;
				if(l == 5 && !(world.getBlockLightOpacity(new BlockPos(xCoord - 1, yCoord, zCoord))==0)) d1 = xCoord - d0;
				if(d1 < xCoord || d1 > xCoord + 1 || d2 < 0.0D || d2 > yCoord + 1 || d3 < zCoord || d3 > zCoord + 1) {
					world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d1, d2, d3, 0.0D, 0.0D, 0.0D, 0);
				}
			}
		}
	}

	private Optional<EntityPlayer> getPlayerInRange(World world, BlockPos pos) {
		if(world.isRaining()) {
			return Optional.ofNullable(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 3, false));
		}

		return Optional.empty();
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.createExplosion(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 2.0F, false);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entityIn;
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 75, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50, 2));
		}
	}
}