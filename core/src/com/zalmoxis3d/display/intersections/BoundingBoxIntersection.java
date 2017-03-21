package com.zalmoxis3d.display.intersections;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by petre.popescu on 2017-03-20.
 */
public class BoundingBoxIntersection implements IIntersectionChecker {
    @Override
    public boolean intersects(Ray ray, DisplayObject displayObject) {
        return Intersector.intersectRayBoundsFast(ray, displayObject.getBounds());
    }
}
