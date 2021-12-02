package unittests.renderer;

import org.junit.Test;
import primitives.*;
import renderer.*;

/**
 * Testing Image writer
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class ImageWriterTest {
	/**
	 * test writing image to folder {@link renderer.ImageWriter#writeToImage()}
	 */

	@Test
	public void writeImageTest() {
		// TC01: Simple grid test
		String imageName = "One color with a grid in a different color";

		// view plane 500*800
		int nX = 800;
		int nY = 500;

		// color blue
		Color blue = new Color(0, 0, 255);

		ImageWriter imageWriter = new ImageWriter(imageName, nX, nY);

		// i = columns j = rows
		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; ++j) {
				// add black colum and rows
				if ((i % 50 == 0 && i > 0) || (j % 50 == 0 && j > 0)) {
					imageWriter.writePixel(j, i, Color.BLACK);
				} else {
					imageWriter.writePixel(j, i, blue);
				}
			}
		}
		imageWriter.writeToImage();
	}
}
