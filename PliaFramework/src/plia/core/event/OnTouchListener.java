package plia.core.event;

import plia.core.scene.Button;

public interface OnTouchListener
{
	void onTouch(Button button, int action, float x, float y);
}
