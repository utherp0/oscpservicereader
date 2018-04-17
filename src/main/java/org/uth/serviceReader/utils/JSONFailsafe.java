package org.uth.serviceReader.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONFailsafe
{
  private JSONFailsafe() {}

  public static String getString( JSONObject target, String field )
  {
    try
    {
      return target.getString(field);
    }
    catch( JSONException exc )
    {
      return null;
    }
  }
}