
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import priv.zhou.Application;
import priv.zhou.common.domain.Result;
import priv.zhou.common.tools.OkHttpUtil;

/**
 * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
 * 设备端接口测试
 *
 * @author xwc
 * @since 2020/10/23
 */
@Slf4j
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestRunner {


    @Test
    public void ttt() {
        Result<Object> test = OkHttpUtil.httpGet("test", "https://api.apihubs.cn/holiday/get?token=6a262e088773238c2129cdaf03f114f3&field=year,month,date,week,weekend,holiday_recess,holiday_overtime&year=2021&month=202102&cn=1");
        System.out.println();
    }

//    //同步刷脸信息
//    @Test
//    public void FaceVisitTest() throws Exception {
//        String devNo = "xiwotest001";
//        String visitTime = "1604633266000";
//        String zityId = "1604588295804399";
//        String temperature = "36.0";
//        String imgData = "";
//        Map<String, Object> checkCodeMap = SignTools.getTreeMap();
//        checkCodeMap.put("devNo", devNo);
//        checkCodeMap.put("visitTime", visitTime);
//        checkCodeMap.put("zityId", zityId);
//        checkCodeMap.put("temperature", temperature);
//        checkCodeMap.put("key", DEVICE_CER_KEY);
//        String sign = SignTools.Utf8EncryptMapMD5(checkCodeMap);
//        OutVoGlobal outVoGlobal = faceService.receiveFaceRecord(devNo, visitTime, zityId, temperature, sign, imgData);
//        logger.info(outVoGlobal.toString());
//    }
//
//    //同步刷卡信息
//    @Test
//    public void CardVisitTest() throws Exception {
//        String devNo = "xiwotest001";
//        String visitTime = "1604641871185";
//        String passcard = "3435709387";
//        String temperature = "36.0";
//        String imgData = "https://kid.zt31.cn/pic/logo/sunmoon/sunmoon.png";
//        Map<String, Object> checkCodeMap = SignTools.getTreeMap();
//        checkCodeMap.put("devNo", devNo);
//        checkCodeMap.put("visitTime", visitTime);
//        checkCodeMap.put("passcard", passcard);
//        checkCodeMap.put("temperature", temperature);
//        checkCodeMap.put("key", DEVICE_CER_KEY);
//        String sign = SignTools.Utf8EncryptMapMD5(checkCodeMap);
//        System.out.println(sign);
//        OutVoGlobal outVoGlobal = faceService.receivePasscardRecord(devNo, visitTime, passcard, temperature, sign, imgData);
//        logger.info(outVoGlobal.toString());
//    }
//
//    @Test
//    public void getIncrementFaceInfoTest() throws Exception {
//        String devNo = "xiwotest001";
//        String requestTimestamp = DateUtil.format(new Date(), PURE_DATETIME_MS_PATTERN);
//        String faceList = "1604296903904051,2140142859,111";
//        //验签
//        Map<String, Object> checkCodeMap = SignTools.getTreeMap();
//        checkCodeMap.put("devNo", devNo);
//        checkCodeMap.put("requestTimestamp", requestTimestamp);
//        checkCodeMap.put("key", DEVICE_CER_KEY);
//        String sign = SignTools.Utf8EncryptMapMD5(checkCodeMap);
//        System.out.println(sign);
//        OutVoGlobal outVoGlobal = faceService.getIncrementCardInfo(devNo, requestTimestamp,sign , faceList);
//        logger.info(outVoGlobal.toString());
//    }
//
//    @Test
//    public void getIncrementFaceInfo() throws Exception {
//
//        String faceList = "1604296903904051,1604333734749336";
//        String groupId = "2";
//        //根据学校编号查询当前学校所有的正常注册的教师人脸信息
//        List<Map<String, Object>> normalFaceInfos = teacherMapper.getNormalFaceInfoByGroupId(groupId);
//
//        if (normalFaceInfos ==null) {
//            logger.info("当前学校不存在正常人脸信息(教师)! groupId:" + groupId);
//        }
//        List<Map<String, Object>> returnList;
//        if (!StringUtils.isEmpty(faceList)){
//            String[] split = faceList.split(",");
//            List<String> reqList = new ArrayList<>();
//            for (String s : split) {
//                reqList.add(s.trim());
//            }
//            List<String> collect = normalFaceInfos.stream().map(faceInfoMap -> faceInfoMap.get("personId").toString()).collect(Collectors.toList());
//            collect.removeAll(reqList);
//            returnList = normalFaceInfos.stream().filter(normalFaceInfoMap -> collect.contains(normalFaceInfoMap.get("personId").toString())).collect(Collectors.toList());
//        } else {
//            returnList = normalFaceInfos;
//        }
//        logger.info(returnList.toString());
//
//    }
//
//    //接收考勤卡离线刷卡识别记录
//    @Test
//    public void receivePasscardRecordOfflineTest() throws Exception {
//        String params = "params=%5B%7B%22devNo%22%3A%22xiwo412008%22%2C%22visitTime%22%3A%222020-11-21+09%3A42%3A04%22%2C%22passcard%22%3A%222116641675%22%2C%22temperature%22%3A%220%22%2C%22sign%22%3A%224e4188dc8dc1b4b67cdc83531338cc05%22%7D%2C%7B%22devNo%22%3A%22xiwo412008%22%2C%22visitTime%22%3A%222020-11-21+09%3A42%3A10%22%2C%22passcard%22%3A%222116641675%22%2C%22temperature%22%3A%220%22%2C%22sign%22%3A%222e70274d7cc928f1352f7c7379966433%22%7D%2C%7B%22devNo%22%3A%22xiwo412008%22%2C%22visitTime%22%3A%222020-11-21+09%3A42%3A16%22%2C%22passcard%22%3A%222116641675%22%2C%22temperature%22%3A%220%22%2C%22sign%22%3A%22a0d2778a422c117b1a7abd4fa235d791%22%7D%5D\n";
//        String decode = URLDecoder.decode(params, "utf-8");
//        String jsonStr = decode.substring(7);
//        JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonStr);
//        OutVoGlobal outVoGlobal = faceService.receivePasscardRecordOffline(jsonArray);
//        logger.info(outVoGlobal.toString());
//    }
}
