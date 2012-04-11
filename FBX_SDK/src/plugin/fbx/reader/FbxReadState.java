package plugin.fbx.reader;



interface FbxReadState
{
	public void start(FbxPlugin fbx);
	public void action(FbxPlugin fbx);
}

class FbxStateMachine
{
	private FbxPlugin owner;
	private FbxReadState state;
	
	public FbxStateMachine(FbxPlugin owner, FbxReadState beginState)
	{
		this.owner = owner;
		this.changeState(beginState);
	}
	
	public void action()
	{
		if(state != null)
		{
			state.action(owner);
		}
	}
	
	public void changeState(FbxReadState state)
	{
		this.state = state;
		
		if(this.state != null)
		{
//			this.state.start(owner);
		}
	}
}