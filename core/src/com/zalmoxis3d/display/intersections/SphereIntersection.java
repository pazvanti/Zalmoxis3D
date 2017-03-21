package com.zalmoxis3d.display.intersections;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by petre.popescu on 2017-03-21.
 *
 * Checks that a Ray intersects with a shpere of the same width as the Display Object. Used primary to check that a
 * 3D Object is clicked/touched
 */
public class SphereIntersection implements IIntersectionChecker{
    @Override
    public boolean intersects(Ray ray, DisplayObject displayObject) {
        return Intersector.intersectRaySphere(ray, displayObject.getCoordinates(), displayObject.getBounds().getWidth(), null);
    }
}
