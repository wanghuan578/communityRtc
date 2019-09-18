//namespace cpp community.common
namespace java com.spirit.community.rtc.login.protocol.rpc.thrift

enum CommonMessageType
{
    MT_HELLO_REQ 		= 100, 					
    MT_HELLO_RES,
    MT_HELLO_NOTIFY,
    MT_KEEPALIVE_REQ,
    MT_KEEPALIVE_RES,
}

enum ErrorCode
{
    OK = 0,                              //�ɹ�
    
    TICKET_INVALID  = 100,               //Ʊ����Ч
    LOGNAME_EXIST,                       //�û����Ѵ���
    LOGNAME_NOT_EXIST,                   //�û���������
    PASSWORD_ERROR,                      //�������
    USERID_INVALID,                      //�û�ID��Ч
    PASS_EXPIRYDATE,                     //������Ч��
    ACCOUNT_LOCKED,                      //�û��ʻ�������
	INVALID_ACCOUNT,                     //�û��ʻ���Ч
    CLIENT_VERSION_INVALID,              //�ͻ��˰���Ч
	ACCOUNT_ACCTIVED,                    //�ʻ��ѱ�����
	VERIFYCODE_VERIFIED,                 //��֤������֤��
	USER_IN_BLACKLIST,                   //�û��ں�����
    LOGIN_CONNECT_ERROR,                 //���Ӳ��ϵ�¼������
    ROOMGATE_CONNECT_ERROR,              //���Ӳ��Ϸ������ط�����
    NICKNAME_DUPLICATE,                  //�û��ǳ��ظ�
    ROOM_UNKNOWN,                        //����δ֪����
    ROOM_STATUS_NO_STARTUP,              //����δ����
    ROOM_STATUS_CLOSE,                   //����ر�
    ROOM_STATUS_LOCKED,                  //��������
    ROOM_USER_TICKET_INVALID,            //�û�Ʊ����Ч
    ROOM_USER_ID_INVALID,                //�û�id��Ч
    ROOM_USER_NO_AUTHORITY,              //�û��޴�Ȩ��
    ROOM_USER_MONEY_LESS,                //�û�����
    ROOM_USER_KICK_OUT,                  //�û����߳�����
    ROOM_USER_OTHER_LOCAL_LOGIN,         //�û��������ط���¼
    ROOM_USER_COUNT_MAX,                 //�������������
    ROOM_NEED_PASSWORD,                  //��Ҫ��������
    ROOM_PASSORD_ERROR,                  //�����������
    ROOM_TO_USERID_INVALID,              //Ŀ���û�id��Ч
    REQUEST_ERROR,                     	 //�������
    REQUEST_TIMEOUT,                     //����ʱ
    DB_CONNECT_ERROR,                    //�����ݿ�����ʧЧ
    DB_OPERATION_EXCEPTION,              //���ݿ�����쳣
    SYSTEM_BUSY,                         //ϵͳ��æ
    UNKOWN_ERROR,                        //δ֪����
}

struct ServiceAddr
{
    1:string    ip,
    2:i16       port,
}

struct ServiceInfo
{
	1:i32       			service_id,
    2:string       			service_name,
	3:i16					service_weight,
	4:ServiceAddr			service_addr,
}

struct HelloReq
{
    1:ServiceInfo service_info,
}

struct HelloNotify
{
    1:i16       error_code,
    2:string    error_text,
    3:string    service_name,
    4:i32       service_id,
    5:i64       server_random,
    6:i64       server_time,
}

struct SessionTicket
{
    1:i32       user_id,                        //�û�id
    2:string    user_name,                      //�û���
    3:string    email,                          //����
    4:string    nick_name,                      //�ǳ�
    5:string    client_ip,                      //�ͻ���ip
    6:string    client_mac,                     //�ͻ���mac
    7:string    client_version,                 //�ͻ��˰汾��
    8:i32       app_id,                         //Ӧ��id(Ĭ��Ϊ0)
    9:string    session_id,                     //�Ựid
    10:string   session_key,                    //�Ựkey
    11:i64      create_time,                    //����ʱ��(��Ϊ��λ)
    12:i64      end_time,                       //��ֹʱ��(��Ϊ��λ)
}

//�սṹ(����û��Ϣ�����Ϣ)
struct CommonNull
{
}

struct CommonRes
{
    1:i16       error_code,
    2:string    error_text,
}

struct KeepAliveReq
{
    1:i32       req_id,
    2:i64       current_time_req,
    3:i16       network_status_level,//����״���ȼ�(0:���ź� 1�ǳ��� 2�� 3һ�� 4�� 5�ǳ���)
}

struct KeepAliveRes
{
    1:i16       error_code,
    2:string    error_text,
    3:i32       req_id,
    4:i64       current_time_req,
    5:i64       current_time_res,
}