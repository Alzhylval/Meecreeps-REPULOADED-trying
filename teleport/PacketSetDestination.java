package mcjty.meecreeps.teleport;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PortalDestination {
    private BlockPos pos;
    private EnumFacing facing;
    private int dimension;

    public PortalDestination(BlockPos pos, EnumFacing facing, int dimension) {
        this.pos = pos;
        this.facing = facing;
        this.dimension = dimension;
    }

    public BlockPos getPos() { return pos; }
    public EnumFacing getFacing() { return facing; }
    public int getDimension() { return dimension; }

    public NBTTagCompound toNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("x", pos.getX());
        nbt.setInteger("y", pos.getY());
        nbt.setInteger("z", pos.getZ());
        nbt.setInteger("facing", facing.ordinal());
        nbt.setInteger("dim", dimension);
        return nbt;
    }

    public static PortalDestination fromNBT(NBTTagCompound nbt) {
        BlockPos pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        EnumFacing facing = EnumFacing.values()[nbt.getInteger("facing")];
        int dim = nbt.getInteger("dim");
        return new PortalDestination(pos, facing, dim);
    }
}
