/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.fxcop;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "target" })
public  class Targets {
	@XmlElement(name = "Target")
	protected List<Target> target;

	public List<Target> getTarget() {
		if (target == null) {
			target = new ArrayList<Target>();
		}
		return this.target;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "modules" })
	public static class Target {
		@XmlElement(name = "Modules", required = true)
		protected Target.Modules modules;
		@XmlAttribute(name = "Name")
		protected String name;

		public Target.Modules getModules() {
			return modules;
		}

		public void setModules(Target.Modules value) {
			this.modules = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String value) {
			this.name = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "module" })
		public static class Modules {
			@XmlElement(name = "Module", required = true)
			protected List<Module> module;

			public List<Target.Modules.Module> getModule() {
				return module;
			}

			public void setModule(List<Target.Modules.Module> value) {
				this.module = value;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "messages", "namespaces" })
			public static class Module {
				@XmlElement(name = "Messages", required = true)
				protected Target.Modules.Module.Messages messages;
				@XmlElement(name = "Namespaces")
				protected Target.Modules.Module.Namespaces namespaces;
				@XmlAttribute(name = "Name")
				protected String name;

				public Target.Modules.Module.Messages getMessages() {
					return messages;
				}

				public void setMessages(Target.Modules.Module.Messages value) {
					this.messages = value;
				}

				public Target.Modules.Module.Namespaces getNamespaces() {
					return namespaces;
				}

				public void setNamespaces(Target.Modules.Module.Namespaces value) {
					this.namespaces = value;
				}

				public String getName() {
					return name;
				}

				public void setName(String value) {
					this.name = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "message" })
				public static class Messages {
					@XmlElement(name = "Message")
					protected List<Target.Modules.Module.Messages.Message> message;

					public List<Target.Modules.Module.Messages.Message> getMessage() {
						if (message == null) {
							message = new ArrayList<Target.Modules.Module.Messages.Message>();
						}
						return this.message;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "issue" })
					public static class Message {
						@XmlElement(name = "Issue", required = true)
						protected Target.Modules.Module.Messages.Message.Issue issue;
						@XmlAttribute(name = "TypeName")
						protected String typeName;
						@XmlAttribute(name = "Category")
						protected String category;
						@XmlAttribute(name = "CheckId")
						protected String checkId;
						@XmlAttribute(name = "Status")
						protected String status;
						@XmlAttribute(name = "Created")
						protected String created;
						@XmlAttribute(name = "FixCategory")
						protected String fixCategory;

						public Target.Modules.Module.Messages.Message.Issue getIssue() {
							return issue;
						}

						public void setIssue(
								Target.Modules.Module.Messages.Message.Issue value) {
							this.issue = value;
						}

						public String getTypeName() {
							return typeName;
						}

						public void setTypeName(String value) {
							this.typeName = value;
						}

						public String getCategory() {
							return category;
						}

						public void setCategory(String value) {
							this.category = value;
						}

						public String getCheckId() {
							return checkId;
						}

						public void setCheckId(String value) {
							this.checkId = value;
						}

						public String getStatus() {
							return status;
						}

						public void setStatus(String value) {
							this.status = value;
						}

						public String getCreated() {
							return created;
						}

						public void setCreated(String value) {
							this.created = value;
						}

						public String getFixCategory() {
							return fixCategory;
						}

						public void setFixCategory(String value) {
							this.fixCategory = value;
						}

						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "value" })
						public static class Issue {
							@XmlValue
							protected String value;
							@XmlAttribute(name = "Name")
							protected String name;
							@XmlAttribute(name = "Certainty")
							protected Byte certainty;
							@XmlAttribute(name = "Level")
							protected String level;

							public String getValue() {
								return value;
							}

							public void setValue(String value) {
								this.value = value;
							}

							public String getName() {
								return name;
							}

							public void setName(String value) {
								this.name = value;
							}

							public Byte getCertainty() {
								return certainty;
							}

							public void setCertainty(Byte value) {
								this.certainty = value;
							}

							public String getLevel() {
								return level;
							}

							public void setLevel(String value) {
								this.level = value;
							}
						}
					}
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "namespace" })
				public static class Namespaces {
					@XmlElement(name = "Namespace", required = true)
					protected List<Namespace> namespace;

					public List<Namespace> getNamespace() {
						return namespace;
					}

					public void setNamespace(
							List<Target.Modules.Module.Namespaces.Namespace> value) {
						this.namespace = value;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "types" })
					public static class Namespace {
						@XmlElement(name = "Types", required = true)
						protected Target.Modules.Module.Namespaces.Namespace.Types types;
						@XmlAttribute(name = "Name")
						protected String name;

						public Target.Modules.Module.Namespaces.Namespace.Types getTypes() {
							return types;
						}

						public void setTypes(
								Target.Modules.Module.Namespaces.Namespace.Types value) {
							this.types = value;
						}

						public String getName() {
							return name;
						}

						public void setName(String value) {
							this.name = value;
						}

						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "type" })
						public static class Types {
							@XmlElement(name = "Type")
							protected List<Target.Modules.Module.Namespaces.Namespace.Types.Type> type;

							public List<Target.Modules.Module.Namespaces.Namespace.Types.Type> getType() {
								if (type == null) {
									type = new ArrayList<Target.Modules.Module.Namespaces.Namespace.Types.Type>();
								}
								return this.type;
							}

							@XmlAccessorType(XmlAccessType.FIELD)
							@XmlType(name = "", propOrder = { "messages", "members" })
							public static class Type {
								@XmlElement(name = "Messages")
								protected Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages messages;
								@XmlElement(name = "Members", required = true)
								protected Target.Modules.Module.Namespaces.Namespace.Types.Type.Members members;
								@XmlAttribute(name = "Name")
								protected String name;
								@XmlAttribute(name = "Kind")
								protected String kind;
								@XmlAttribute(name = "Accessibility")
								protected String accessibility;
								@XmlAttribute(name = "ExternallyVisible")
								protected String externallyVisible;

								public Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages getMessages() {
									return messages;
								}

								public void setMessages(
										Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages value) {
									this.messages = value;
								}

								public Target.Modules.Module.Namespaces.Namespace.Types.Type.Members getMembers() {
									return members;
								}

								public void setMembers(
										Target.Modules.Module.Namespaces.Namespace.Types.Type.Members value) {
									this.members = value;
								}

								public String getName() {
									return name;
								}

								public void setName(String value) {
									this.name = value;
								}

								public String getKind() {
									return kind;
								}

								public void setKind(String value) {
									this.kind = value;
								}

								public String getAccessibility() {
									return accessibility;
								}

								public void setAccessibility(String value) {
									this.accessibility = value;
								}

								public String getExternallyVisible() {
									return externallyVisible;
								}

								public void setExternallyVisible(String value) {
									this.externallyVisible = value;
								}

								@XmlAccessorType(XmlAccessType.FIELD)
								@XmlType(name = "", propOrder = { "member" })
								public static class Members {
									@XmlElement(name = "Member")
									protected List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member> member;

									public List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member> getMember() {
										if (member == null) {
											member = new ArrayList<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member>();
										}
										return this.member;
									}

									@XmlAccessorType(XmlAccessType.FIELD)
									@XmlType(name = "", propOrder = { "messages" })
									public static class Member {
										@XmlElement(name = "Messages", required = true)
										protected Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages messages;
										@XmlAttribute(name = "Name")
										protected String name;
										@XmlAttribute(name = "Kind")
										protected String kind;
										@XmlAttribute(name = "Static")
										protected String _static;
										@XmlAttribute(name = "Accessibility")
										protected String accessibility;
										@XmlAttribute(name = "ExternallyVisible")
										protected String externallyVisible;

										public Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages getMessages() {
											return messages;
										}

										public void setMessages(
												Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages value) {
											this.messages = value;
										}

										public String getName() {
											return name;
										}

										public void setName(String value) {
											this.name = value;
										}

										public String getKind() {
											return kind;
										}

										public void setKind(String value) {
											this.kind = value;
										}

										public String getStatic() {
											return _static;
										}

										public void setStatic(String value) {
											this._static = value;
										}

										public String getAccessibility() {
											return accessibility;
										}

										public void setAccessibility(String value) {
											this.accessibility = value;
										}

										public String getExternallyVisible() {
											return externallyVisible;
										}

										public void setExternallyVisible(String value) {
											this.externallyVisible = value;
										}

										@XmlAccessorType(XmlAccessType.FIELD)
										@XmlType(name = "", propOrder = { "message" })
										public static class Messages {
											@XmlElement(name = "Message")
											protected List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message> message;

											public List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message> getMessage() {
												if (message == null) {
													message = new ArrayList<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message>();
												}
												return this.message;
											}

											@XmlAccessorType(XmlAccessType.FIELD)
											@XmlType(name = "", propOrder = { "issue" })
											public static class Message {
												@XmlElement(name = "Issue")
												protected List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue> issue;
												@XmlAttribute(name = "Id")
												protected String id;
												@XmlAttribute(name = "TypeName")
												protected String typeName;
												@XmlAttribute(name = "Category")
												protected String category;
												@XmlAttribute(name = "CheckId")
												protected String checkId;
												@XmlAttribute(name = "Status")
												protected String status;
												@XmlAttribute(name = "Created")
												protected String created;
												@XmlAttribute(name = "FixCategory")
												protected String fixCategory;

												public List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue> getIssue() {
													if (issue == null) {
														issue = new ArrayList<Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue>();
													}
													return this.issue;
												}

												public String getId() {
													return id;
												}

												public void setId(String value) {
													this.id = value;
												}

												public String getTypeName() {
													return typeName;
												}

												public void setTypeName(String value) {
													this.typeName = value;
												}

												public String getCategory() {
													return category;
												}

												public void setCategory(String value) {
													this.category = value;
												}

												public String getCheckId() {
													return checkId;
												}

												public void setCheckId(String value) {
													this.checkId = value;
												}

												public String getStatus() {
													return status;
												}

												public void setStatus(String value) {
													this.status = value;
												}

												public String getCreated() {
													return created;
												}

												public void setCreated(String value) {
													this.created = value;
												}

												public String getFixCategory() {
													return fixCategory;
												}

												public void setFixCategory(String value) {
													this.fixCategory = value;
												}

												@XmlAccessorType(XmlAccessType.FIELD)
												@XmlType(name = "", propOrder = { "value" })
												public static class Issue {
													@XmlValue
													protected String value;
													@XmlAttribute(name = "Name")
													protected String name;
													@XmlAttribute(name = "Certainty")
													protected Byte certainty;
													@XmlAttribute(name = "Level")
													protected String level;
													@XmlAttribute(name = "Path")
													protected String path;
													@XmlAttribute(name = "File")
													protected String file;
													@XmlAttribute(name = "Line")
													protected Byte line;

													public String getValue() {
														return value;
													}

													public void setValue(String value) {
														this.value = value;
													}

													public String getName() {
														return name;
													}

													public void setName(String value) {
														this.name = value;
													}

													public Byte getCertainty() {
														return certainty;
													}

													public void setCertainty(Byte value) {
														this.certainty = value;
													}

													public String getLevel() {
														return level;
													}

													public void setLevel(String value) {
														this.level = value;
													}

													public String getPath() {
														return path;
													}

													public void setPath(String value) {
														this.path = value;
													}

													public String getFile() {
														return file;
													}

													public void setFile(String value) {
														this.file = value;
													}

													public Byte getLine() {
														return line;
													}

													public void setLine(Byte value) {
														this.line = value;
													}
												}
											}
										}
									}
								}

								@XmlAccessorType(XmlAccessType.FIELD)
								@XmlType(name = "", propOrder = { "message" })
								public static class Messages {
									@XmlElement(name = "Message", required = true)
									protected List<Message> message;

									public List<Message> getMessage() {
										return message;
									}

									public void setMessage(
											List<Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message> value) {
										this.message = value;
									}

									@XmlAccessorType(XmlAccessType.FIELD)
									@XmlType(name = "", propOrder = { "issue" })
									public static class Message {
										@XmlElement(name = "Issue", required = true)
										protected Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message.Issue issue;
										@XmlAttribute(name = "Id")
										protected String id;
										@XmlAttribute(name = "TypeName")
										protected String typeName;
										@XmlAttribute(name = "Category")
										protected String category;
										@XmlAttribute(name = "CheckId")
										protected String checkId;
										@XmlAttribute(name = "Status")
										protected String status;
										@XmlAttribute(name = "Created")
										protected String created;
										@XmlAttribute(name = "FixCategory")
										protected String fixCategory;

										public Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message.Issue getIssue() {
											return issue;
										}

										public void setIssue(
												Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message.Issue value) {
											this.issue = value;
										}

										public String getId() {
											return id;
										}

										public void setId(String value) {
											this.id = value;
										}

										public String getTypeName() {
											return typeName;
										}

										public void setTypeName(String value) {
											this.typeName = value;
										}

										public String getCategory() {
											return category;
										}

										public void setCategory(String value) {
											this.category = value;
										}

										public String getCheckId() {
											return checkId;
										}

										public void setCheckId(String value) {
											this.checkId = value;
										}

										public String getStatus() {
											return status;
										}

										public void setStatus(String value) {
											this.status = value;
										}

										public String getCreated() {
											return created;
										}

										public void setCreated(String value) {
											this.created = value;
										}

										public String getFixCategory() {
											return fixCategory;
										}

										public void setFixCategory(String value) {
											this.fixCategory = value;
										}

										@XmlAccessorType(XmlAccessType.FIELD)
										@XmlType(name = "", propOrder = { "value" })
										public static class Issue {
											@XmlValue
											protected String value;
											@XmlAttribute(name = "Name")
											protected String name;
											@XmlAttribute(name = "Certainty")
											protected Byte certainty;
											@XmlAttribute(name = "Level")
											protected String level;

											public String getValue() {
												return value;
											}

											public void setValue(String value) {
												this.value = value;
											}

											public String getName() {
												return name;
											}

											public void setName(String value) {
												this.name = value;
											}

											public Byte getCertainty() {
												return certainty;
											}

											public void setCertainty(Byte value) {
												this.certainty = value;
											}

											public String getLevel() {
												return level;
											}

											public void setLevel(String value) {
												this.level = value;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

