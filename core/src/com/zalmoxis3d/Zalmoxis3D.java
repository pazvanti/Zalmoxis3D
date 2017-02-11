package com.zalmoxis3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;

public class Zalmoxis3D extends ApplicationAdapter {
	private DisplayObject mainDisplayObject;
	private Stage stage;
	
	@Override
	public void create () {
		this.mainDisplayObject = new DisplayObject();
		stage = new Stage(mainDisplayObject);
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
