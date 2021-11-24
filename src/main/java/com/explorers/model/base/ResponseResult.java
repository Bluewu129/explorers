package com.explorers.model.base;

import com.explorers.utils.StringUtil;
import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义Result
 *
 * @author wugaobo
 */
public class ResponseResult<T> implements Serializable {

  /**
   * 返回的实体
   */
  private T result;

  /**
   * 返回的消息
   */
  private String message;

  /**
   * 状态码
   */
  private int statusCode;

  /**
   * 系统异常
   */
  private String systemError;

  /**
   * 业务异常
   */
  private String businessError;

  /**
   * 成功返回
   *
   * @param res
   * @param msg
   * @param <T>
   * @return
   */
  public static <T> ResponseResult success(T res, String msg) {
    ResponseResult responseResult = new ResponseResult();
    responseResult.setMessage(msg);
    responseResult.setStatusCode(HttpServletResponse.SC_OK);
    responseResult.setResult(res);
    return responseResult;
  }

  /**
   * 成功返回
   *
   * @param res
   * @param <T>
   * @return
   */
  public static <T> ResponseResult success(T res) {
    ResponseResult<T> result = new ResponseResult();
    result.setResult(res);
    result.setStatusCode(HttpServletResponse.SC_OK);
    return result;
  }

  /**
   * 系统异常
   *
   * @param e
   * @param msg
   * @param <T>
   * @return
   */
  public static <T> ResponseResult systemError(Throwable e, String msg) {
    ResponseResult<T> result = new ResponseResult();
    result.setMessage(msg);
    result.setSystemError(StringUtil.getStackTrace(e));
    result.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    return result;
  }

  public static <T> ResponseResult systemError(String error, String msg) {
    return systemError(error, msg, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  public static <T> ResponseResult systemError(String error, String msg, int statusCode) {
    ResponseResult<T> result = new ResponseResult();
    result.setMessage(msg);
    result.setStatusCode(statusCode);
    result.setSystemError(error);
    return result;
  }

  public static <T> ResponseResult systemError(String error, String msg, int statusCode, T result) {
    ResponseResult<T> res = systemError(error, msg, statusCode);
    res.setResult(result);
    return res;
  }

  public static <T> ResponseResult systemError(Throwable e, String msg, int statusCode) {
    ResponseResult<T> result = new ResponseResult();
    result.setMessage(msg);
    result.setSystemError(StringUtil.getStackTrace(e));
    result.setStatusCode(statusCode);
    return result;
  }

  public static <T> ResponseResult systemError(Throwable e, String msg, int statusCode, T result) {
    ResponseResult<T> res = systemError(e, msg, statusCode);
    res.setResult(result);
    return res;
  }

  /**
   * 返回业务异常
   *
   * @param e
   * @param msg
   * @param <T>
   * @return
   */
  public static <T> ResponseResult businessError(Throwable e, String msg) {
    ResponseResult<T> result = new ResponseResult();
    result.setMessage(msg);
    result.setBusinessError(e.getMessage());
    result.setStatusCode(HttpServletResponse.SC_OK);
    return result;
  }

  public static <T> ResponseResult businessError(Throwable e, String msg, int statusCode) {
    ResponseResult<T> result = businessError(e, msg);
    result.setStatusCode(statusCode);
    return result;
  }

  public static <T> ResponseResult businessError(Throwable e, String msg, int statusCode,
      T result) {
    ResponseResult<T> res = businessError(e, msg, statusCode);
    res.setResult(result);
    return res;
  }

  public static <T> ResponseResult businessError(String error, String msg) {
    return businessError(error, msg, HttpServletResponse.SC_OK);
  }

  public static <T> ResponseResult businessError(String error, String msg, int statusCode) {
    ResponseResult<T> result = new ResponseResult();
    result.setMessage(msg);
    result.setBusinessError(error);
    result.setStatusCode(statusCode);
    return result;
  }

  public static <T> ResponseResult businessError(String error, String msg, int statusCode,
      T result) {
    ResponseResult<T> res = businessError(error, msg, statusCode);
    res.setResult(result);
    return res;
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getBusinessError() {
    return businessError;
  }

  public void setBusinessError(String businessError) {
    this.businessError = businessError;
  }

  public String getSystemError() {
    return systemError;
  }

  public void setSystemError(String systemError) {
    this.systemError = systemError;
  }
}
