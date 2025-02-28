package com.direwolf20.charginggadgets.capabilities;

import com.direwolf20.charginggadgets.blocks.chargingstation.ChargingStationTile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ChargerItemHandler extends ItemStackHandler {
    private final ChargingStationTile chargingStationTile;

    public ChargerItemHandler(ChargingStationTile chargingStationTile) {
        super(2);
        this.chargingStationTile = chargingStationTile;
    }

    @Override
    protected void onContentsChanged(int slot) {
        chargingStationTile.setChanged();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot == ChargingStationTile.Slots.FUEL.getId() && stack.getItem() == Items.BUCKET)
            return super.insertItem(slot, stack, simulate);

        if (slot == ChargingStationTile.Slots.FUEL.getId() && ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0)
            return stack;

        if (slot == ChargingStationTile.Slots.CHARGE.getId() && (! stack.getCapability(CapabilityEnergy.ENERGY).isPresent() || getStackInSlot(slot).getCount() > 0))
            return stack;

        return super.insertItem(slot, stack, simulate);
    }
}
