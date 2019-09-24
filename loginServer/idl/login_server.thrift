//1)�ỰƱ��: hex(aes(SessionTicket���л�), ���� ��login_server�����ļ���ȡ))
//2)�Ựid:   login_server:uuid()����, ������center_server����
//3)�Ựkey:  login_server:�������������ͨѶ����
include "common.thrift"
//namespace cpp community.login_server
namespace java com.spirit.community.protocol.thrift.login

enum MessageType
{   
	MT_CLIENT_REGISTER_REQ = 500,
	MT_CLIENT_REGISTER_RES,
    MT_CLIENT_PASSWORD_LOGIN_REQ,         //�ͻ��������¼����
    MT_CLIENT_LOGIN_RES,                        //�ͻ��˵�¼�ظ�
    MT_CLIENT_LOGOUT_REQ,                       //�û��ӿͻ����˳�
    MT_CLIENT_LOGOUT_RES,
    MT_UPDATE_AVAILABLE_RES,						//�ͻ�������
}

struct ClientPasswordLoginReq
{
    1:i64    	user_id,                        //��¼��
    2:i64       client_random,                  //�ͻ��������
    3:string    client_mac,                     //�ͻ���mac
    4:string    client_version,                 //�ͻ��˰汾��
    5:string    check_sum,                       //�����( hex(md5(ClientPasswordLoginReqChecksum���л�)) )
}

struct ClientPasswordLoginReqChecksum
{
    1:i64    	user_id,                        //��¼��
    2:string    password,                       //hex(md5(�û���������))
    3:i64       client_random,                  //�ͻ��������
    4:i64       server_random,                  //����������
}

struct ClientTicketLoginReqChecksum
{
    1:i64       user_id,                        //�û�id
    2:string    session_ticket,                 //�ỰƱ��
    3:i64       client_random,                  //�ͻ��������
    4:i64       server_random,                  //����������
}

struct ClientLoginRes
{
    1:i16       error_code,
    2:string    error_text,
    3:string    user_info,                      //�û���Ϣ( hex( aes(ClientLoginResUserInfo���л�), ����Ϊ md5(�û���������)) )
    4:string    session_ticket,                 //�ỰƱ��
	5:list<common.ServiceInfo> room_gate_info,       //roomgate��ַ�б�
}

struct ClientLoginResUserInfo
{
    1:i64       user_id,                        //�û�ID
    2:string    user_name,                      //�û���
    3:string    email,                          //����
    4:string    nick_name,                      //�ǳ�
    5:string    avatar_url,                     //�û�ͷ��url
    6:string   client_ip,                      //�ͻ���ip
}

struct LogoutReq
{
    1:i64       user_id,                        //�û�id
    2:string    session_ticket,                 //�ỰƱ��
}

struct LogoutRes
{
    1:i16       error_code,
    2:i64       user_id,
}

struct UserRegisterReq
{
    1:string    user_name,                      //�û���
    2:string    nick_name,                          //����
    3:i16    	gender,                      //�ǳ�
    4:string 	email,                   //�Ա�
    5:string   client_ip,                      //�ͻ���ip
    6:string   client_mac,                     //�ͻ���mac
    7:string   client_version,                 //�ͻ��˰汾��
    8:i32      app_id,                         //Ӧ��id(Ĭ��Ϊ0)
    9:string   session_id,                     //�Ựid
    10:string   session_key,                    //�Ựkey
    11:string 	cellphone,
    12:string   password,
	13:string   invitation_code,
	14:string   identity_card,
}

struct UserRegisterRes
{
    1:i16       error_code,
    2:string    error_text,
    3:string    user_id,
}

struct UpdateResourceReq
{
	1:string	client_version,		//�ͻ��˰汾��
	2:string    resource_version,	//��Դ�汾��
}

struct UpdateResourceRes
{
	1:i16		result,							//0:(����Ҫ����) -1:(��Ҫ����)
	2:i16		update_type,					//��������:  1��ѡ������; 2 ǿ������; 3 ��Ĭ������
	3:string	new_source_version,				//�°汾��
	4:string	download_url;					//�����������ص�ַ
	5:string	md5_value;						//md5ֵ
	6:string	feature_url;					//����url
	7:i32		file_size;						//��������С
}
