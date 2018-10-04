/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

public class CreateBuildLog {
	private static final Logger logger = Logger.getLogger(CreateBuildLog.class);

	private CreateBuildLog() {
	}

	public static void createBuildLog(String server, String username, String password, String jobName, String destPath,
			String time, String id, String appName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String tempJobName = jobName.replaceAll("/", "/job/").replaceAll(" ", "%20");
			String webPage = server + "/job/" + tempJobName + "/lastBuild/consoleText";
			String path = null;
			String authString = username + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			inputStream = urlConnection.getInputStream();
			if (id != null) {
				path = destPath + "//" + appName + "_" + "buildlog" + "_id_" + id + "_" + time + ".txt";
			} else {
				path = destPath + "//" + appName + "_" + "buildlog" + "_" + time + ".txt";
			}
			outputStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			logger.info("Buildlog created..!!");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
