/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.findbugs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.infosys.utilities.findbugs.BugCollection.Project;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "class_", "project", "method", "string", "typelist", "field", "sourceLine", "int_",
		"longMessage", "shortMessage", "localVariable" })
public class BugInstance {
	@XmlElement(name = "Project", required = false)
	protected List<Project> project;
	@XmlElement(name = "String", required = false)
	protected List<String> string;
	@XmlElement(name = "Type", required = false)
	protected List<Type> typelist;
	@XmlElement(name = "Class", required = true)
	protected List<Class> class_;
	@XmlElement(name = "Method", required = false)
	protected List<Method> method;
	@XmlElement(name = "SourceLine", required = false)
	protected List<SourceLine> sourceLine;
	@XmlElement(name = "Int", required = false)
	protected List<Int> int_;
	@XmlElement(name = "Field", required = false)
	protected List<Field> field;
	@XmlElement(name = "LongMessage", required = false)
	protected java.lang.String longMessage;
	@XmlElement(name = "ShortMessage", required = false)
	protected java.lang.String shortMessage;
	@XmlElement(name = "LocalVariable", required = false)
	protected java.lang.String localVariable;
	@XmlAttribute(name = "type")
	protected java.lang.String type;
	@XmlAttribute(name = "priority")
	protected Byte priority;
	@XmlAttribute(name = "rank")
	protected Byte rank;
	@XmlAttribute(name = "abbrev")
	protected java.lang.String abbrev;
	@XmlAttribute(name = "category")
	protected java.lang.String category;
	@XmlAttribute(name = "instanceHash")
	protected java.lang.String instanceHash;
	@XmlAttribute(name = "instanceOccurrenceNum")
	protected Byte instanceOccurrenceNum;
	@XmlAttribute(name = "instanceOccurrenceMax")
	protected Byte instanceOccurrenceMax;
	@XmlAttribute(name = "cweid")
	protected Short cweid;

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public List<String> getString() {
		return string;
	}

	public void setString(List<String> string) {
		this.string = string;
	}

