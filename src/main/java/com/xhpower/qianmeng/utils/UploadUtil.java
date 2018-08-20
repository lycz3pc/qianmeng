package com.xhpower.qianmeng.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @ClassName: UploadUtil
 * @Description: TODO(文件上传的公共类)
 * @author lisf
 * @date 2016年10月12日 上午9:12:39
 *
 */
public class UploadUtil {

	private static String projectPath;

	public static void setPath(String path) {
		projectPath = path;
	}

	public static String getPath() {
		return projectPath;
	}

	/**
	 * 
	 * @Title: uploadFile
	 * @Description: 批量上传文件 ,返回文件路径集合
	 * @param filePath
	 * @param files
	 * @author root
	 * @return List<String> 返回类型
	 */
	public static List<String> uploadFile(String filePath, MultipartFile files[]) {
		List<String> pathList = new ArrayList<String>();
		// 保存的文件路径

		if (files.length != 0) {
			try {
				String realPath = ResourceUtils.getURL("classpath:").getPath() + filePath;
				for (MultipartFile file : files) {
					// 上传文件名称
					String fileName = file.getOriginalFilename();

					// 生成新的文件名
					fileName = System.currentTimeMillis() + "-" + fileName;

					// 创建存储目录
					File saveDir = new File(realPath, fileName.toString());

					if (!saveDir.getParentFile().exists()) {
						saveDir.getParentFile().mkdirs();
					}
					// 转存文件
					file.transferTo(saveDir);

					String path = filePath + File.separator + fileName;

					pathList.add(path.replaceAll("\\\\", "/"));
				}
				return pathList;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: uploadFile
	 * @Description: 上传单个文件 ,返回文件的路
	 * @param filePath
	 *            文件路径
	 * @param file
	 * @return 设定文
	 * @author lisf
	 * @return String 返回类型
	 */
	public static String uploadFile(String filePath, MultipartFile file, String rootPath) {
		if (!file.isEmpty()) {
			try {
				// 保存的文件路径
				String realPath = StringUtils.isBlank(rootPath)
						? ResourceUtils.getURL("classpath:").getPath() + filePath : rootPath + filePath;

				// 上传文件名称
				String fileName = file.getOriginalFilename();

				// 生成新的文件名
				fileName = System.currentTimeMillis() + "-" + fileName;

				// 创建存储目录
				File saveDir = new File(realPath, fileName.toString());

				if (!saveDir.getParentFile().exists()) {
					saveDir.getParentFile().mkdirs();
				}
				// 转存文件
				file.transferTo(saveDir);

				String path = filePath + File.separator + fileName;

				return path.replaceAll("\\\\", "/");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: saveFile
	 * @Description: TODO(文件的上传)
	 * @param url
	 * @param request
	 * @param file
	 * @return String 返回类型 @throws
	 */
	public static String saveFile(String url, HttpServletRequest request, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				url = url + File.separator + format.format(new Date());
				// 保存的文件路径
				String filePath = request.getSession().getServletContext().getRealPath("/") + url;
				// 上传文件名称
				String fileName = file.getOriginalFilename();

				// 转义拆分重命名文件
				String[] strArr = fileName.split("\\.");

				// 生成UID随机数
				UUID uuid = UUID.randomUUID();
				String string = uuid.toString();
				String strName = string.replace("-", "");

				// 生成新的文件名
				fileName = strName + "." + strArr[strArr.length - 1];

				// 创建存储目录
				File saveDir1 = new File(filePath, fileName.toString());

				if (!saveDir1.getParentFile().exists()) {
					saveDir1.getParentFile().mkdirs();
				}
				// 转存文件
				file.transferTo(saveDir1);
				return fileName.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: deleteFile
	 * @Description: TODO(删除记录时删除相对应的文件，传入项目下的路径，请求Request,以及要删除的文件名称)
	 * @param fileName
	 * @param request
	 * @param url
	 *            设定文件
	 * @return void 返回类型
	 */
	public static void deleteFile(String fileName, HttpServletRequest request, String url) {
		// 项目路径
		String filePath = request.getSession().getServletContext().getRealPath("/") + url;
		// 上传文件名称
		File file = new File(filePath, fileName);

		if (!file.exists()) {
			System.out.println("文件不存在！不执行删除操作");
		} else {
			// 删除文件
			file.delete();
		}
	}

}
