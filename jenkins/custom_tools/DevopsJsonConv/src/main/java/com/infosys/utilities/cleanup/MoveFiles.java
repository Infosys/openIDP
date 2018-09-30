/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class MoveFiles {
	final static Logger logger = Logger.getLogger(MoveFiles.class);

	private MoveFiles() {
	}

	public static Boolean move(String srcPath, String destPath) {
		Boolean status = false;
		try (InputStream inStream = new FileInputStream(new File(srcPath));
				OutputStream outStream = new FileOutputStream(new File(destPath))) {
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0)
				outStream.write(buffer, 0, length);

			return (new File(srcPath)).delete();

		} catch (IOException e) {
			logger.error(e);
		}
		return status;
	}
}
