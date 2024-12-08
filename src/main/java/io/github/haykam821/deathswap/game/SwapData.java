package io.github.haykam821.deathswap.game;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public record SwapData(Vec3d pos, Entity vehicle, List<Entity> passengers) {
	public void apply(ServerPlayerEntity player) {
		player.stopRiding();
		player.teleport(this.pos.getX(), this.pos.getY(), this.pos.getZ(), false);

		if (this.vehicle != null) {
			player.startRiding(this.vehicle, true);
		}

		for (Entity passenger : this.passengers) {
			passenger.startRiding(player, true);
		}
	}

	public static SwapData from(ServerPlayerEntity player) {
		return new SwapData(player.getPos(), player.getVehicle(), player.getPassengerList());
	}
}
