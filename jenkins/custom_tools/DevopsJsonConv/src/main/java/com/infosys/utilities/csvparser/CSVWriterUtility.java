/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.csvparser;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVWriterUtility {
	private static final char DEFAULT_SEPARATOR = ',';

	private CSVWriterUtility() {
	}

	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
		boolean first = true;
		char newSeparators=separators;
		// default customQuote is empty
		if (newSeparators == ' ') {
			newSeparators = DEFAULT_SEPARATOR;
		}
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(newSeparators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}
			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}

	private static String followCVSformat(String value) {
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;
	}
}
