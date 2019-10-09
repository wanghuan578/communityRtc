/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.spirit.community.protocol.thrift.roomgate;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-10-08")
public class ConnectChecksum implements org.apache.thrift.TBase<ConnectChecksum, ConnectChecksum._Fields>, java.io.Serializable, Cloneable, Comparable<ConnectChecksum> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ConnectChecksum");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("user_id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SESSION_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("session_key", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLIENT_RANDOM_FIELD_DESC = new org.apache.thrift.protocol.TField("client_random", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField SERVER_RANDOM_FIELD_DESC = new org.apache.thrift.protocol.TField("server_random", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ConnectChecksumStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ConnectChecksumTupleSchemeFactory();

  public long user_id; // required
  public java.lang.String session_key; // required
  public long client_random; // required
  public long server_random; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "user_id"),
    SESSION_KEY((short)2, "session_key"),
    CLIENT_RANDOM((short)3, "client_random"),
    SERVER_RANDOM((short)4, "server_random");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // USER_ID
          return USER_ID;
        case 2: // SESSION_KEY
          return SESSION_KEY;
        case 3: // CLIENT_RANDOM
          return CLIENT_RANDOM;
        case 4: // SERVER_RANDOM
          return SERVER_RANDOM;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __USER_ID_ISSET_ID = 0;
  private static final int __CLIENT_RANDOM_ISSET_ID = 1;
  private static final int __SERVER_RANDOM_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("user_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SESSION_KEY, new org.apache.thrift.meta_data.FieldMetaData("session_key", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_RANDOM, new org.apache.thrift.meta_data.FieldMetaData("client_random", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SERVER_RANDOM, new org.apache.thrift.meta_data.FieldMetaData("server_random", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ConnectChecksum.class, metaDataMap);
  }

  public ConnectChecksum() {
  }

  public ConnectChecksum(
    long user_id,
    java.lang.String session_key,
    long client_random,
    long server_random)
  {
    this();
    this.user_id = user_id;
    setUser_idIsSet(true);
    this.session_key = session_key;
    this.client_random = client_random;
    setClient_randomIsSet(true);
    this.server_random = server_random;
    setServer_randomIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ConnectChecksum(ConnectChecksum other) {
    __isset_bitfield = other.__isset_bitfield;
    this.user_id = other.user_id;
    if (other.isSetSession_key()) {
      this.session_key = other.session_key;
    }
    this.client_random = other.client_random;
    this.server_random = other.server_random;
  }

  public ConnectChecksum deepCopy() {
    return new ConnectChecksum(this);
  }

  @Override
  public void clear() {
    setUser_idIsSet(false);
    this.user_id = 0;
    this.session_key = null;
    setClient_randomIsSet(false);
    this.client_random = 0;
    setServer_randomIsSet(false);
    this.server_random = 0;
  }

  public long getUser_id() {
    return this.user_id;
  }

  public ConnectChecksum setUser_id(long user_id) {
    this.user_id = user_id;
    setUser_idIsSet(true);
    return this;
  }

  public void unsetUser_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  /** Returns true if field user_id is set (has been assigned a value) and false otherwise */
  public boolean isSetUser_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  public void setUser_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USER_ID_ISSET_ID, value);
  }

  public java.lang.String getSession_key() {
    return this.session_key;
  }

  public ConnectChecksum setSession_key(java.lang.String session_key) {
    this.session_key = session_key;
    return this;
  }

  public void unsetSession_key() {
    this.session_key = null;
  }

  /** Returns true if field session_key is set (has been assigned a value) and false otherwise */
  public boolean isSetSession_key() {
    return this.session_key != null;
  }

  public void setSession_keyIsSet(boolean value) {
    if (!value) {
      this.session_key = null;
    }
  }

  public long getClient_random() {
    return this.client_random;
  }

  public ConnectChecksum setClient_random(long client_random) {
    this.client_random = client_random;
    setClient_randomIsSet(true);
    return this;
  }

  public void unsetClient_random() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CLIENT_RANDOM_ISSET_ID);
  }

  /** Returns true if field client_random is set (has been assigned a value) and false otherwise */
  public boolean isSetClient_random() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CLIENT_RANDOM_ISSET_ID);
  }

  public void setClient_randomIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CLIENT_RANDOM_ISSET_ID, value);
  }

  public long getServer_random() {
    return this.server_random;
  }

  public ConnectChecksum setServer_random(long server_random) {
    this.server_random = server_random;
    setServer_randomIsSet(true);
    return this;
  }

  public void unsetServer_random() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SERVER_RANDOM_ISSET_ID);
  }

  /** Returns true if field server_random is set (has been assigned a value) and false otherwise */
  public boolean isSetServer_random() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SERVER_RANDOM_ISSET_ID);
  }

  public void setServer_randomIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SERVER_RANDOM_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUser_id();
      } else {
        setUser_id((java.lang.Long)value);
      }
      break;

    case SESSION_KEY:
      if (value == null) {
        unsetSession_key();
      } else {
        setSession_key((java.lang.String)value);
      }
      break;

    case CLIENT_RANDOM:
      if (value == null) {
        unsetClient_random();
      } else {
        setClient_random((java.lang.Long)value);
      }
      break;

    case SERVER_RANDOM:
      if (value == null) {
        unsetServer_random();
      } else {
        setServer_random((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUser_id();

    case SESSION_KEY:
      return getSession_key();

    case CLIENT_RANDOM:
      return getClient_random();

    case SERVER_RANDOM:
      return getServer_random();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUser_id();
    case SESSION_KEY:
      return isSetSession_key();
    case CLIENT_RANDOM:
      return isSetClient_random();
    case SERVER_RANDOM:
      return isSetServer_random();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ConnectChecksum)
      return this.equals((ConnectChecksum)that);
    return false;
  }

  public boolean equals(ConnectChecksum that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_user_id = true;
    boolean that_present_user_id = true;
    if (this_present_user_id || that_present_user_id) {
      if (!(this_present_user_id && that_present_user_id))
        return false;
      if (this.user_id != that.user_id)
        return false;
    }

    boolean this_present_session_key = true && this.isSetSession_key();
    boolean that_present_session_key = true && that.isSetSession_key();
    if (this_present_session_key || that_present_session_key) {
      if (!(this_present_session_key && that_present_session_key))
        return false;
      if (!this.session_key.equals(that.session_key))
        return false;
    }

    boolean this_present_client_random = true;
    boolean that_present_client_random = true;
    if (this_present_client_random || that_present_client_random) {
      if (!(this_present_client_random && that_present_client_random))
        return false;
      if (this.client_random != that.client_random)
        return false;
    }

    boolean this_present_server_random = true;
    boolean that_present_server_random = true;
    if (this_present_server_random || that_present_server_random) {
      if (!(this_present_server_random && that_present_server_random))
        return false;
      if (this.server_random != that.server_random)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(user_id);

    hashCode = hashCode * 8191 + ((isSetSession_key()) ? 131071 : 524287);
    if (isSetSession_key())
      hashCode = hashCode * 8191 + session_key.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(client_random);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(server_random);

    return hashCode;
  }

  @Override
  public int compareTo(ConnectChecksum other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUser_id()).compareTo(other.isSetUser_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user_id, other.user_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSession_key()).compareTo(other.isSetSession_key());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSession_key()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.session_key, other.session_key);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetClient_random()).compareTo(other.isSetClient_random());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient_random()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.client_random, other.client_random);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetServer_random()).compareTo(other.isSetServer_random());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServer_random()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.server_random, other.server_random);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ConnectChecksum(");
    boolean first = true;

    sb.append("user_id:");
    sb.append(this.user_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("session_key:");
    if (this.session_key == null) {
      sb.append("null");
    } else {
      sb.append(this.session_key);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_random:");
    sb.append(this.client_random);
    first = false;
    if (!first) sb.append(", ");
    sb.append("server_random:");
    sb.append(this.server_random);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ConnectChecksumStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectChecksumStandardScheme getScheme() {
      return new ConnectChecksumStandardScheme();
    }
  }

  private static class ConnectChecksumStandardScheme extends org.apache.thrift.scheme.StandardScheme<ConnectChecksum> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ConnectChecksum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.user_id = iprot.readI64();
              struct.setUser_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SESSION_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.session_key = iprot.readString();
              struct.setSession_keyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_RANDOM
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.client_random = iprot.readI64();
              struct.setClient_randomIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SERVER_RANDOM
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.server_random = iprot.readI64();
              struct.setServer_randomIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ConnectChecksum struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(USER_ID_FIELD_DESC);
      oprot.writeI64(struct.user_id);
      oprot.writeFieldEnd();
      if (struct.session_key != null) {
        oprot.writeFieldBegin(SESSION_KEY_FIELD_DESC);
        oprot.writeString(struct.session_key);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CLIENT_RANDOM_FIELD_DESC);
      oprot.writeI64(struct.client_random);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SERVER_RANDOM_FIELD_DESC);
      oprot.writeI64(struct.server_random);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ConnectChecksumTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectChecksumTupleScheme getScheme() {
      return new ConnectChecksumTupleScheme();
    }
  }

  private static class ConnectChecksumTupleScheme extends org.apache.thrift.scheme.TupleScheme<ConnectChecksum> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ConnectChecksum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUser_id()) {
        optionals.set(0);
      }
      if (struct.isSetSession_key()) {
        optionals.set(1);
      }
      if (struct.isSetClient_random()) {
        optionals.set(2);
      }
      if (struct.isSetServer_random()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetUser_id()) {
        oprot.writeI64(struct.user_id);
      }
      if (struct.isSetSession_key()) {
        oprot.writeString(struct.session_key);
      }
      if (struct.isSetClient_random()) {
        oprot.writeI64(struct.client_random);
      }
      if (struct.isSetServer_random()) {
        oprot.writeI64(struct.server_random);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ConnectChecksum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.user_id = iprot.readI64();
        struct.setUser_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.session_key = iprot.readString();
        struct.setSession_keyIsSet(true);
      }
      if (incoming.get(2)) {
        struct.client_random = iprot.readI64();
        struct.setClient_randomIsSet(true);
      }
      if (incoming.get(3)) {
        struct.server_random = iprot.readI64();
        struct.setServer_randomIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

