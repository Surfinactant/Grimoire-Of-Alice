/*
 * This class was created by <Katrix>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 *
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimore-Of-Alice/blob/master/LICENSE.md
 */
package arekkuusu.grimoireofalice.plugin.danmakucore;

import arekkuusu.grimoireofalice.plugin.danmakucore.form.GOAForms;
import arekkuusu.grimoireofalice.plugin.danmakucore.subentity.GOASubEntities;
import net.katsstuff.danmakucore.data.ShotData;
import net.katsstuff.danmakucore.lib.data.LibForms;

import static net.katsstuff.danmakucore.lib.LibColor.COLOR_VANILLA_RED;

public class LibGOAShotData {

	public static final ShotData WIND = new ShotData(GOAForms.WIND, COLOR_VANILLA_RED, 0.4F, 0.5F, 0, 50, GOASubEntities.WIND);
	public static final ShotData NOTE = new ShotData(LibForms.CONTROL, COLOR_VANILLA_RED, 0.4F, 0.5F, 0, 50, GOASubEntities.NOTE);
}