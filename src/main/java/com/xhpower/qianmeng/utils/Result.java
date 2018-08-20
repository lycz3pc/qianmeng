package com.xhpower.qianmeng.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * @ClassName: R
 * @Description: 返回数据
 * @author lisf
 * @date 2017年3月31日 下午7:57:06
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}

	public Result() {
		put("code", 0);
		put("time", System.currentTimeMillis());
	}

	public static Result error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static Result error(String msg) {
		return error(500, msg);
	}

	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		r.put("type", Type.error);
		return r;
	}

	public static Result error(SystemCode systemCode, String msg) {
		Result r = new Result();
		r.put("code", systemCode.getCode());
		r.put("msg", msg);
		r.put("type", Type.error);
		return r;
	}

	public static Result success(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		r.put("type", Type.success);
		return r;
	}

	public static Result success(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result success() {
		Result r = new Result();
		r.put("type", Type.success);
		return r;
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Result page(Page<?> page) {
		super.put("total", page.getTotal());
		super.put("size", page.getSize());
		super.put("current", page.getCurrent());
		super.put("pages", page.getPages());
		super.put("rows", page.getRecords());
		return this;
	}

	public Result list(List<?> list) {
		super.put("list", list);
		return this;
	}
}
