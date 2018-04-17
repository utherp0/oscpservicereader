package org.uth.serviceReader.tests;

import org.json.JSONObject;
import java.util.*;
import org.uth.serviceReader.openshift.data.Namespace;
import org.uth.serviceReader.utils.SecureURLReader;

public class NamespaceTest
{
  public static void main( String[] args )
  {
    if( args.length != 2 && args.length != 1 )
    {
      System.out.println( "Usage: java NamespaceTest <oscp_master_url>");
      System.out.println( "Usage: java NamespaceTest <oscp_master_url> <valid_token>");
      System.exit(0);
    }

    // If only one arg get the token from the $TESTENV environment variable
    String token = null;

    if( args.length == 1 )
    {
      token = System.getenv("TESTENV");

      if( token == null )
      {
        System.out.println( "No such environment variable TESTENV");
        System.exit(0);
      }
    }
    else
    {
      token = args[1];
    }

    new NamespaceTest( args[0], token );
  }

  public NamespaceTest( String oscpURL, String token )
  {
    long start = System.currentTimeMillis();

    // Exercising the two api options for Namespaces
    String allNamespaces = SecureURLReader.getContents(oscpURL, "api/v1/namespaces", token);

    long end = System.currentTimeMillis();

    if( allNamespaces == null )
    {
      System.out.println( "Test failed, unable to obtain Namespaces from " + oscpURL );
      System.exit(0);
    }

    // Check count of Namespaces found.
    JSONObject allNamespacesJSON = new JSONObject( allNamespaces );
    List<Namespace> oscpNamespaces = Namespace.extractNamespaces(allNamespacesJSON);

    if( oscpNamespaces == null )
    {
      System.out.println( "Unable to obtain Namespaces from JSON");
      System.exit(0);
    }

    System.out.println( "Found " + oscpNamespaces.size() + " namespaces at " + oscpURL );
    System.out.println( "Test took " + ( end - start ) + "ms." );

    for( Namespace namespace : oscpNamespaces )
    {
      System.out.println( "  " + namespace.getName() + " [" + namespace.getOwner() + "] " + namespace.getSelfLink());
    }
  }
}