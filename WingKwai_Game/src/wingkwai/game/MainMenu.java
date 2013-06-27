package wingkwai.game;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
	
	private Sprite profileLayout;
	private Button loadProfile, createNewProfile, backToMain;
	
	private Sprite[] mapPic;
	private Sprite createRaceLayout;
	private Button createRaceStart_btn;
	private Button createRaceBack_btn;
	
	private Button labRadioBtnHierarchy;
	private Button[] labRadioBtns;
	
	private Button aiRadioBtnHierarchy;
	private Button[] aiRadioBtns;

	private Button creditLayout;
	private Button deURL;
	
	private boolean isChangedScene = false;
	private boolean isExited = false;
	
	private int state = 0;
	
	private Sprite[] txt;
	
	private boolean isLoad = false;
	
	// Profile
	public static Profile profile;
	int profileData[] = new int[3];
	
	private Database db;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		db = new Database(this);
		profile = db.get();
		
		scene = new Scene();
		
		bg = sprite("ui/mainmenu.png");
		
		bgBtnGroup = button();
		load_btn = button();//"ui/load_btn.jpg");
		create_btn = button();//"ui/create_btn.jpg");
		credit_btn = button();//"ui/credit_btn.jpg");
		exit_btn = button();//"ui/exit_btn.jpg");
		
		profileLayout = sprite("ui/profile_layout.png");
		profileLayout.setScale(0.8f, 1);
		profileLayout.setCenter(0.5f, 0.5f);

		loadProfile = button();
		loadProfile.setPosition(0.75f, 0.75f);
		loadProfile.setScale(0.15f, 0.1f);
		
		createNewProfile = button();
		createNewProfile.setPosition(0.12f, 0.9f);
		createNewProfile.setScale(0.2f, 0.1f);
		
		backToMain = button();
		backToMain.setPosition(0.75f, 0.9f);
		backToMain.setScale(0.15f, 0.1f);
		
		txt = new Sprite[3];
		for (int i = 0; i < txt.length; i++)
		{
			txt[i] = new Sprite();
			txt[i].setPosition(0.4325f, 0.23f + (i * 0.2f));
		}
		
		profileLayout.addChild(loadProfile, createNewProfile, backToMain);
		profileLayout.addChild(txt);
		profileLayout.setActive(false);
		
		labRadioBtnHierarchy = button();
		aiRadioBtnHierarchy = button();

		labRadioBtns = new Button[6];
		labRadioBtns[0] = button("ui/radios/num1.png");
		labRadioBtns[1] = button("ui/radios/num1_chk.png");
		labRadioBtns[2] = button("ui/radios/num2.png");
		labRadioBtns[3] = button("ui/radios/num2_chk.png");
		labRadioBtns[4] = button("ui/radios/num3.png");
		labRadioBtns[5] = button("ui/radios/num3_chk.png");

		aiRadioBtns = new Button[6];
		aiRadioBtns[0] = button("ui/radios/num1.png");
		aiRadioBtns[1] = button("ui/radios/num1_chk.png");
		aiRadioBtns[2] = button("ui/radios/num2.png");
		aiRadioBtns[3] = button("ui/radios/num2_chk.png");
		aiRadioBtns[4] = button("ui/radios/num3.png");
		aiRadioBtns[5] = button("ui/radios/num3_chk.png");
		
		for (int i = 0; i < aiRadioBtns.length; i++)
		{
			labRadioBtns[i].setScale(0.1f, 0.1f);
			
			float pad = ((int)(i / 2f)) * 0.15f;
			labRadioBtns[i].setCenter(0.4f + pad, 0.55f);
			labRadioBtns[i].setOnTouchListener(this);
			
			if(i == 1 || i == 3 || i == 5)
			{
				labRadioBtns[i].setActive(false);
			}
			
			//
			
			aiRadioBtns[i].setScale(0.1f, 0.1f);
			aiRadioBtns[i].setCenter(0.4f + pad, 0.75f);
			aiRadioBtns[i].setOnTouchListener(this);
			
			if(i == 1 || i == 3 || i == 5)
			{
				aiRadioBtns[i].setActive(false);
			}
		}
		
//		labRadioBtnHierarchy.addChild(labNum);
		labRadioBtnHierarchy.addChild(labRadioBtns);
		
