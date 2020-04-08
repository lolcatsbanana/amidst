package amidst.mojangapi.minecraftinterface;

import java.util.function.Function;

import amidst.documentation.ThreadSafe;
import amidst.mojangapi.world.WorldType;

/**
 * Acts as an additional layer of abstraction for interfacing with Minecraft.
 * This allows for other sources of data other than direct reflection against a
 * loaded jar of Minecraft.
 *
 * Implementing classes need to be thread-safe!
 */
@ThreadSafe
public interface MinecraftInterface {

	public World createWorld(long seed, WorldType worldType, String generatorOptions) throws MinecraftInterfaceException;

	public RecognisedVersion getRecognisedVersion();

	@ThreadSafe
	public static interface World {

		/**
		 * @param useQuarterResolution Minecraft calculates biomes at
		 *            quarter-resolution, then noisily interpolates the biome-map up
		 *            to 1:1 resolution when needed, set useQuarterResolutionMap to
		 *            true to read from the quarter-resolution map, or false to read
		 *            values that have been interpolated up to full resolution.
		 *
		 *            When useQuarterResolution is true, the x, y, width, and height
		 *            paramaters must all correspond to a quarter of the Minecraft
		 *            block coordinates/sizes you wish to obtain the biome data for.
		 *
		 *            Amidst displays the quarter-resolution biome map, however full
		 *            resolution is required to determine the position and nature of
		 *            structures, as the noisy interpolation can change which biome
		 *            a structure is located in (if the structure is located on a
		 *            biome boundary).
		 *
		 * The biomeDataMapper callback is called with the biome data as an argument;
		 * this array is only valid for the scope of the closure and should not escape it.
		 * Any attempt to read the array once the callback returned may return wrong data.
		 */
		public<T> T getBiomeData(int x, int y, int width, int height,
				boolean useQuarterResolution, Function<int[], T> biomeDataMapper)
				throws MinecraftInterfaceException;
	}
}
