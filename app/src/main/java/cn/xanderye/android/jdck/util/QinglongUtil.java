package cn.xanderye.android.jdck.util;

import cn.xanderye.android.jdck.entity.QlInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XanderYe
 * @description: 青龙面板工具类
 * @date 2022/5/11 14:04
 */
public class QinglongUtil {

    /**
     * 登录
     * @param qlInfo
     * @return java.lang.String
     * @author XanderYe
     * @date 2022/5/11
     */
    public static String login(QlInfo qlInfo) throws IOException {
        String url = qlInfo.getAddress();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        url += "/auth/token";
        url += "?t=" + System.currentTimeMillis();
        JSONObject params = new JSONObject();
        params.put("client_id", qlInfo.getClientId());
        params.put("client_secret", qlInfo.getClientSecret());
        HttpUtil.ResEntity resEntity = HttpUtil.doPostJSON(url, params.toJSONString());
        if (resEntity.getStatusCode() != 200) {
            throw new IOException("服务器" + resEntity.getStatusCode() + "错误");
        }
        JSONObject res = JSON.parseObject(resEntity.getResponse());
        if (res.getInteger("code") != 200) {
            throw new IOException(res.getString("message"));
        }
        return res.getJSONObject("data").getString("token");
    }
}    
