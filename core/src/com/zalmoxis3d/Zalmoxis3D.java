package com.zalmoxis3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;
import com.zalmoxis3d.display.basicobjects.objects3D.Sphere;
import com.zalmoxis3d.display.text.Text3D;
import com.zalmoxis3d.event.events.TouchEvent;

public class Zalmoxis3D extends ApplicationAdapter {
	private DisplayObject mainDisplayObject;
	private Stage stage;
	
	@Override
	public void create () {
		this.mainDisplayObject = new DisplayObject();
		stage = Stage.getInstance();
		stage.init(mainDisplayObject);

		Text3D text3D = new Text3D("test", Color.RED, 5);
		mainDisplayObject.addChild(text3D);

		Sphere s1 = new Sphere(50, 50, new Material());
		mainDisplayObject.addChild(s1);
		s1.translate(200, 100, 0);

		s1.addEventListener(TouchEvent.TOUCH_UP, (event) -> {
			text3D.setText("New text");
			text3D.setColor(Color.BLUE);
			text3D.setScale(8);
		});

		stage.zoomOut(100);
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
