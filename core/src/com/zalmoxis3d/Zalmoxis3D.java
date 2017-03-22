package com.zalmoxis3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;
import com.zalmoxis3d.display.basicobjects.Cube;
import com.zalmoxis3d.display.basicobjects.Sphere;
import com.zalmoxis3d.display.text.Text2D;
import com.zalmoxis3d.display.text.Text3D;
import com.zalmoxis3d.event.EventDispatcher;
import com.zalmoxis3d.event.IEventFunction;
import com.zalmoxis3d.event.events.Event;
import com.zalmoxis3d.event.events.TouchEvent;

public class Zalmoxis3D extends ApplicationAdapter {
	private DisplayObject mainDisplayObject;
	private Stage stage;
	
	@Override
	public void create () {
		this.mainDisplayObject = new DisplayObject();
		stage = new Stage(mainDisplayObject);

		Sphere s1 = new Sphere(100, 100, new Material(ColorAttribute.createDiffuse(Color.BLUE)));
		s1.translate(100, 0, 0);

		Sphere s2 = new Sphere(100, 100, new Material(ColorAttribute.createDiffuse(Color.RED)));
		s2.translate(-100, 0, 0);

		Cube c1 = new Cube(30, 30, 30, new Material(ColorAttribute.createDiffuse(Color.GREEN)));

		DisplayObject container = new DisplayObject();
		container.addChild(s1);
		container.addChild(s2);
		container.addChild(c1);

		Text3D text3D = new Text3D("Something", 10);
		container.addChild(text3D);

		Text2D text2D = new Text2D("Some 2D Text");
		container.addChild(text2D);

		Text2D text2DRed = new Text2D("Some red text", Color.RED);
		text2DRed.translate(100, 0, 0);
		container.addChild(text2DRed);

		s1.addEventListener(TouchEvent.TOUCH_DOWN, new IEventFunction<TouchEvent>() {
			@Override
			public void dispatch(TouchEvent event) {
				System.out.println("S1 touched: " + event.getMouseButton());
			}
		});

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
