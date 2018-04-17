package org.uth.serviceReader.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class URLReader
{
  public static final Integer URL_TIMEOUT = 60000;

  public static String getContents( String targetURL )
  {
    StringBuilder builder = new StringBuilder();

    URLConnection connection = null;
    InputStreamReader reader = null;

    try
    {
      URL url = new URL( targetURL );
      connection = url.openConnection();

      if( connection != null ) connection.setReadTimeout(URL_TIMEOUT);

      if( connection != null && connection.getInputStream() != null )
      {
        reader = new InputStreamReader( connection.getInputStream(), Charset.defaultCharset());
        BufferedReader stream = new BufferedReader( reader );

        if( stream != null )
        {
          int read = 0;
          while( ( read = stream.read()) != -1 )
          {
            builder.append( (char)read );
          }

          stream.close();
        }
      }

      reader.close();
    }
    catch( Exception exc )
    {
      exc.printStackTrace();
      return null;
    }

    return builder.toString();
  }
}