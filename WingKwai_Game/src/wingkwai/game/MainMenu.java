package wingkwai.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import plia.core.Game;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import plia.core.scene.Sprite;
import plia.core.scene.View;

public class MainMenu extends Game implements OnTouchListener
{
	private Scene scene;
	
	private Layer<View> bgLayer;
	private Layer<View> uiLayer;
	private Button bgBtnGroup;
	private Button create_btn;
	private Button load_btn;
	private Button credit_btn;
	private Button exit_btn;
	private Sprite bg;
	
	private Sprite createRaceLayout;
	private Button createRaceStart_btn;
	private Button createRaceBack_btn;
	
	private Button aiRadioBtnHierarchy;
	private Button[] aiRadioBtns;
	private Button aiNum;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		scene = new Scene();
		
		bg = sprite("ui/mainmenu.png");
		
		bgBtnGroup = button();
		load_btn = button();//"ui/load_btn.jpg");
		create_btn = button();//"ui/create_btn.jpg");
		credit_btn = button();//"ui/credit_btn.jpg");
		exit_btn = button();//"ui/exit_btn.jpg");
		
		aiRadioBtnHierarchy = button();
		
		aiNum = button("ui/radios/ai_num.png");
		aiNum.setScale(0.09f, 0.15f);
		aiNum.setCenter(0.19f, 0.59f);
		
		aiRadioBtns = new Button[6];
		aiRadioBtns[0] = button("ui/radios/num1.png");
		aiRadioBtns[1] = button("ui/radios/num1_chk.png");
		aiRadioBtns[2] = button("ui/radios/num2.png");
		aiRadioBtns[3] = button("ui/radios/num2_chk.png");
		aiRadioBtns[4] = button("ui/radios/num3.png");
		aiRadioBtns[5] = button("ui/radios/num3_chk.png");
		
		for (int i = 0; i < aiRadioBtns.length; i++)
		{
			aiRadioBtns[i].setScale(0.06f, 0.1f);
			
			float pad = ((int)(i / 2f)) * 0.1f;
			aiRadioBtns[i].setCenter(0.28f + pad, 0.6f);
			aiRadioBtns[i].setOnTouchListener(this);
			
			if(i == 1 || i == 3 || i == 5)
			{
				aiRadioBtns[i].setActive(false);
			}
		}
		
		aiRadioBtnHierarchy.addChild(aiNum);
		aiRadioBtnHierarchy.addChild(aiRadioBtns);
		
		createRaceStart_btn = button("ui/start_btn.png");
		createRaceStart_btn.setScale(0.1f, 0.0875f);
		createRaceStart_btn.setPosition(0.125f, 0.875f);

		createRaceBack_btn = button("ui/back_btn.png");
		createRaceBack_btn.setScale(0.1f, 0.0875f);
		createRaceBack_btn.setPosition(0.78f, 0.875f);
		
		createRaceLayout = sprite("ui/layout.png");
		createRaceLayout.setScale(0.8f, 1);
		createRaceLayout.setCenter(0.5f, 0.5f);
		
		createRaceLayout.addChild(createRaceStart_btn, createRaceBack_btn);
		createRaceLayout.addChild(aiRadioBtnHierarchy);
		createRaceLayout.setActive(false);
		
		load_btn.setScale(0.26f, 0.12f);
		create_btn.setScale(0.26f, 0.12f);
		credit_btn.setScale(0.26f, 0.12f);
		exit_btn.setScale(0.28f, 0.12f);
		
		load_btn.setCenter(0.235f, 0.34f);
		create_btn.setCenter(0.27f, 0.51f);
		credit_btn.setCenter(0.25f, 0.69f);
		exit_btn.setCenter(0.25f, 0.885f);
		
		create_btn.setOnTouchListener(this);
		load_btn.setOnTouchListener(this);
		credit_btn.setOnTouchListener(this);
		exit_btn.setOnTouchListener(this);
		createRaceStart_btn.setOnTouchListener(this);
		createRaceBack_btn.setOnTouchListener(this);

		bgBtnGroup.addChild(create_btn, load_btn, credit_btn, exit_btn);
		bg.addChild(bgBtnGroup);

		bgLayer = new Layer<View>();
		bgLayer.addChild(bg);
		
		uiLayer = new Layer<View>();

		scene.addLayer(bgLayer);
		scene.addLayer(uiLayer);
		
		setScene(scene);
	}

	public void onUpdate()
	{
		
	}

	private void chooseAiNumber(int index)
	{
		for (int i = 1; i < aiRadioBtns.length; i+=2)
		{
			aiRadioBtns[i].setActive(false);
		}
		
		aiRadioBtns[index].setActive(true);
	}

	private void resetAiNumber()
	{
		for (int i = 0; i < aiRadioBtns.length; i++)
		{
			if(i == 1 || i == 3 || i == 5)
			{
				aiRadioBtns[i].setActive(false);
			}
			else
			{
				aiRadioBtns[i].setActive(true);
			}
		}
	}
	
	public void onTouch(Button btn, int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_UP)
		{
			if(bgBtnGroup.isActive())
			{
				if(btn == create_btn)
				{
					if(!uiLayer.contains(createRaceLayout))
					{
						uiLayer.addChild(createRaceLayout);
						createRaceLayout.setActive(true);
						bgBtnGroup.setActive(false);
						
						resetAiNumber();
					}
				}
				else if(btn == exit_btn)
				{
					Game.exit();
				}
			}
			else
			{
				if(btn == createRaceStart_btn)
				{
					int aiCount = 0;
					
					for (int i = 0; i < aiRadioBtns.length; i++)
					{
						if(i == 1)
						{
							if(aiRadioBtns[i].isActive())
							{
								aiCount = 1;
								break;
							}
						}
						else if(i == 3)
						{
							if(aiRadioBtns[i].isActive())
							{
								aiCount = 2;
								break;
							}
						}
						else if(i == 5)
						{
							if(aiRadioBtns[i].isActive())
							{
								aiCount = 3;
								break;
							}
						}
					}
					
					Intent intent = new Intent(this, Stage1.class);
					intent.putExtra("AI Count", aiCount);
					startActivity(intent);
					
//					RaceScene raceScene = new RaceScene();
//					if(raceScene != getScene())
//					{
//						log("Set Scene");
//						setScene(raceScene);
//						
//					}
				}
				else if(btn == createRaceBack_btn)
				{
					if(uiLayer.contains(createRaceLayout))
					{
						uiLayer.removeChild(createRaceLayout);
						createRaceLayout.setActive(false);
						bgBtnGroup.setActive(true);
					}
				}
				else if(btn == aiRadioBtns[0])
				{
					chooseAiNumber(1);
				}
				else if(btn == aiRadioBtns[2])
				{
					chooseAiNumber(3);
				}
				else if(btn == aiRadioBtns[4])
				{
					chooseAiNumber(5);
				}
//				else if(btn == aiRadioBtns[1])
//				{
//					resetAiNumber();
//					aiRadioBtns[0].setActive(true);
//				}
//				else if(btn == aiRadioBtns[3])
//				{
//					resetAiNumber();
//					aiRadioBtns[2].setActive(true);
//				}
//				else if(btn == aiRadioBtns[5])
//				{
//					resetAiNumber();
//					aiRadioBtns[4].setActive(true);
//				}
			}
		}
	}
}
