

## 基于zookeeper的服务治理框架



### 一键安装

- mvn clean install


### 采用框架及功能


#### 框架


- 网络模型netty
- 采用libtba二进制协议
- 过程协同zookeeper
- SDK支持java和c++


#### 功能


- 注册服务
- 获取服务列表
- 服务变更通知
- 获取服务状态信息



### 功能


#### SDK服务注册

```
MsgServiceRegisterReq req;

	common::AddressInfo address;
	address.ip = "127.0.0.1";
	address.port = 9999;
	common::RouteInfo route;
	route.address = address;
	route.weight = 99;
	route.name = "translate";
	req.body_.route = route;
	
	tba_byte_buffer buff(512);
	req.serialize_ex<tba_byte_buffer>(&buff);

	bufferevent_write((bufferevent*)socket_handler, buff.buffer(), buff.data_size());
```

#### SDK获取服务列表

```
	MsgGetServiceListReq req;

	req.body_.service_name = "translate";
	
	tba_byte_buffer buff(512);
	req.serialize_ex<tba_byte_buffer>(&buff);

	bufferevent_write((bufferevent*)socket_handler, buff.buffer(), buff.data_size());
```

#### SDK查询RestApi

```
@RequestMapping("/provider")
public class ProviderServiceController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public ResultEntity getServicesInfo() {
        ResultEntity entity = new ResultEntity();
        try {
            return entity.succeed(applicationService.getApplicationInfo());
        } catch (IllegalAccessException | TbaException | InstantiationException e) {
            return entity.failed(UNEXPECTED_EXCEPTION.setTextDefault(e.getMessage()));
        } catch (MainStageException e) {
            return entity.failed(e.getResultType());
        }
    }

    @RequestMapping(value = "/application/detail", method = RequestMethod.POST)
    public ResultEntity getApplicationDetail(@RequestBody ServiceInfoDetailRequest req) {
        ResultEntity entity = new ResultEntity();
        try {
            return entity.succeed(applicationService.getApplicationDetail(req));
        } catch (IllegalAccessException | TbaException | InstantiationException e) {
            return entity.failed(UNEXPECTED_EXCEPTION.setTextDefault(e.getMessage()));
        }
        catch (MainStageException e) {
            return entity.failed(e.getResultType());
        }
    }
}
```


#### 服务变更通知


```

    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

        ChildData childData = pathChildrenCacheEvent.getData();
        byte[] data = childData.getData();
        if (data == null) {
            log.info("childEvent data ==============================> null");
            return;
        }

        TbaUtil<ServiceInfo> tba = new TbaUtil();
        ServiceInfo serviceInfo =tba.Deserialize(data, ServiceInfo.class);
        
        log.info("Decode Node ServiceInfo Info: {}", JSON.toJSONString(serviceInfo, true));

        switch (pathChildrenCacheEvent.getType()){
            case CHILD_ADDED:
                log.info("新增子节点：{}", childData.getPath());
                nodeSyncNotify(childData.getPath(), SyncType.APPEND, serviceInfo);
                break;

            case CHILD_UPDATED:
                log.info("更新子节点：{}", childData.getPath());
                break;

            case CHILD_REMOVED:
                log.info("删除子节点：{}", childData.getPath());
                nodeSyncNotify(childData.getPath(), SyncType.DROP, serviceInfo);
                break;
        }
    }

    private int nodeSyncNotify(String path, String mode, ServiceInfo info) {

        String sub = path.substring(0, path.lastIndexOf("/"));
        List<ChannelHandlerContext> contexts = sessionFactory.context(sub);

        log.info("ChannelHandlerContext：len: {}", contexts.size());

        String [] hostInfo = path.substring(path.lastIndexOf("/") + 1).split(":");
        String serviceName = path.substring("/services/".length(), path.lastIndexOf("/"));

        RouteInfo route = new RouteInfo();
        AddressInfo addr = new AddressInfo();
        addr.ip = hostInfo[0];
        addr.port = Short.valueOf(hostInfo[1]);
        route.address = addr;
        route.name = serviceName;
        route.weight = info.route.weight;

        contexts.stream().forEach(e -> {
            ServiceListSyncNotify notify = new ServiceListSyncNotify();
            notify.mode = mode;
            notify.route = route;
            TsRpcHead head = new TsRpcHead(RpcEventType.MT_SERVICE_LIST_CHANGE_NOTIFY);
            e.write(new TsEvent(head, notify, 1024));
            e.flush();
        });

        return 0;
    }
```

### 相关资源

- [客户端SDK](https://github.com/wanghuan578/essential-sdk)(essential-sdk)
- [java 版序列化工具](https://github.com/wanghuan578/libtba-java)(libtba-java)


### 作者和贡献者信息


- spirit(57810140@qq.com)

