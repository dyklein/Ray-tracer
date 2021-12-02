package renderer;

import java.util.MissingResourceException;

import elements.*;
import primitives.Color;
import primitives.Ray;

/**
 * create an image from the scene
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 *
 */
public class Render {
	private int threadsCount = 0;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage

	/**
	 * camera
	 */
	private Camera camera;
	/**
	 * image writer
	 */
	private ImageWriter imageWriter;
	/**
	 * abstract ray tracer
	 */
	private RayTracerBase rayTracer;

	/**
	 * set the camera
	 * 
	 * @param c camera
	 * @return this object
	 */
	public Render setCamera(Camera c) {
		this.camera = c;
		return this;
	}

	/**
	 * set the image writer
	 * 
	 * @param i image writer
	 * @return this object
	 */
	public Render setImageWriter(ImageWriter i) {
		this.imageWriter = i;
		return this;
	}

	/**
	 * set the ray tracer
	 * 
	 * @param r ray tracer
	 * @return this object
	 */
	public Render setRayTracer(RayTracerBase r) {
		this.rayTracer = r;
		return this;
	}

	/**
	 * For each pixel it will construct a ray and for each ray it will receive a
	 * color.
	 * 
	 * @throws MissingResourceException check if resources are missing
	 */
	public void renderImage() {
		// check that all the fields are not null.
		if (this.imageWriter == null) {
			throw new MissingResourceException("image Writer cant be null", "Render", "imageWriter");
		} else if (this.camera == null) {
			throw new MissingResourceException("camera cant be null", "Render", "camera");
		} else if (this.rayTracer == null) {
			throw new MissingResourceException("ray tracer cant be null", "Render", "rayTracer");
		}
		// loop over all the ViewPlane’s pixels
		int nY = this.imageWriter.getNy();
		int nX = this.imageWriter.getNx();
		if (threadsCount == 0) {
			for (int i = 0; i < nY; i++) {
				System.out.print(i + "\r\n");
				for (int j = 0; j < nX; j++) {
					castRay(nX, nY, j, i);
				}

			}
		} else {
			this.renderImageThreaded();
		}

	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object - with multi-threading
	 */
	private void renderImageThreaded() {
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		final Pixel thePixel = new Pixel(nY, nX);
		// Generate threads
		Thread[] threads = new Thread[threadsCount];
		for (int i = threadsCount - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel))
					castRay(nX, nY, pixel.col, pixel.row);
			});
		}
		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Print percents on the console
		thePixel.print();

		// Ensure all threads have finished
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}

		if (print)
			System.out.print("\r done 100%");
	}

	/**
	 * Cast ray from camera in order to color a pixel
	 * 
	 * @param nX  resolution on X axis (number of pixels in row)
	 * @param nY  resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 */
	private void castRay(int nX, int nY, int col, int row) {
		// construct a ray
		Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
		// for each ray it will receive a color
		// The color will be written in the appropriate pixel in the image using the
		// writePixel method
		imageWriter.writePixel(col, row, rayTracer.traceRay(ray));
	}

	/**
	 * creates a grid of lines
	 * 
	 * @param interval grid lines interval
	 * @param color    color
	 * @throws MissingResourceException check if the image writer is null
	 */
	public void printGrid(int interval, Color color) {
		// check if the image writer felid is not null
		if (this.imageWriter == null) {
			throw new MissingResourceException("Image writer can not be empty", "Render", "imageWriter");
		}
		// i = columns j = rows
		int nY = this.imageWriter.getNy();
		int nX = this.imageWriter.getNx();
		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; ++j) {
				// add color colum and rows of the grid lines
				if ((i % interval == 0) || (j % interval == 0)) {
					this.imageWriter.writePixel(j, i, color);
				}
			}
		}
	}

	/**
	 * Set multi-threading <br>
	 * - if the parameter is 0 - number of cores less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultiThreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("MultiThreading parameter must be 0 or higher");
		if (threads != 0)
			this.threadsCount = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			this.threadsCount = cores <= 2 ? 1 : cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		print = true;
		return this;
	}

	/**
	 * write the to a image
	 * 
	 * @throws IllegalArgumentException image writer can be null
	 * 
	 */
	public void writeToImage() {
		// check if the image writer felid is not null
		if (this.imageWriter == null) {
			throw new MissingResourceException("Image writer can not be empty", "Render", "imageWriter");
		}
		this.imageWriter.writeToImage();
	}

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multiThreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long maxRows = 0;
		private long maxCols = 0;
		private long pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long counter = 0;
		private int percents = 0;
		private long nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			this.maxRows = maxRows;
			this.maxCols = maxCols;
			this.pixels = (long) maxRows * maxCols;
			this.nextCounter = this.pixels / 100;
			if (Render.this.print)
				System.out.printf("\r %02d%%", this.percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++this.counter;
			if (col < this.maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (Render.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			++row;
			if (row < this.maxRows) {
				col = 0;
				target.row = this.row;
				target.col = this.col;
				if (Render.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percent = nextP(target);
			if (Render.this.print && percent > 0)
				synchronized (this) {
					notifyAll();
				}
			if (percent >= 0)
				return true;
			if (Render.this.print)
				synchronized (this) {
					notifyAll();
				}
			return false;
		}

		/**
		 * Debug print of progress percentage - must be run from the main thread
		 */
		public void print() {
			if (Render.this.print)
				while (this.percents < 100)
					try {
						synchronized (this) {
							wait();
						}
						System.out.printf("\r %02d%%", this.percents);
						System.out.flush();
					} catch (Exception e) {
					}
		}
	}

}
