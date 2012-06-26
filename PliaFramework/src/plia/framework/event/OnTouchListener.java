package plia.framework.event;

import plia.framework.scene.view.Button;

public interface OnTouchListener
{
	void onTouch(Button button, int action, float x, float y);
}
