/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.xmlparser;

public class ParserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ParserException(String msg, Exception e) {
		super(msg, e);
	}
}
