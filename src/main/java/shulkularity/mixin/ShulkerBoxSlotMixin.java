package shulkularity.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.container.ShulkerBoxSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ShulkerBoxSlot.class)
public class ShulkerBoxSlotMixin {
	/**
	 * @author vini2003
	 * Allow Shulker Boxes to be nested.
	 */
	@Overwrite
	public boolean canInsert(ItemStack stack) {
		ShulkerBoxSlot slot = (ShulkerBoxSlot) (Object) this;
		DefaultedList<ItemStack> stacks = DefaultedList.ofSize(slot.inventory.getInvSize() + 1, ItemStack.EMPTY);
		for (int i = 0; i < slot.inventory.getInvSize(); ++i) {
			stacks.set(i, slot.inventory.getInvStack(i));
		}
		stacks.set(slot.inventory.getInvSize(), stack);
		CompoundTag blockTag = Inventories.toTag(new CompoundTag(), stacks);
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeCompoundTag(blockTag);
		return !(buffer.array().length > 32767);
	}
}
