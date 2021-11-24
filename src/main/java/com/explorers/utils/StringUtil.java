package com.explorers.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.validation.constraints.NotNull;

/**
 * @author wugaobo
 */
public class StringUtil {

  public static String getStackTrace(@NotNull Throwable e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    String var3;
    try {
      e.printStackTrace(pw);
      var3 = sw.toString();
    } finally {
      pw.close();
    }

    return var3;
  }
}
