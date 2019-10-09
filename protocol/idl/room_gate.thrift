include "common.thrift"
namespace java com.spirit.community.protocol.thrift.roomgate

enum MessageType
{  
    MT_CONNECT_REQ = 600,
    MT_CONNECT_RES,
    MT_GET_ROOMLIST_REQ,
    MT_GET_ROOMLIST_RES,
    MT_UPDATE_ROOMLIST_NOTIFY,
	UPDATE_USERINFO_REQ,
    UPDATE_USERINFO_RES,
    MT_CHAT_REQ,
    MT_CHAT_RES,
    MT_CHAT_NOTIFY,
}

enum ChatType
{
    PRIVATE_CHAT = 0,                           //私聊
    PUBLIC_CHAT = 1,                            //第1位: 0私聊      1公聊
    AUTO_REPLY  = 2,                            //第2位: 0手工输入  1自动回复
    USER_MOOD = 4,                              //第3位: 0普通      1用户心情
    POKE = 8,                                   //第4位: 0普通      1动一下表情
    SHAKE_WINDOW = 16,                          //第5位: 0普通      1震动窗口
}

struct FontInfo
{
    1:string        name,                       //名称
    2:i16           size,                       //大小
    3:bool          bold,                       //是否加粗
    4:bool          italic,                     //是否倾斜
    5:bool          underline,                  //是否加下划线
    6:bool          strikeout,                  //是否加删除线
    7:i32           color,                      //颜色值
}

struct ChatReq
{
    1:string        from_nick_name,             //发送用户呢称
    2:string        to_nick_name,               //接收用户呢称
    3:i32           chat_type,                  //聊天类型 关联enum ChatType
    4:string        chat_text,                  //聊天文本
    5:i64           chat_time,                  //聊天时间
}

struct ConnectReq
{
    1:string       sessoin_ticket,
    2:string       checksum,
}

struct ConnectChecksum
{
    1:i64           user_id,
    2:string        session_key,
    3:i64           client_random,
    4:i64           server_random,
}

struct RoomInfo
{
	1:i32		room_id,
	2:string 	room_name,
	3:string	room_resource_url,
	4:i32		room_category,
}

struct GetRoomlistRes
{
	1:i16       			error_code,
    2:string    			error_text,
    3:list<RoomInfo>     	room_list,
}

struct UpdateUserinfoReq
{
    1:string        nick_name,          //用户昵称
    2:string        avatar_url,         //用户头像url
    3:string        sign_name,          //个性签名
    4:i16          gender,             //性别(0男 1女 2保密)
    5:string        birthdate,          //出生日期
    6:string        chinese_zodiac,     //生肖
    7:string        zodiac,             //星座
    8:string        blood_type          //血型
    9:string       interest,           //用户爱好,兴趣
    10:string       character,          //用户性格
    11:i16          chest,              //胸围
    12:i16          waist,              //腰围
    13:i16          hip,                //臀围
    14:string       address,            //地址(故乡)
}
