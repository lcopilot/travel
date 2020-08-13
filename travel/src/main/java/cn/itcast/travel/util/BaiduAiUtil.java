package cn.itcast.travel.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaiduAiUtil {

	private static String IMAGE_TYPE ;
	private static String groupId ;
	private static AipFace client;
	private static double face_liveness;
	private static double score;

	private HashMap<String, String> options = new HashMap<String, String>();

	public BaiduAiUtil() {
		//参数设置
		options.put("quality_control", "HIGH");// 图片质量  NONE不控制  LOW较低的质量  NORMAL一般的质量  HIGH较高的质量
		options.put("liveness_control", "HIGH");// 活体检测  NONE不控制  LOW较低的质量  NORMAL一般的质量  HIGH较高的质量
	}

	static {
		//读取配置文件
		InputStream is = BaiduAiUtil.class.getClassLoader()
				.getResourceAsStream("Face.properties");
		//创建Properties对象
		Properties pro = new Properties();
		//关联文件
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		score=Double.valueOf(pro.getProperty("score"));
		face_liveness=Double.valueOf(pro.getProperty("face_liveness"));
		IMAGE_TYPE=pro.getProperty("IMAGE_TYPE");
		groupId=pro.getProperty("groupId");
		client = new AipFace(pro.getProperty("APP_ID"), pro.getProperty("API_KEY"), pro.getProperty("SECRET_KEY"));
	}
	// @PostConstruct
	// public void init() {
	//   client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
	// }

	/**
	 * 人脸注册 ：将用户照片存入人脸库中
	 */
	public Boolean faceRegister(String userId, String image) {
		// 人脸注册
		JSONObject res = client.addUser(image, IMAGE_TYPE, groupId, userId, options);
		Integer errorCode = res.getInt("error_code");
		return errorCode == 0 ? true : false;
	}

	/**
	 * 人脸更新 ：更新人脸库中的用户照片
	 */
	public Boolean faceUpdate(String userId, String image) {
		// 人脸更新
		JSONObject res = client.updateUser(image, IMAGE_TYPE, groupId, userId, options);
		Integer errorCode = res.getInt("error_code");
		return errorCode == 0 ? true : false;
	}

	/**
	 * 人脸检测：判断上传图片中是否具有面部头像
	 */
	public Boolean faceCheck(String image) {
		JSONObject res = client.detect(image, IMAGE_TYPE, options);
		if (res.has("error_code") && res.getInt("error_code") == 0) {
			JSONObject resultObject = res.getJSONObject("result");
			Integer faceNum = resultObject.getInt("face_num"); // 检测的人脸数量
			boolean flag = faceNum == 1 ? true : false;
			if (flag) {
				return sample(image);
			}
		}
		return false;

	}

	/**
	 * 人脸查找：查找人脸库中最相似的人脸并返回数据 处理：用户的匹配得分（score）大于80分，即可认为是同一个用户
	 */
	public String faceSearch(String image) {
		JSONObject res = client.search(image, IMAGE_TYPE, groupId, options);
		if (res.has("error_code") && res.getInt("error_code") == 0) {
			JSONObject result = res.getJSONObject("result");
			JSONArray userList = result.getJSONArray("user_list");
			if (userList.length() > 0) {
				JSONObject user = userList.getJSONObject(0);
				double score1 = user.getDouble("score");
				if (score1 > score) {
					return user.getString("user_id");
				}
			}
		}
		return null;
	}

	/**
	 * 活体检测
	 *
	 * @param image
	 * @return
	 */
	public boolean sample(String image) {
		FaceVerifyRequest req = new FaceVerifyRequest(image, "BASE64");
		ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
		list.add(req);
		JSONObject res = client.faceverify(list);
		JSONObject result = res.getJSONObject("result");
		double face_liveness1 = result.getDouble("face_liveness");
		if (face_liveness1 > face_liveness) {
			return true;
		}
		return false;
	}




}
