/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.spirit.community.protocol.thrift.roomgate;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-10-09")
public class ConnectReq implements org.apache.thrift.TBase<ConnectReq, ConnectReq._Fields>, java.io.Serializable, Cloneable, Comparable<ConnectReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ConnectReq");

  private static final org.apache.thrift.protocol.TField SESSOIN_TICKET_FIELD_DESC = new org.apache.thrift.protocol.TField("sessoin_ticket", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CHECKSUM_FIELD_DESC = new org.apache.thrift.protocol.TField("checksum", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ConnectReqStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ConnectReqTupleSchemeFactory();

  public java.lang.String sessoin_ticket; // required
  public java.lang.String checksum; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SESSOIN_TICKET((short)1, "sessoin_ticket"),
    CHECKSUM((short)2, "checksum");

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
        case 1: // SESSOIN_TICKET
          return SESSOIN_TICKET;
        case 2: // CHECKSUM
          return CHECKSUM;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SESSOIN_TICKET, new org.apache.thrift.meta_data.FieldMetaData("sessoin_ticket", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHECKSUM, new org.apache.thrift.meta_data.FieldMetaData("checksum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ConnectReq.class, metaDataMap);
  }

  public ConnectReq() {
  }

  public ConnectReq(
    java.lang.String sessoin_ticket,
    java.lang.String checksum)
  {
    this();
    this.sessoin_ticket = sessoin_ticket;
    this.checksum = checksum;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ConnectReq(ConnectReq other) {
    if (other.isSetSessoin_ticket()) {
      this.sessoin_ticket = other.sessoin_ticket;
    }
    if (other.isSetChecksum()) {
      this.checksum = other.checksum;
    }
  }

  public ConnectReq deepCopy() {
    return new ConnectReq(this);
  }

  @Override
  public void clear() {
    this.sessoin_ticket = null;
    this.checksum = null;
  }

  public java.lang.String getSessoin_ticket() {
    return this.sessoin_ticket;
  }

  public ConnectReq setSessoin_ticket(java.lang.String sessoin_ticket) {
    this.sessoin_ticket = sessoin_ticket;
    return this;
  }

  public void unsetSessoin_ticket() {
    this.sessoin_ticket = null;
  }

  /** Returns true if field sessoin_ticket is set (has been assigned a value) and false otherwise */
  public boolean isSetSessoin_ticket() {
    return this.sessoin_ticket != null;
  }

  public void setSessoin_ticketIsSet(boolean value) {
    if (!value) {
      this.sessoin_ticket = null;
    }
  }

  public java.lang.String getChecksum() {
    return this.checksum;
  }

  public ConnectReq setChecksum(java.lang.String checksum) {
    this.checksum = checksum;
    return this;
  }

  public void unsetChecksum() {
    this.checksum = null;
  }

  /** Returns true if field checksum is set (has been assigned a value) and false otherwise */
  public boolean isSetChecksum() {
    return this.checksum != null;
  }

  public void setChecksumIsSet(boolean value) {
    if (!value) {
      this.checksum = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case SESSOIN_TICKET:
      if (value == null) {
        unsetSessoin_ticket();
      } else {
        setSessoin_ticket((java.lang.String)value);
      }
      break;

    case CHECKSUM:
      if (value == null) {
        unsetChecksum();
      } else {
        setChecksum((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case SESSOIN_TICKET:
      return getSessoin_ticket();

    case CHECKSUM:
      return getChecksum();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case SESSOIN_TICKET:
      return isSetSessoin_ticket();
    case CHECKSUM:
      return isSetChecksum();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ConnectReq)
      return this.equals((ConnectReq)that);
    return false;
  }

  public boolean equals(ConnectReq that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_sessoin_ticket = true && this.isSetSessoin_ticket();
    boolean that_present_sessoin_ticket = true && that.isSetSessoin_ticket();
    if (this_present_sessoin_ticket || that_present_sessoin_ticket) {
      if (!(this_present_sessoin_ticket && that_present_sessoin_ticket))
        return false;
      if (!this.sessoin_ticket.equals(that.sessoin_ticket))
        return false;
    }

    boolean this_present_checksum = true && this.isSetChecksum();
    boolean that_present_checksum = true && that.isSetChecksum();
    if (this_present_checksum || that_present_checksum) {
      if (!(this_present_checksum && that_present_checksum))
        return false;
      if (!this.checksum.equals(that.checksum))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetSessoin_ticket()) ? 131071 : 524287);
    if (isSetSessoin_ticket())
      hashCode = hashCode * 8191 + sessoin_ticket.hashCode();

    hashCode = hashCode * 8191 + ((isSetChecksum()) ? 131071 : 524287);
    if (isSetChecksum())
      hashCode = hashCode * 8191 + checksum.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ConnectReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetSessoin_ticket()).compareTo(other.isSetSessoin_ticket());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSessoin_ticket()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sessoin_ticket, other.sessoin_ticket);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetChecksum()).compareTo(other.isSetChecksum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChecksum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.checksum, other.checksum);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ConnectReq(");
    boolean first = true;

    sb.append("sessoin_ticket:");
    if (this.sessoin_ticket == null) {
      sb.append("null");
    } else {
      sb.append(this.sessoin_ticket);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("checksum:");
    if (this.checksum == null) {
      sb.append("null");
    } else {
      sb.append(this.checksum);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ConnectReqStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectReqStandardScheme getScheme() {
      return new ConnectReqStandardScheme();
    }
  }

  private static class ConnectReqStandardScheme extends org.apache.thrift.scheme.StandardScheme<ConnectReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ConnectReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SESSOIN_TICKET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sessoin_ticket = iprot.readString();
              struct.setSessoin_ticketIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CHECKSUM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.checksum = iprot.readString();
              struct.setChecksumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ConnectReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sessoin_ticket != null) {
        oprot.writeFieldBegin(SESSOIN_TICKET_FIELD_DESC);
        oprot.writeString(struct.sessoin_ticket);
        oprot.writeFieldEnd();
      }
      if (struct.checksum != null) {
        oprot.writeFieldBegin(CHECKSUM_FIELD_DESC);
        oprot.writeString(struct.checksum);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ConnectReqTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectReqTupleScheme getScheme() {
      return new ConnectReqTupleScheme();
    }
  }

  private static class ConnectReqTupleScheme extends org.apache.thrift.scheme.TupleScheme<ConnectReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ConnectReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetSessoin_ticket()) {
        optionals.set(0);
      }
      if (struct.isSetChecksum()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSessoin_ticket()) {
        oprot.writeString(struct.sessoin_ticket);
      }
      if (struct.isSetChecksum()) {
        oprot.writeString(struct.checksum);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ConnectReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.sessoin_ticket = iprot.readString();
        struct.setSessoin_ticketIsSet(true);
      }
      if (incoming.get(1)) {
        struct.checksum = iprot.readString();
        struct.setChecksumIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

