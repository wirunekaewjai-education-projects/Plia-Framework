package plia.event;

import plia.scene.Button;

public interface OnTouchListener
{
	void onTouch(Button button, int action, float x, float y);
}
