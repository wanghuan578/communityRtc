/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.spirit.community.protocol.thrift.login;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-10-08")
public class ClientPasswordLoginReq implements org.apache.thrift.TBase<ClientPasswordLoginReq, ClientPasswordLoginReq._Fields>, java.io.Serializable, Cloneable, Comparable<ClientPasswordLoginReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ClientPasswordLoginReq");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("user_id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CLIENT_RANDOM_FIELD_DESC = new org.apache.thrift.protocol.TField("client_random", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField CLIENT_MAC_FIELD_DESC = new org.apache.thrift.protocol.TField("client_mac", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CLIENT_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("client_version", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CHECK_SUM_FIELD_DESC = new org.apache.thrift.protocol.TField("check_sum", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ClientPasswordLoginReqStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ClientPasswordLoginReqTupleSchemeFactory();

  public long user_id; // required
  public long client_random; // required
  public java.lang.String client_mac; // required
  public java.lang.String client_version; // required
  public java.lang.String check_sum; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "user_id"),
    CLIENT_RANDOM((short)2, "client_random"),
    CLIENT_MAC((short)3, "client_mac"),
    CLIENT_VERSION((short)4, "client_version"),
    CHECK_SUM((short)5, "check_sum");

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
        case 2: // CLIENT_RANDOM
          return CLIENT_RANDOM;
        case 3: // CLIENT_MAC
          return CLIENT_MAC;
        case 4: // CLIENT_VERSION
          return CLIENT_VERSION;
        case 5: // CHECK_SUM
          return CHECK_SUM;
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
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("user_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_RANDOM, new org.apache.thrift.meta_data.FieldMetaData("client_random", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLIENT_MAC, new org.apache.thrift.meta_data.FieldMetaData("client_mac", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_VERSION, new org.apache.thrift.meta_data.FieldMetaData("client_version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHECK_SUM, new org.apache.thrift.meta_data.FieldMetaData("check_sum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ClientPasswordLoginReq.class, metaDataMap);
  }

  public ClientPasswordLoginReq() {
  }

  public ClientPasswordLoginReq(
    long user_id,
    long client_random,
    java.lang.String client_mac,
    java.lang.String client_version,
    java.lang.String check_sum)
  {
    this();
    this.user_id = user_id;
    setUser_idIsSet(true);
    this.client_random = client_random;
    setClient_randomIsSet(true);
    this.client_mac = client_mac;
    this.client_version = client_version;
    this.check_sum = check_sum;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ClientPasswordLoginReq(ClientPasswordLoginReq other) {
    __isset_bitfield = other.__isset_bitfield;
    this.user_id = other.user_id;
    this.client_random = other.client_random;
    if (other.isSetClient_mac()) {
      this.client_mac = other.client_mac;
    }
    if (other.isSetClient_version()) {
      this.client_version = other.client_version;
    }
    if (other.isSetCheck_sum()) {
      this.check_sum = other.check_sum;
    }
  }

  public ClientPasswordLoginReq deepCopy() {
    return new ClientPasswordLoginReq(this);
  }

  @Override
  public void clear() {
    setUser_idIsSet(false);
    this.user_id = 0;
    setClient_randomIsSet(false);
    this.client_random = 0;
    this.client_mac = null;
    this.client_version = null;
    this.check_sum = null;
  }

  public long getUser_id() {
    return this.user_id;
  }

  public ClientPasswordLoginReq setUser_id(long user_id) {
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

  public long getClient_random() {
    return this.client_random;
  }

  public ClientPasswordLoginReq setClient_random(long client_random) {
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

  public java.lang.String getClient_mac() {
    return this.client_mac;
  }

  public ClientPasswordLoginReq setClient_mac(java.lang.String client_mac) {
    this.client_mac = client_mac;
    return this;
  }

  public void unsetClient_mac() {
    this.client_mac = null;
  }

  /** Returns true if field client_mac is set (has been assigned a value) and false otherwise */
  public boolean isSetClient_mac() {
    return this.client_mac != null;
  }

  public void setClient_macIsSet(boolean value) {
    if (!value) {
      this.client_mac = null;
    }
  }

  public java.lang.String getClient_version() {
    return this.client_version;
  }

  public ClientPasswordLoginReq setClient_version(java.lang.String client_version) {
    this.client_version = client_version;
    return this;
  }

  public void unsetClient_version() {
    this.client_version = null;
  }

  /** Returns true if field client_version is set (has been assigned a value) and false otherwise */
  public boolean isSetClient_version() {
    return this.client_version != null;
  }

  public void setClient_versionIsSet(boolean value) {
    if (!value) {
      this.client_version = null;
    }
  }

  public java.lang.String getCheck_sum() {
    return this.check_sum;
  }

  public ClientPasswordLoginReq setCheck_sum(java.lang.String check_sum) {
    this.check_sum = check_sum;
    return this;
  }

  public void unsetCheck_sum() {
    this.check_sum = null;
  }

  /** Returns true if field check_sum is set (has been assigned a value) and false otherwise */
  public boolean isSetCheck_sum() {
    return this.check_sum != null;
  }

  public void setCheck_sumIsSet(boolean value) {
    if (!value) {
      this.check_sum = null;
    }
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

    case CLIENT_RANDOM:
      if (value == null) {
        unsetClient_random();
      } else {
        setClient_random((java.lang.Long)value);
      }
      break;

    case CLIENT_MAC:
      if (value == null) {
        unsetClient_mac();
      } else {
        setClient_mac((java.lang.String)value);
      }
      break;

    case CLIENT_VERSION:
      if (value == null) {
        unsetClient_version();
      } else {
        setClient_version((java.lang.String)value);
      }
      break;

    case CHECK_SUM:
      if (value == null) {
        unsetCheck_sum();
      } else {
        setCheck_sum((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUser_id();

    case CLIENT_RANDOM:
      return getClient_random();

    case CLIENT_MAC:
      return getClient_mac();

    case CLIENT_VERSION:
      return getClient_version();

    case CHECK_SUM:
      return getCheck_sum();

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
    case CLIENT_RANDOM:
      return isSetClient_random();
    case CLIENT_MAC:
      return isSetClient_mac();
    case CLIENT_VERSION:
      return isSetClient_version();
    case CHECK_SUM:
      return isSetCheck_sum();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ClientPasswordLoginReq)
      return this.equals((ClientPasswordLoginReq)that);
    return false;
  }

  public boolean equals(ClientPasswordLoginReq that) {
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

    boolean this_present_client_random = true;
    boolean that_present_client_random = true;
    if (this_present_client_random || that_present_client_random) {
      if (!(this_present_client_random && that_present_client_random))
        return false;
      if (this.client_random != that.client_random)
        return false;
    }

    boolean this_present_client_mac = true && this.isSetClient_mac();
    boolean that_present_client_mac = true && that.isSetClient_mac();
    if (this_present_client_mac || that_present_client_mac) {
      if (!(this_present_client_mac && that_present_client_mac))
        return false;
      if (!this.client_mac.equals(that.client_mac))
        return false;
    }

    boolean this_present_client_version = true && this.isSetClient_version();
    boolean that_present_client_version = true && that.isSetClient_version();
    if (this_present_client_version || that_present_client_version) {
      if (!(this_present_client_version && that_present_client_version))
        return false;
      if (!this.client_version.equals(that.client_version))
        return false;
    }

    boolean this_present_check_sum = true && this.isSetCheck_sum();
    boolean that_present_check_sum = true && that.isSetCheck_sum();
    if (this_present_check_sum || that_present_check_sum) {
      if (!(this_present_check_sum && that_present_check_sum))
        return false;
      if (!this.check_sum.equals(that.check_sum))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(user_id);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(client_random);

    hashCode = hashCode * 8191 + ((isSetClient_mac()) ? 131071 : 524287);
    if (isSetClient_mac())
      hashCode = hashCode * 8191 + client_mac.hashCode();

    hashCode = hashCode * 8191 + ((isSetClient_version()) ? 131071 : 524287);
    if (isSetClient_version())
      hashCode = hashCode * 8191 + client_version.hashCode();

    hashCode = hashCode * 8191 + ((isSetCheck_sum()) ? 131071 : 524287);
    if (isSetCheck_sum())
      hashCode = hashCode * 8191 + check_sum.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ClientPasswordLoginReq other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetClient_mac()).compareTo(other.isSetClient_mac());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient_mac()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.client_mac, other.client_mac);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetClient_version()).compareTo(other.isSetClient_version());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient_version()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.client_version, other.client_version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCheck_sum()).compareTo(other.isSetCheck_sum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCheck_sum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.check_sum, other.check_sum);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ClientPasswordLoginReq(");
    boolean first = true;

    sb.append("user_id:");
    sb.append(this.user_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_random:");
    sb.append(this.client_random);
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_mac:");
    if (this.client_mac == null) {
      sb.append("null");
    } else {
      sb.append(this.client_mac);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_version:");
    if (this.client_version == null) {
      sb.append("null");
    } else {
      sb.append(this.client_version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("check_sum:");
    if (this.check_sum == null) {
      sb.append("null");
    } else {
      sb.append(this.check_sum);
    }
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

  private static class ClientPasswordLoginReqStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ClientPasswordLoginReqStandardScheme getScheme() {
      return new ClientPasswordLoginReqStandardScheme();
    }
  }

  private static class ClientPasswordLoginReqStandardScheme extends org.apache.thrift.scheme.StandardScheme<ClientPasswordLoginReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ClientPasswordLoginReq struct) throws org.apache.thrift.TException {
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
          case 2: // CLIENT_RANDOM
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.client_random = iprot.readI64();
              struct.setClient_randomIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_MAC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.client_mac = iprot.readString();
              struct.setClient_macIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CLIENT_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.client_version = iprot.readString();
              struct.setClient_versionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CHECK_SUM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.check_sum = iprot.readString();
              struct.setCheck_sumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ClientPasswordLoginReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(USER_ID_FIELD_DESC);
      oprot.writeI64(struct.user_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CLIENT_RANDOM_FIELD_DESC);
      oprot.writeI64(struct.client_random);
      oprot.writeFieldEnd();
      if (struct.client_mac != null) {
        oprot.writeFieldBegin(CLIENT_MAC_FIELD_DESC);
        oprot.writeString(struct.client_mac);
        oprot.writeFieldEnd();
      }
      if (struct.client_version != null) {
        oprot.writeFieldBegin(CLIENT_VERSION_FIELD_DESC);
        oprot.writeString(struct.client_version);
        oprot.writeFieldEnd();
      }
      if (struct.check_sum != null) {
        oprot.writeFieldBegin(CHECK_SUM_FIELD_DESC);
        oprot.writeString(struct.check_sum);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ClientPasswordLoginReqTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ClientPasswordLoginReqTupleScheme getScheme() {
      return new ClientPasswordLoginReqTupleScheme();
    }
  }

  private static class ClientPasswordLoginReqTupleScheme extends org.apache.thrift.scheme.TupleScheme<ClientPasswordLoginReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ClientPasswordLoginReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUser_id()) {
        optionals.set(0);
      }
      if (struct.isSetClient_random()) {
        optionals.set(1);
      }
      if (struct.isSetClient_mac()) {
        optionals.set(2);
      }
      if (struct.isSetClient_version()) {
        optionals.set(3);
      }
      if (struct.isSetCheck_sum()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetUser_id()) {
        oprot.writeI64(struct.user_id);
      }
      if (struct.isSetClient_random()) {
        oprot.writeI64(struct.client_random);
      }
      if (struct.isSetClient_mac()) {
        oprot.writeString(struct.client_mac);
      }
      if (struct.isSetClient_version()) {
        oprot.writeString(struct.client_version);
      }
      if (struct.isSetCheck_sum()) {
        oprot.writeString(struct.check_sum);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ClientPasswordLoginReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.user_id = iprot.readI64();
        struct.setUser_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.client_random = iprot.readI64();
        struct.setClient_randomIsSet(true);
      }
      if (incoming.get(2)) {
        struct.client_mac = iprot.readString();
        struct.setClient_macIsSet(true);
      }
      if (incoming.get(3)) {
        struct.client_version = iprot.readString();
        struct.setClient_versionIsSet(true);
      }
      if (incoming.get(4)) {
        struct.check_sum = iprot.readString();
        struct.setCheck_sumIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

