package com.zalmoxis3d.display.intersections;

import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by petre.popescu on 2017-03-20.
 *
 * This interface provides the needed structure to create an intersection checker
 * It is used to check item click/touch
 */
public interface IIntersectionChecker {
    public boolean intersects(Ray ray, DisplayObject displayObject);
}
