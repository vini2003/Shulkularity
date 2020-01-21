package shulkularity.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerBoxBlockEntityMixin {
	/**
	 * @author vini2003
	 * Allow Shulker Boxes to be nested.
	 */
	@Overwrite
	public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
		ShulkerBoxBlockEntity blockEntity = (ShulkerBoxBlockEntity) (Object) this;
		CompoundTag blockTag = blockEntity.serializeInventory(new CompoundTag());
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeCompoundTag(blockTag);
		buffer.writeItemStack(stack);
		return !(buffer.array().length > 32767);
	}
}
