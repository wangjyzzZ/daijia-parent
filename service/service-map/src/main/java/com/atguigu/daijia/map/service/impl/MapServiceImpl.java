package com.atguigu.daijia.map.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.map.service.MapService;
import com.atguigu.daijia.model.form.map.CalculateDrivingLineForm;
import com.atguigu.daijia.model.vo.map.DrivingLineVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class MapServiceImpl implements MapService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("tencent.cloud.map")
    private String key;


    @Override
    public DrivingLineVo calculateDrivingLine(CalculateDrivingLineForm calculateDrivingLineForm) {
        String url = "https://apis.map.qq.com/ws/direction/v1/driving/?from={from}&to={to}&key={key}";

        //封装传递参数
        Map<String,String> map = new HashMap();
        //开始位置
        // 经纬度：比如 北纬40 东京116
        map.put("from",calculateDrivingLineForm.getStartPointLatitude()+","+calculateDrivingLineForm.getStartPointLongitude());
        //结束位置
        map.put("to",calculateDrivingLineForm.getEndPointLatitude()+","+calculateDrivingLineForm.getEndPointLongitude());
        //key
        map.put("key",key);

        //使用RestTemplate调用 GET
        JSONObject result = restTemplate.getForObject(url, JSONObject.class, map);
        //处理返回结果
        //判断调用是否成功
        int status = result.getIntValue("status");
        if(status != 0) {//失败
            throw new GuiguException(ResultCodeEnum.MAP_FAIL);
        }

        //获取返回路线信息
        JSONObject route =
                result.getJSONObject("result").getJSONArray("routes").getJSONObject(0);

        //创建vo对象
        DrivingLineVo drivingLineVo = new DrivingLineVo();
        //预估时间
        drivingLineVo.setDuration(route.getBigDecimal("duration"));
        //距离  6.583 == 6.58 / 6.59
        drivingLineVo.setDistance(route.getBigDecimal("distance")
                .divide(new BigDecimal(1000))
                .setScale(2, RoundingMode.HALF_UP));
        //路线
        drivingLineVo.setPolyline(route.getJSONArray("polyline"));

        return drivingLineVo;
    }
}