//		aiRadioBtnHierarchy.addChild(aiNum);
		aiRadioBtnHierarchy.addChild(aiRadioBtns);
		
		createRaceStart_btn = button("ui/start_btn.png");
		createRaceStart_btn.setScale(0.1f, 0.0875f);
		createRaceStart_btn.setPosition(0.125f, 0.875f);

		createRaceBack_btn = button("ui/back_btn.png");
		createRaceBack_btn.setScale(0.1f, 0.0875f);
		createRaceBack_btn.setPosition(0.78f, 0.875f);
		
		createRaceLayout = sprite("ui/createrace_layout.png");
		createRaceLayout.setScale(0.8f, 1);
		createRaceLayout.setCenter(0.5f, 0.5f);
		
		mapPic = new Sprite[1];
		mapPic[0] = sprite("ui/map01.jpg");
		mapPic[0].setScale(0.3f, 0.3f);
		mapPic[0].setCenter(0.5f, 0.3f);
		
		createRaceLayout.addChild(mapPic);
		
		creditLayout = button("ui/credit_layout.png");
		creditLayout.setScale(0.8f, 1);
		creditLayout.setCenter(0.5f, 0.5f);
		creditLayout.setActive(false);
		
		deURL = button();
		deURL.setScale(0.3f, 0.1f);
		deURL.setCenter(0.5f, 0.75f);
		deURL.setActive(false);
