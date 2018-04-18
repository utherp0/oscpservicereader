package org.uth.serviceReader.openshift.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uth.serviceReader.utils.JSONFailsafe;

public class Namespace
{
  private String _name = null;
  private String _selfLink = null;
  private String _creationTimestamp = null;

  private String _owner = null;
  private String _displayName = null;
  private String _description = null;
  private String _status = null;

  // Accessors
  public String getName() { return _name; }
  public String getSelfLink() { return _selfLink; }
  public String getCreationTimestamp() { return _creationTimestamp; }
  public String getOwner() { return _owner; }
  public String getDisplayName() { return _displayName; }
  public String getDescription() { return _description; }
  public String getStatus() { return _status; }

  // Mutators
  public void setName( String name ) { _name = name; }
  public void setSelfLink( String selfLink ) { _selfLink = selfLink; }
  public void setCreationTimestamp( String creationTimestamp ) { _creationTimestamp = creationTimestamp; }
  public void setOwner( String owner ) { _owner = owner; }
  public void setDisplayName( String displayName ) { _displayName = displayName; }
  public void setDescription( String description ) { _description = description; }
  public void setStatus( String status ) { _status = status; }

  // Empty Constructor
  public Namespace() {}

  // Full Constructor
  public Namespace( String name, String selfLink, String creationTimestamp, String owner, String displayName, String description, String status )
  {
    this._name = name;
    this._selfLink = selfLink;
    this._creationTimestamp = creationTimestamp;
    this._owner = owner;
    this._displayName = displayName;
    this._description = description;
    this._status = status;
  }

  /**
   * Convert provided JSONObject into a Namespace object
   * @param input pre-extracted JSON object containing only the Namespace JSON
   * @return Populated Namespace object
   * @throws InvalidOpenshiftJSONException if the contents of the JSONObject are invalid
   */
  public static Namespace extractNamespace( JSONObject input )
  {
    JSONObject status = input.getJSONObject( "status" );
    JSONObject metadata = input.getJSONObject( "metadata" );

    String statusPhase = status.getString( "phase" );
    String name = metadata.getString( "name" );
    String selfLink = metadata.getString( "selfLink" );
    String creationTimestamp = metadata.getString( "creationTimestamp" );

    JSONObject annotations = metadata.getJSONObject( "annotations" );

    // Failsafe checks for possibly optional field
    String owner = JSONFailsafe.getString(annotations, "openshift.io/requester");
    String description = JSONFailsafe.getString(annotations, "openshift.io/description");
    String displayName = JSONFailsafe.getString(annotations, "openshift.io/display-name");

    return new Namespace( name, selfLink, creationTimestamp, owner, displayName, description, statusPhase );
  }

  public static List<Namespace> extractNamespaces( JSONObject input )
  {
    ArrayList<Namespace> working = new ArrayList<Namespace>();

    if( !input.getString("kind").equals("NamespaceList") )
    {
      return null;
    }
    else
    {
      JSONArray components = input.getJSONArray("items");

      for( int loop = 0; loop < components.length(); loop++ )
      {
        Namespace namespace = Namespace.extractNamespace(components.getJSONObject(loop));

        if( namespace != null ) working.add(namespace);
      }

      return working;
    }
  }

  /**
   * Return a generic version of this object.
   */
  public Generic toGeneric()
  {
    Generic working = new Generic();

    working.addAtomic("name", this._name);
    working.addAtomic("selfLink", this._selfLink);
    working.addAtomic("creationTimestamp", this._creationTimestamp);
    working.addAtomic("owner",this._owner);
    working.addAtomic("displayName", this._displayName);
    working.addAtomic("description", this._description);
    working.addAtomic("status", this._status);

    return working;
  }
}