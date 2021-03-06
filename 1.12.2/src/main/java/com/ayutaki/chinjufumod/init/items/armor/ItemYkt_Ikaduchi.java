package com.ayutaki.chinjufumod.init.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.ayutaki.chinjufumod.init.ASDecoModWoods;
import com.ayutaki.chinjufumod.init.ChinjufuModArmor;
import com.ayutaki.chinjufumod.init.ChinjufuModArmorMaterial;
import com.ayutaki.chinjufumod.init.ChinjufuModItems;
import com.ayutaki.chinjufumod.init.items.armor.model.ModelYukataBody;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemYkt_Ikaduchi extends ItemArmor {

	public ItemYkt_Ikaduchi(int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(ChinjufuModArmorMaterial.IKADUCHIYKT, renderIndexIn, equipmentSlotIn);

	}

	/* アーマーモデル */
	@Override
	@SideOnly(Side.CLIENT) /* ←サーバーで処理されないように、Side.CLIENTを入れる */
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
			EntityEquipmentSlot armorSlot, ModelBiped _default) {

		if(!itemStack.isEmpty()) {

			if(itemStack.getItem() instanceof ItemArmor) {

				ModelYukataBody armorModel = new ModelYukataBody(1.0f);
				ModelYukataBody armorModelLegs = new ModelYukataBody(0.5f);

				armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST) || (armorSlot == EntityEquipmentSlot.CHEST);
				armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				armorModelLegs.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS) || (armorSlot == EntityEquipmentSlot.FEET);
				armorModelLegs.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS) || (armorSlot == EntityEquipmentSlot.FEET);

				armorModel.isSneak = _default.isSneak;
				armorModel.isRiding = _default.isRiding;
				armorModel.isChild = _default.isChild;
				armorModel.rightArmPose = _default.rightArmPose;
				armorModel.leftArmPose = _default.leftArmPose;

				armorModelLegs.isSneak = _default.isSneak;
				armorModelLegs.isRiding = _default.isRiding;
				armorModelLegs.isChild = _default.isChild;
				armorModelLegs.rightArmPose = _default.rightArmPose;
				armorModelLegs.leftArmPose = _default.leftArmPose;

				return armorModel;
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();

		if(this == ChinjufuModArmor.IKADUCHIYKT_HELMET) { }

		if(this == ChinjufuModArmor.IKADUCHIYKT_CHESTPLATE) {
			tooltip.add(I18n.format("tips.item.item_ykt.name", meta));
		}

		if(this == ChinjufuModArmor.IKADUCHIYKT_LEGGINGS) {
			tooltip.add(I18n.format("tips.item.item_ykt.name", meta));
		}
	}

	/* 金床で修理可能 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {

		if(this == ChinjufuModArmor.IKADUCHIYKT_HELMET) {
			return (repair.getItem() == Item.getItemFromBlock(ASDecoModWoods.KAEDE_SlabHalf));
		}

		if(this == ChinjufuModArmor.YKTD_BOOTS) {
			int k;
			k = repair.getMetadata();

			if(k == 5) {
				return (repair.getItem() == Item.getItemFromBlock(Blocks.WOODEN_SLAB)); }

			if(k != 5) { return false; }
		}

		return (repair.getItem() == ChinjufuModItems.TANMONO);
	}

}