	public List<Type> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<Type> typelist) {
		this.typelist = typelist;
	}

	public List<Class> getClazz() {
		return class_;
	}

	public void setClazz(List<Class> class_) {
		this.class_ = class_;
	}

	public List<Method> getMethod() {
		return method;
	}

	public void setMethod(List<Method> method) {
		this.method = method;
	}

	public List<SourceLine> getSourceLine() {
		return sourceLine;
	}

	public void setSourceLine(List<SourceLine> sourceLine) {
		this.sourceLine = sourceLine;
	}

	public List<Int> getInts() {
		return int_;
	}

	public void setInts(List<Int> int_) {
		this.int_ = int_;
	}

	public List<Field> getField() {
		return field;
	}

	public void setField(List<Field> field) {
		this.field = field;
	}

	public java.lang.String getLongMessage() {
		return longMessage;
	}

	public void setLongMessage(java.lang.String longMessage) {
		this.longMessage = longMessage;
	}

	public java.lang.String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(java.lang.String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public java.lang.String getLocalVariable() {
		return localVariable;
	}

	public void setLocalVariable(java.lang.String localVariable) {
		this.localVariable = localVariable;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String value) {
		this.type = value;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte value) {
		this.priority = value;
	}

	public Byte getRank() {
		return rank;
	}

	public void setRank(Byte value) {
		this.rank = value;
	}

	public java.lang.String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(java.lang.String value) {
		this.abbrev = value;
	}

	public java.lang.String getCategory() {
		return category;
	}

	public void setCategory(java.lang.String value) {
		this.category = value;
	}

	public java.lang.String getInstanceHash() {
		return instanceHash;
	}

	public void setInstanceHash(java.lang.String value) {
		this.instanceHash = value;
	}

	public Byte getInstanceOccurrenceNum() {
		return instanceOccurrenceNum;
	}

	public void setInstanceOccurrenceNum(Byte value) {
		this.instanceOccurrenceNum = value;
	}

	public Byte getInstanceOccurrenceMax() {
		return instanceOccurrenceMax;
	}

	public void setInstanceOccurrenceMax(Byte value) {
		this.instanceOccurrenceMax = value;
	}

	public Short getCweid() {
		return cweid;
	}

	public void setCweid(Short value) {
		this.cweid = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "sourceLine", "message" })
	public static class Class {
		@XmlElement(name = "SourceLine", required = true)
		protected Class.SourceLine sourceLine;
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "classname")
		protected java.lang.String classname;
		@XmlAttribute(name = "primary")
		protected java.lang.String primary;

		public Class.SourceLine getSourceLine() {
			return sourceLine;
		}

		public void setSourceLine(Class.SourceLine value) {
			this.sourceLine = value;
		}

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getClassname() {
			return classname;
		}

		public void setClassname(java.lang.String value) {
			this.classname = value;
		}

		public java.lang.String getPrimary() {
			return primary;
		}

		public void setPrimary(java.lang.String value) {
			this.primary = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "message" })
		public static class SourceLine {
			@XmlElement(name = "Message", required = true)
			protected java.lang.String message;
			@XmlAttribute(name = "classname")
			protected java.lang.String classname;
			@XmlAttribute(name = "start")
			protected Byte start;
			@XmlAttribute(name = "end")
			protected Short end;
			@XmlAttribute(name = "sourcefile")
			protected java.lang.String sourcefile;
			@XmlAttribute(name = "sourcepath")
			protected java.lang.String sourcepath;

			public java.lang.String getMessage() {
				return message;
			}

			public void setMessage(java.lang.String value) {
				this.message = value;
			}

			public java.lang.String getClassname() {
				return classname;
			}

			public void setClassname(java.lang.String value) {
				this.classname = value;
			}

			public Byte getStart() {
				return start;
			}

			public void setStart(Byte value) {
				this.start = value;
			}

			public Short getEnd() {
				return end;
			}

			public void setEnd(Short value) {
				this.end = value;
			}

			public java.lang.String getSourcefile() {
				return sourcefile;
			}

			public void setSourcefile(java.lang.String value) {
				this.sourcefile = value;
			}

			public java.lang.String getSourcepath() {
				return sourcepath;
			}

			public void setSourcepath(java.lang.String value) {
				this.sourcepath = value;
			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "sourceLine", "message" })
	public static class Field {
		@XmlElement(name = "SourceLine", required = true)
		protected Field.SourceLine sourceLine;
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "classname")
		protected java.lang.String classname;
		@XmlAttribute(name = "name")
		protected java.lang.String name;
		@XmlAttribute(name = "signature")
		protected java.lang.String signature;
		@XmlAttribute(name = "isStatic")
		protected java.lang.String isStatic;
		@XmlAttribute(name = "primary")
		protected java.lang.String primary;
		@XmlAttribute(name = "role")
		protected java.lang.String role;

		public Field.SourceLine getSourceLine() {
			return sourceLine;
		}

		public void setSourceLine(Field.SourceLine value) {
			this.sourceLine = value;
		}

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getClassname() {
			return classname;
		}

		public void setClassname(java.lang.String value) {
			this.classname = value;
		}

		public java.lang.String getName() {
			return name;
		}

		public void setName(java.lang.String value) {
			this.name = value;
		}

		public java.lang.String getSignature() {
			return signature;
		}

		public void setSignature(java.lang.String value) {
			this.signature = value;
		}

		public java.lang.String getIsStatic() {
			return isStatic;
		}

		public void setIsStatic(java.lang.String value) {
			this.isStatic = value;
		}

		public java.lang.String getPrimary() {
			return primary;
		}

		public void setPrimary(java.lang.String value) {
			this.primary = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "message" })
		public static class SourceLine {
			@XmlElement(name = "Message", required = true)
			protected java.lang.String message;
			@XmlAttribute(name = "classname")
			protected java.lang.String classname;
			@XmlAttribute(name = "sourcefile")
			protected java.lang.String sourcefile;
			@XmlAttribute(name = "sourcepath")
			protected java.lang.String sourcepath;

			public java.lang.String getMessage() {
				return message;
			}

			public void setMessage(java.lang.String value) {
				this.message = value;
			}

			public java.lang.String getClassname() {
				return classname;
			}

			public void setClassname(java.lang.String value) {
				this.classname = value;
			}

			public java.lang.String getSourcefile() {
				return sourcefile;
			}

			public void setSourcefile(java.lang.String value) {
				this.sourcefile = value;
			}

			public java.lang.String getSourcepath() {
				return sourcepath;
			}

			public void setSourcepath(java.lang.String value) {
				this.sourcepath = value;
			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "message" })
	public static class Int {
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "value")
		protected Byte value1;
		@XmlAttribute(name = "role")
		protected java.lang.String role;

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public Byte getValue1() {
			return value1;
		}

		public void setValue1(Byte value) {
			this.value1 = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}
	}

		@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "sourceLine", "message" })
	public static class Method {
		@XmlElement(name = "SourceLine", required = true)
		protected Method.SourceLine sourceLine;
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "classname")
		protected java.lang.String classname;
		@XmlAttribute(name = "name")
		protected java.lang.String name;
		@XmlAttribute(name = "signature")
		protected java.lang.String signature;
		@XmlAttribute(name = "isStatic")
		protected java.lang.String isStatic;
		@XmlAttribute(name = "primary")
		protected java.lang.String primary;
		@XmlAttribute(name = "role")
		protected java.lang.String role;

		public Method.SourceLine getSourceLine() {
			return sourceLine;
		}

		public void setSourceLine(Method.SourceLine value) {
			this.sourceLine = value;
		}

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getClassname() {
			return classname;
		}

		public void setClassname(java.lang.String value) {
			this.classname = value;
		}

		public java.lang.String getName() {
			return name;
		}

		public void setName(java.lang.String value) {
			this.name = value;
		}

		public java.lang.String getSignature() {
			return signature;
		}

		public void setSignature(java.lang.String value) {
			this.signature = value;
		}

		public java.lang.String getIsStatic() {
			return isStatic;
		}

		public void setIsStatic(java.lang.String value) {
			this.isStatic = value;
		}

		public java.lang.String getPrimary() {
			return primary;
		}

		public void setPrimary(java.lang.String value) {
			this.primary = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "content" })
		public static class SourceLine {
			@XmlElementRef(name = "Message", type = JAXBElement.class, required = false)
			@XmlMixed
			protected List<Serializable> content;
			@XmlAttribute(name = "classname")
			protected java.lang.String classname;
			@XmlAttribute(name = "start")
			protected Short start;
			@XmlAttribute(name = "end")
			protected Short end;
			@XmlAttribute(name = "startBytecode")
			protected Byte startBytecode;
			@XmlAttribute(name = "endBytecode")
			protected Short endBytecode;
			@XmlAttribute(name = "sourcefile")
			protected java.lang.String sourcefile;
			@XmlAttribute(name = "sourcepath")
			protected java.lang.String sourcepath;

			public List<Serializable> getContent() {
				if (content == null) {
					content = new ArrayList<Serializable>();
				}
				return this.content;
			}

			public java.lang.String getClassname() {
				return classname;
			}

			public void setClassname(java.lang.String value) {
				this.classname = value;
			}

			public Short getStart() {
				return start;
			}

			public void setStart(Short value) {
				this.start = value;
			}

			public Short getEnd() {
				return end;
			}

			public void setEnd(Short value) {
				this.end = value;
			}

			public Byte getStartBytecode() {
				return startBytecode;
			}

			public void setStartBytecode(Byte value) {
				this.startBytecode = value;
			}

			public Short getEndBytecode() {
				return endBytecode;
			}

			public void setEndBytecode(Short value) {
				this.endBytecode = value;
			}

			public java.lang.String getSourcefile() {
				return sourcefile;
			}

			public void setSourcefile(java.lang.String value) {
				this.sourcefile = value;
			}

			public java.lang.String getSourcepath() {
				return sourcepath;
			}

			public void setSourcepath(java.lang.String value) {
				this.sourcepath = value;
			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "value" })
	public static class Property {
		@XmlValue
		protected java.lang.String value;
		@XmlAttribute(name = "name")
		protected java.lang.String name;
		@XmlAttribute(name = "value")
		protected java.lang.String value1;

		public java.lang.String getValue() {
			return value;
		}

		public void setValue(java.lang.String value) {
			this.value = value;
		}

		public java.lang.String getName() {
			return name;
		}

		public void setName(java.lang.String value) {
			this.name = value;
		}

		public java.lang.String getValue1() {
			return value1;
		}

		public void setValue1(java.lang.String value) {
			this.value1 = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "message" })
	public static class SourceLine {
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "classname")
		protected java.lang.String classname;
		@XmlAttribute(name = "primary")
		protected java.lang.String primary;
		@XmlAttribute(name = "start")
		protected Short start;
		@XmlAttribute(name = "end")
		protected Short end;
		@XmlAttribute(name = "startBytecode")
		protected Short startBytecode;
		@XmlAttribute(name = "endBytecode")
		protected Short endBytecode;
		@XmlAttribute(name = "sourcefile")
		protected java.lang.String sourcefile;
		@XmlAttribute(name = "sourcepath")
		protected java.lang.String sourcepath;
		@XmlAttribute(name = "role")
		protected java.lang.String role;
		@XmlAttribute(name = "synthetic")
		protected java.lang.String synthetic;

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getClassname() {
			return classname;
		}

		public void setClassname(java.lang.String value) {
			this.classname = value;
		}

		public java.lang.String getPrimary() {
			return primary;
		}

		public void setPrimary(java.lang.String value) {
			this.primary = value;
		}

		public Short getStart() {
			return start;
		}

		public void setStart(Short value) {
			this.start = value;
		}

		public Short getEnd() {
			return end;
		}

		public void setEnd(Short value) {
			this.end = value;
		}

		public Short getStartBytecode() {
			return startBytecode;
		}

		public void setStartBytecode(Short value) {
			this.startBytecode = value;
		}

		public Short getEndBytecode() {
			return endBytecode;
		}

		public void setEndBytecode(Short value) {
			this.endBytecode = value;
		}

		public java.lang.String getSourcefile() {
			return sourcefile;
		}

		public void setSourcefile(java.lang.String value) {
			this.sourcefile = value;
		}

		public java.lang.String getSourcepath() {
			return sourcepath;
		}

		public void setSourcepath(java.lang.String value) {
			this.sourcepath = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}

		public java.lang.String getSynthetic() {
			return synthetic;
		}

		public void setSynthetic(java.lang.String value) {
			this.synthetic = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "message" })
	public static class String {
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "value")
		protected java.lang.String value1;
		@XmlAttribute(name = "role")
		protected java.lang.String role;

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getValue1() {
			return value1;
		}

		public void setValue1(java.lang.String value) {
			this.value1 = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "sourceLine", "message" })
	public static class Type {
		@XmlElement(name = "SourceLine", required = true)
		protected Type.SourceLine sourceLine;
		@XmlElement(name = "Message", required = true)
		protected java.lang.String message;
		@XmlAttribute(name = "descriptor")
		protected java.lang.String descriptor;
		@XmlAttribute(name = "role")
		protected java.lang.String role;

		public Type.SourceLine getSourceLine() {
			return sourceLine;
		}

		public void setSourceLine(Type.SourceLine value) {
			this.sourceLine = value;
		}

		public java.lang.String getMessage() {
			return message;
		}

		public void setMessage(java.lang.String value) {
			this.message = value;
		}

		public java.lang.String getDescriptor() {
			return descriptor;
		}

		public void setDescriptor(java.lang.String value) {
			this.descriptor = value;
		}

		public java.lang.String getRole() {
			return role;
		}

		public void setRole(java.lang.String value) {
			this.role = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "message" })
		public static class SourceLine {
			@XmlElement(name = "Message", required = true)
			protected java.lang.String message;
			@XmlAttribute(name = "classname")
			protected java.lang.String classname;
			@XmlAttribute(name = "sourcefile")
			protected java.lang.String sourcefile;
			@XmlAttribute(name = "sourcepath")
			protected java.lang.String sourcepath;
			@XmlAttribute(name = "start")
			protected Short start;
			@XmlAttribute(name = "end")
			protected Short end;

			public java.lang.String getMessage() {
				return message;
			}

			public void setMessage(java.lang.String value) {
				this.message = value;
			}

			public java.lang.String getClassname() {
				return classname;
			}

			public void setClassname(java.lang.String value) {
				this.classname = value;
			}

			public java.lang.String getSourcefile() {
				return sourcefile;
			}

			public void setSourcefile(java.lang.String value) {
				this.sourcefile = value;
			}

			public java.lang.String getSourcepath() {
				return sourcepath;
			}

			public void setSourcepath(java.lang.String value) {
				this.sourcepath = value;
			}

			public Short getStart() {
				return start;
			}

			public void setStart(Short value) {
				this.start = value;
			}

			public Short getEnd() {
				return end;
			}

			public void setEnd(Short value) {
				this.end = value;
			}
		}
	}
}