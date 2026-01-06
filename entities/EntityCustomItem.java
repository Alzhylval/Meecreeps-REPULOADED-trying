package mcjty.meecreeps.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCustomItem extends EntityItem {
    public EntityCustomItem(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ItemStack stack = getItem();
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null && nbt.hasKey("selfDestructTicks")) {
            int ticks = nbt.getInteger("selfDestructTicks");
            if (ticks > 0) {
                ticks--;
                nbt.setInteger("selfDestructTicks", ticks);
            } else {
                world.createExplosion(null, posX, posY, posZ, 60f, true);
                setDead();
            }
        }
    }
}
