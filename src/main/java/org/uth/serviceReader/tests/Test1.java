package org.uth.serviceReader.tests;

import java.time.LocalTime;

import org.json.*;
import org.uth.serviceReader.utils.URLReader;

public class Test1
{
  public static void main( String args[] )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java Test1 targetURL");
      System.exit(0);
    }

    new Test1(args[0]);
  }

  public Test1( String targetURL )
  {
    log( "Pulling " + targetURL );
    String urlContents = URLReader.getContents(targetURL);

    //log( "Received " + urlContents );

    if( urlContents != null )
    {
      try
      {
        JSONObject json = new JSONObject( urlContents );

        //log( "JSON OBJECT: " + json );

        String[] names = JSONObject.getNames(json);

        for( String value : names )
        {
          log( "Found: " + value );

          if( value.equals("features"))
          {
            log( "IN FEATURES PROCESSING.");

            JSONArray features = json.getJSONArray("features");

            log( "  Found " + features.toList().size());

            for( int loop = 0; loop < features.length(); loop ++ )
            {
              JSONObject component = features.getJSONObject(loop);

              log( "    " + loop + " " + component.toString());
            }
          }
        }
      }
      catch( Exception exc )
      {
        log( "Exception occured " + exc.toString() );
      }
    }
  }

  private void log( String message )
  {
    LocalTime now = LocalTime.now();
    System.out.println( "[" + now + "] " + "(Test1) " + message );
  }
}