package eaitask.targetsystem;

public enum TypeOfAccount {
	TRANSACTION, SAVINGS;
	
	public static String print(TypeOfAccount toa)
	{
		switch(toa)
		{
			case TRANSACTION:
			{
				return("Transaction");
			}
			case SAVINGS:
			{
				return("Savings");
			}
			
			default:
			{
				return("nd");
			}
		}
	}
}
