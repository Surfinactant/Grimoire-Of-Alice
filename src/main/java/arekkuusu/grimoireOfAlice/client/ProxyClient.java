/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireOfAlice.client;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import arekkuusu.grimoireOfAlice.ProxyServer;
import arekkuusu.grimoireOfAlice.client.model.ModelDoll;
import arekkuusu.grimoireOfAlice.client.render.RenderExplosiveDoll;
import arekkuusu.grimoireOfAlice.client.render.RenderHolyKeyStone;
import arekkuusu.grimoireOfAlice.client.render.RenderNeedleDoll;
import arekkuusu.grimoireOfAlice.client.tile.TileEntityHolyKeyStone;
import arekkuusu.grimoireOfAlice.entity.mob.EntityAlicesDoll;
import arekkuusu.grimoireOfAlice.entity.projectile.EntityThrowingExplosiveDoll;
import arekkuusu.grimoireOfAlice.entity.projectile.EntityThrowingNeedleDoll;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyServer {

	@Override
	public void RegisterRenders() {
		//RenderingRegistry.registerEntityRenderingHandler(EntityTHDoll.class, new RenderLivingGOA(new ModelTHDoll(), LibResource.Doll_O));
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowingExplosiveDoll.class, new RenderExplosiveDoll());
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowingNeedleDoll.class, new RenderNeedleDoll());
		
		TileEntitySpecialRenderer render10 = new RenderHolyKeyStone();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolyKeyStone.class, render10);
	}
}