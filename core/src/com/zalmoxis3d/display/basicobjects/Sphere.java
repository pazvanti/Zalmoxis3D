package com.zalmoxis3d.display.basicobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by Petre Popescu on 29-Jan-17.
 */
public class Sphere extends DisplayObject {
    public Sphere(float size, int divisions, Material material) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(size, size, size, divisions, divisions,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
    public Sphere(float size, int divisions, Attribute... attributes) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(size, size, size, divisions, divisions,
                new Material(attributes),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
    public Sphere(float size, int divisions, Texture texture) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(size, size, size, divisions, divisions,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
    public Sphere(float width, float height, float depth, int divisionsU, int divisionsV, Material material) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(width, height, depth, divisionsU, divisionsV,
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
    public Sphere(float width, float height, float depth, int divisionsU, int divisionsV, Attribute... attributes) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(width, height, depth, divisionsU, divisionsV,
                new Material(attributes),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
    public Sphere(float width, float height, float depth, int divisionsU, int divisionsV, Texture texture) {
        super();
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(width, height, depth, divisionsU, divisionsV,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        this.modelInstance = new ModelInstance(model);
    }
}
