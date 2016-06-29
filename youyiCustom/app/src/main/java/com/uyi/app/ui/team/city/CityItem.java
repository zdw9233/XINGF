package com.uyi.app.ui.team.city;


public class CityItem implements ContactItemInterface
{
	private String nickName;
	private String fullName;
	private int id;

	public CityItem(String nickName, String fullName,int id)
	{
		super();
		this.nickName = nickName;
		this.id = id;
		this.setFullName(fullName);
	}

	@Override
	public String getItemForIndex()
	{
		return fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getDisplayInfo()
	{
		return nickName;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	@Override
	public int getDisplayId() {
		
		return id;
	}

}
