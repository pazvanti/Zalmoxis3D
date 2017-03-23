package com.zalmoxis3d.event.events;

import com.zalmoxis3d.display.Stage;

/**
 * Created by petre.popescu on 2017-03-23.
 */
public class StageEvent extends Event{
    public static final String SHOW = "show";
    public static final String RESIZE = "resize";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String HIDE = "hide";

    private Stage stage = null;
    public StageEvent(Stage stage, String type) {
        super(type);
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }
}
