package org.uth.serviceReader.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SecureTest1
{
  public static final Integer URL_TIMEOUT = 60000;

  public static void main( String[] args )
  {
    if( args.length != 2 )
    {
      System.out.println( "Usage: java SecureTest1 serviceURL token" );
      System.exit(0);
    }

    new SecureTest1( args[0], args[1] );
  }

  public SecureTest1( String targetURL, String authToken )
  {
    StringBuilder builder = new StringBuilder();

    HttpURLConnection connection = null;
    InputStreamReader reader = null;

    try
    {
      // Create a trust manager that does not validate certificate chains
      TrustManager[] trustAllCerts = new TrustManager[] 
      { 
        new X509TrustManager() 
        {     
          public java.security.cert.X509Certificate[] getAcceptedIssuers() 
          { 
            return new java.security.cert.X509Certificate[0];
          }

          public void checkClientTrusted( java.security.cert.X509Certificate[] certs, String authType) 
          {
          }

          public void checkServerTrusted(  java.security.cert.X509Certificate[] certs, String authType ) 
          {
          }
        } 
      }; 

      SSLContext sc = SSLContext.getInstance("SSL"); 
      sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      URL url = new URL( targetURL );
      connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Authorization", "Bearer " + authToken );

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

        System.out.println( builder.toString());
      }

      reader.close();
    }
    catch( Exception exc )
    {
      exc.printStackTrace();
      System.out.println( "Test failed due to " + exc.toString() );
    }    
  }
}