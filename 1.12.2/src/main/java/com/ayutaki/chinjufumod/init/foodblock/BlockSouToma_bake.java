package com.ayutaki.chinjufumod.init.foodblock;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.ayutaki.chinjufumod.handlers.SoundsHandler;
import com.ayutaki.chinjufumod.init.ASDecoModKitchen;
import com.ayutaki.chinjufumod.init.ChinjufuModFoodBlocks;
import com.ayutaki.chinjufumod.init.TTimeItems;
import com.ayutaki.chinjufumod.init.blocks.BlockFacingSapo;
import com.ayutaki.chinjufumod.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSouToma_bake extends BlockFacingSapo {

	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.25, 0.75, 0.125, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.25, 0.75, 0.125, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.25, 0.75, 0.125, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.25, 0.75, 0.125, 0.75);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };

	public BlockSouToma_bake()  {
		super(Material.WOOD);

		/*寸胴・フライパン*/
		this.setSoundType(SoundType.METAL);
		this.setHardness(1.0F);
		this.setResistance(5.0F);
	}

	/*湯気のエフェクト*/
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {

		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		World par1World = world;
		int par2 = i;
		int par3 = j;
		int par4 = k;
		Random par5Random = random;

		if (world.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.LIT_FURNACE || world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITSTOVE || world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITOVEN) {

			for (int la = 0; la < 1; ++la) {
				double d0 = (double) ((float) par2 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D;
				double d1 = ((double) ((float) par3 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D) + 0.5D;
				double d2 = (double) ((float) par4 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D;
				double d3 = 0.12D;
				double d4 = 0.17D;
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d4 + 0.25, d1 + d3 -0.5, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		/** 効果音 **/
		if (random.nextDouble() < 0.1D) {

			if (world.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.LIT_FURNACE || world
					.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITSTOVE || world
					.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITOVEN) {

				world.playSound(i, j, k, SoundsHandler.GUTSUGUTSU, SoundCategory.BLOCKS, 0.5F, 1.0F, false);
			}
		}
	}

	/* 右クリック操作 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack itemstack = playerIn.getHeldItem(hand);
		Item item = itemstack.getItem();

		if (item != TTimeItems.PASTA_sara) {
			return true;
		}

		else if (item == TTimeItems.PASTA_sara) {

			/*特定のアイテムを1個消費*/
			itemstack.shrink(1);
			worldIn.playSound(null, pos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.PLAYERS, 1.5F, 1.2F);

			if (itemstack.isEmpty()) {
				((EntityPlayer) playerIn).inventory
				.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ChinjufuModFoodBlocks.PASTATOMA_1)));

			}
			else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ChinjufuModFoodBlocks.PASTATOMA_1)))) {
				playerIn.dropItem(new ItemStack(Item.getItemFromBlock(ChinjufuModFoodBlocks.PASTATOMA_1)), false);
			}

			return worldIn.setBlockState(pos, ChinjufuModFoodBlocks.FRYPAN
					.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		return true;
	}

	/* 当たり判定 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {

		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		tooltip.add(I18n.format("tips.tile.block_food_sautoma_b.name", meta));
	}
}
