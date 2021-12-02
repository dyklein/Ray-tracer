package unittests.special;

import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for Glossy Surface and Diffuse Glass
 * 
 * @author Daniel Klein
 * @author Yitzchak Meltz
 */
public class GlossyAndDiffuseSurfacesTest {
        private Scene scene = new Scene("Test scene");
        private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                        .setViewPlaneSize(200, 200).setDistance(1000);

        @Test
        public void createTenBodies() {
                scene.geometries.add( //
                                new Sphere(60, new Point3D(0, 0, -700)) //
                                                .setEmission(new Color(java.awt.Color.RED)) //
                                                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30)
                                                                .setKt(0).setKr(.9)), //

                                new Sphere(20, new Point3D(70, 40, -860)) //
                                                .setEmission(new Color(java.awt.Color.GREEN)) //
                                                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30)
                                                                .setKt(0.1).setKr(.9)), //

                                new Sphere(50, new Point3D(200, 50, -900)) //
                                                .setEmission(new Color(java.awt.Color.GRAY)) //
                                                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30)
                                                                .setKt(0).setKr(.9)), //

                                new Sphere(50, new Point3D(-150, 200, -800)) //
                                                .setEmission(new Color(java.awt.Color.YELLOW)) //
                                                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30)
                                                                .setKt(0).setKr(.9)), //

                                new Sphere(50, new Point3D(0, 100, -800)) //
                                                .setEmission(new Color(java.awt.Color.ORANGE)) //
                                                .setMaterial(new Material().setKd(0.8).setKs(0.8).setShininess(30)
                                                                .setKt(0).setKr(.5)), //

                                new Sphere(70, new Point3D(-200, -100, -730)) //
                                                .setEmission(new Color(java.awt.Color.PINK)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)), //

                                new Triangle(new Point3D(-60, -30, -170), new Point3D(-30, -60, 0),
                                                new Point3D(-28, -28, -4)) //
                                                                .setEmission(new Color(java.awt.Color.GREEN)) //
                                                                .setMaterial(new Material().setKd(0.4).setKs(0.4)
                                                                                .setShininess(30).setKt(.5).setKr(0)) //
                );

                scene.lights.add( //
                                new SpotLight(new Color(400, 240, 0), new Point3D(-390, -390, 400),
                                                new Vector(1, 1, -3)) //
                                                                .setKl(1E-5).setKq(1.5E-7));

                scene.lights.add( //
                                new SpotLight(new Color(400, 240, 0), new Point3D(390, -390, 400), new Vector(0, 0, -1)) //
                                                .setKl(1E-5).setKq(1.5E-7));

                scene.lights.add( //
                                new SpotLight(new Color(400, 240, 0), new Point3D(290, -490, .03),
                                                new Vector(0, 0, -200)) //
                                                                .setKl(1E-5).setKq(1.5E-7));

                RayTracerBasic tracer = new RayTracerBasic(scene);
                tracer.setNumOfRays(35);
                tracer.setRayDistance(100);

                Render render = new Render(). //
                                setImageWriter(new ImageWriter("createTenBodies", 400, 400)) //
                                .setCamera(camera) //
                                .setRayTracer(tracer).setMultiThreading(0).setDebugPrint();
                render.renderImage();
                render.writeToImage();
        }
}