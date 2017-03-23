package com.zalmoxis3d.display.basicobjects.objects3D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.intersections.BoundingBoxIntersection;

/**
 * Created by petre.popescu on 2017-03-22.
 *
 * A basic 3D 6-sided rectangle that has a width, height, depth and material
 */
public class Cube extends DisplayObject {
    public Cube(float width, float height, float depth, Material material) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox(width, height, depth,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
        this.intersectionChecker = new BoundingBoxIntersection();
    }

    public Cube(float width, float height, float depth, Attribute... attributes) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox(width, height, depth,
                new Material(attributes),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
        this.intersectionChecker = new BoundingBoxIntersection();
    }

    public Cube(float width, float height, float depth, Texture texture) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox(width, height, depth,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
        this.intersectionChecker = new BoundingBoxIntersection();
    }
}
