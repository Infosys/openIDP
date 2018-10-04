/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.findbugs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "message" })
public  class LocalVariable {
	@XmlElement(name = "Message", required = true)
	protected java.lang.String message;
	@XmlAttribute(name = "name")
	protected java.lang.String name;
	@XmlAttribute(name = "register")
	protected Byte register;
	@XmlAttribute(name = "pc")
	protected Byte pc;
	@XmlAttribute(name = "role")
	protected java.lang.String role;

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String value) {
		this.message = value;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}

	public Byte getRegister() {
		return register;
	}

	public void setRegister(Byte value) {
		this.register = value;
	}

	public Byte getPc() {
		return pc;
	}

	public void setPc(Byte value) {
		this.pc = value;
	}

	public java.lang.String getRole() {
		return role;
	}

	public void setRole(java.lang.String value) {
		this.role = value;
	}
}

