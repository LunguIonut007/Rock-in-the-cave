package com.lungunaiman.rockinthecave.GameClasses;


public class HighTime
{
    private static HighTime ourInstance;
    private long startTime;

    public static HighTime getInstance()
    {
        if(ourInstance==null)
        {
            ourInstance = new HighTime();
        }
        return ourInstance;
    }

    private HighTime()
    {
        startTime = System.currentTimeMillis();
    }

    public long getElapsedTime()
    {

        return  System.currentTimeMillis() - startTime;
    }

    public String getTime()
    {
        long seconds =  (getElapsedTime() / 1000) % 60 ;
        long minutes =  ((getElapsedTime() / (1000*60)) % 60);

        return ( minutes+" : "+seconds);
    }


}