//		creditLayout.addChild(deURL);
		
		createRaceLayout.addChild(createRaceStart_btn, createRaceBack_btn);
		createRaceLayout.addChild(labRadioBtnHierarchy);
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
		
		
		createNewProfile.setOnTouchListener(this);
		loadProfile.setOnTouchListener(this);
		backToMain.setOnTouchListener(this);
		
		create_btn.setOnTouchListener(this);
		load_btn.setOnTouchListener(this);
		credit_btn.setOnTouchListener(this);
		exit_btn.setOnTouchListener(this);
		createRaceStart_btn.setOnTouchListener(this);
		createRaceBack_btn.setOnTouchListener(this);
		deURL.setOnTouchListener(this);
		creditLayout.setOnTouchListener(this);

		bgBtnGroup.addChild(create_btn, load_btn, credit_btn, exit_btn);
		bg.addChild(bgBtnGroup);

		bgLayer = new Layer<View>();
		bgLayer.addChild(bg);
		
		uiLayer = new Layer<View>();

		scene.addLayer(bgLayer);
		scene.addLayer(uiLayer);

		setScene(scene);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		isChangedScene = false;
		isExited = false;
	}
	
	

	public void onUpdate()
	{
		if(isLoad)
		{
			profileData[0] = profile.getMatch();
			profileData[1] = profile.getWin();
			profileData[2] = profile.getLose();
			
			for (int i = 0; i < txt.length; i++)
			{
				String textData = profileData[i]+"";
				txt[i].setImageSrc(text(textData, 60, Color.WHITE));
				txt[i].setScale(textData.length() * 0.0175f, 0.04f);
			}

			isLoad = false;
		}
	}


	private void chooseRadioButtonNumber(Button[] btn, int index)
	{
		for (int i = 1; i < btn.length; i+=2)
		{
			btn[i].setActive(false);
		}
		
		btn[index].setActive(true);
	}
	
	private int getRadioButtonPoint(Button[] btn)
	{
		for (int i = 0; i < btn.length; i++)
		{
			if(i == 1)
			{
				if(btn[i].isActive())
				{
					return 1;
				}
			}
			else if(i == 3)
			{
				if(btn[i].isActive())
				{
					return 2;
				}
			}
			else if(i == 5)
			{
				if(btn[i].isActive())
				{
					return 3;
				}
			}
		}
		
		return 0;
	}
	
	public void onTouch(Button btn, int action, float x, float y)
	{
		if(!isChangedScene)
		{
			if(action == TouchEvent.ACTION_UP)
			{
				if(state == 0)
				{
					if(btn == load_btn)
					{
						if(!uiLayer.contains(profileLayout))
						{
							uiLayer.addChild(profileLayout);
							profileLayout.setActive(true);
							bgBtnGroup.setActive(false);
							isLoad = true;
							state = 1;
						}
					}
					else if(btn == create_btn)
					{
						if(!uiLayer.contains(createRaceLayout))
						{
							uiLayer.addChild(createRaceLayout);
							createRaceLayout.setActive(true);
							bgBtnGroup.setActive(false);
							
							chooseRadioButtonNumber(aiRadioBtns, 1);
							chooseRadioButtonNumber(labRadioBtns, 1);
							
							state = 2;
						}
					}
					else if(btn == credit_btn)
					{
						state = 3;
						if(!uiLayer.contains(creditLayout))
						{
							uiLayer.addChild(deURL);
							uiLayer.addChild(creditLayout);
							
							creditLayout.setActive(true);
							deURL.setActive(true);
							bgBtnGroup.setActive(false);
						}
						
					}
					else if(btn == exit_btn)
					{
						isExited = true;
						Game.exit();
					}
				}
				else if(state == 1)
				{
					if(btn == backToMain)
					{
						if(uiLayer.contains(profileLayout))
						{
							uiLayer.removeChild(profileLayout);
							profileLayout.setActive(false);
							bgBtnGroup.setActive(true);
							
							state = 0;
						}
					}
					else if(btn == createNewProfile)
					{
						profile = new Profile();
						isLoad = true;
					}
					else if(btn == loadProfile)
					{
						isLoad = true;
					}
				}
				else if(state == 2)
				{
					if(btn == createRaceStart_btn)
					{
						isChangedScene = true;
						int aiCount = getRadioButtonPoint(aiRadioBtns);
						int labCount = getRadioButtonPoint(labRadioBtns);
						
						Intent intent = new Intent(this, Stage1.class);
						intent.putExtra("AI Count", aiCount);
						intent.putExtra("LAB Count", labCount);
						startActivity(intent);
					}
					else if(btn == createRaceBack_btn)
					{
						if(uiLayer.contains(createRaceLayout))
						{
							uiLayer.removeChild(createRaceLayout);
							createRaceLayout.setActive(false);
							bgBtnGroup.setActive(true);
							
							state = 0;
						}
					}
					else if(btn == aiRadioBtns[0])
					{
						chooseRadioButtonNumber(aiRadioBtns, 1);
					}
					else if(btn == aiRadioBtns[2])
					{
						chooseRadioButtonNumber(aiRadioBtns, 3);
					}
					else if(btn == aiRadioBtns[4])
					{
						chooseRadioButtonNumber(aiRadioBtns, 5);
					}
					else if(btn == labRadioBtns[0])
					{
						chooseRadioButtonNumber(labRadioBtns, 1);
					}
					else if(btn == labRadioBtns[2])
					{
						chooseRadioButtonNumber(labRadioBtns, 3);
					}
					else if(btn == labRadioBtns[4])
					{
						chooseRadioButtonNumber(labRadioBtns, 5);
					}
				}
				else if(state == 3)
				{
					if(btn == deURL && !isChangedScene)
					{
						isChangedScene = true;
						Uri uri = Uri.parse("http://www.dpu.ac.th/eng/ae/");
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					}
					else 
					{
						if(uiLayer.contains(creditLayout))
						{
							uiLayer.removeChild(creditLayout);
							uiLayer.removeChild(deURL);
							createRaceLayout.setActive(false);
							deURL.setActive(false);
							bgBtnGroup.setActive(true);

							state = 0;
						}
					}
					
				}
			}
		}
	}
	
	@Override
	protected void onDestroy()
	{
		scene = null;
		
		bgLayer = null;
		uiLayer = null;
		bgBtnGroup = null;
		create_btn = null;
		load_btn = null;
		credit_btn = null;
		exit_btn = null;
		bg = null;
		
		profileLayout = null;
		loadProfile = null;
		createNewProfile = null;
		backToMain = null;
		
		createRaceLayout = null;
		createRaceStart_btn = null;
		createRaceBack_btn = null;
		
		labRadioBtnHierarchy = null;
		labRadioBtns = null;
		
		aiRadioBtnHierarchy = null;
		aiRadioBtns = null;

		creditLayout = null;
		deURL = null;

		txt = null;

		super.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		if(isExited)
		{
			db.delete();
			db.add(profile);
			super.onBackPressed();
		}
	}
}
