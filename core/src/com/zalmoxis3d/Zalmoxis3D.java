package com.zalmoxis3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;
import com.zalmoxis3d.display.basicobjects.Sphere;
import com.zalmoxis3d.display.text.Text3D;
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

		Sphere s1 = new Sphere(100, 100, new Material());
		s1.translate(100, 0, 0);

		Sphere s2 = new Sphere(100, 100, new Material());
		s2.translate(-100, 0, 0);

		DisplayObject container = new DisplayObject();
		container.addChild(s1);
		container.addChild(s2);

		Text3D text3D = new Text3D("Something", 10);
		container.addChild(text3D);

		s1.addEventListener(TouchEvent.TOUCH_DOWN, new IEventFunction<TouchEvent>() {
			@Override
			public void dispatch(TouchEvent event) {
				System.out.println("S1 touched: "+ event.getMouseButton());
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
