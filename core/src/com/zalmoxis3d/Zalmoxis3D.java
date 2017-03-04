package com.zalmoxis3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Material;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;
import com.zalmoxis3d.display.basicobjects.Sphere;

public class Zalmoxis3D extends ApplicationAdapter {
	private DisplayObject mainDisplayObject;
	private Stage stage;
	
	@Override
	public void create () {
		this.mainDisplayObject = new DisplayObject();
		stage = new Stage(mainDisplayObject);

		Sphere s1 = new Sphere(10, 10, 10, 10, 10, new Material());
		s1.translate(10, 0, 0);

		Sphere s2 = new Sphere(10, 10, 10, 10, 10, new Material());
		s2.translate(-10, 0, 0);

		DisplayObject container = new DisplayObject();
		container.addChild(s1);
		container.addChild(s2);

		container.translate(0, 10, -10);
		mainDisplayObject.addChild(container);
	}

	@Override
	public void render () {
		stage.render();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
