package org.uth.serviceReader.openshift.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Generic data object - fields/arrays are stored in a map for name based access
 * without using basic accessors.
 */
public class Generic
{
  private Map<String,String> _atomics = null;
  private Map<String, Generic> _generics = null;

  public Generic() 
  {
    _atomics = new HashMap<String,String>();
    _generics = new HashMap<String,Generic>();
  }

  public boolean addAtomic( String name, String value )
  {
    if( _atomics.containsKey(name)) return false;

    _atomics.put(name,value);
    return true;
  }

  public boolean addGeneric( String name, Generic generic )
  {
    if( _generics.containsKey(name)) return false;

    _generics.put(name,generic);
    return true;
  }

  public Set<String> getAtomicsKeys()
  {
    return _atomics.keySet();
  }

  public Set<String> getGenericsKeys()
  {
    return _generics.keySet();
  }

  public String getAtomicValue( String name )
  {
    if( !( _atomics.containsKey(name))) return null;

    return _atomics.get(name);
  }

  public Generic getGeneric( String name )
  {
    if( !( _generics.containsKey(name))) return null;

    return _generics.get(name);
  }
}