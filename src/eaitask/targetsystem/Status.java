package eaitask.targetsystem;

public enum Status {
	GOLD, SILBER, BRONZE;
	public static String print(Status status)
	{
		switch(status)
		{
			case GOLD:
			{
				return("Gold");
			}
			case SILBER:
			{
				return("Silber");
			}
			case BRONZE:
			{
				return("Bronze");
			}
			default:
			{
				return("nd");
			}
		}
	}
}
