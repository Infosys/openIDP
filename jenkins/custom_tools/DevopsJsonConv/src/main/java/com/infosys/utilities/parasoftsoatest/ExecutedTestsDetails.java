/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.parasoftsoatest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "total" })
public class ExecutedTestsDetails {
	@XmlElement(name = "Total", required = true)
	protected ExecutedTestsDetails.Total total;
	@XmlAttribute(name = "type")
	protected String type;

	public ExecutedTestsDetails.Total getTotal() {
		return total;
	}

	public void setTotal(ExecutedTestsDetails.Total value) {
		this.total = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "project" })
	public static class Total {
		@XmlElement(name = "Project", required = true)
		protected ExecutedTestsDetails.Total.Project project;
		@XmlAttribute(name = "authChange")
		protected String authChange;
		@XmlAttribute(name = "authFail")
		protected String authFail;
		@XmlAttribute(name = "change")
		protected Integer change;
		@XmlAttribute(name = "changePass")
		protected Integer changePass;
		@XmlAttribute(name = "changeTotal")
		protected Integer changeTotal;
		@XmlAttribute(name = "fail")
		protected Integer fail;
		@XmlAttribute(name = "name")
		protected String name;
		@XmlAttribute(name = "pass")
		protected Integer pass;
		@XmlAttribute(name = "total")
		protected Integer total;

		public ExecutedTestsDetails.Total.Project getProject() {
			return project;
		}

		public void setProject(ExecutedTestsDetails.Total.Project value) {
			this.project = value;
		}

		public String getAuthChange() {
			return authChange;
		}

		public void setAuthChange(String value) {
			this.authChange = value;
		}

		public String getAuthFail() {
			return authFail;
		}

		public void setAuthFail(String value) {
			this.authFail = value;
		}

		public Integer getChange() {
			return change;
		}

		public void setChange(Integer value) {
			this.change = value;
		}

		public Integer getChangePass() {
			return changePass;
		}

		public void setChangePass(Integer value) {
			this.changePass = value;
		}

		public Integer getChangeTotal() {
			return changeTotal;
		}

		public void setChangeTotal(Integer value) {
			this.changeTotal = value;
		}

		public Integer getFail() {
			return fail;
		}

		public void setFail(Integer value) {
			this.fail = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String value) {
			this.name = value;
		}

		public Integer getPass() {
			return pass;
		}

		public void setPass(Integer value) {
			this.pass = value;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer value) {
			this.total = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "testSuite" })
		public static class Project {
			@XmlElement(name = "TestSuite", required = true)
			protected ExecutedTestsDetails.Total.Project.TestSuite1 testSuite;
			@XmlAttribute(name = "authChange")
			protected String authChange;
			@XmlAttribute(name = "authFail")
			protected String authFail;
			@XmlAttribute(name = "change")
			protected Integer change;
			@XmlAttribute(name = "changePass")
			protected Integer changePass;
			@XmlAttribute(name = "changeTotal")
			protected Integer changeTotal;
			@XmlAttribute(name = "fail")
			protected Integer fail;
			@XmlAttribute(name = "name")
			protected String name;
			@XmlAttribute(name = "pass")
			protected Integer pass;
			@XmlAttribute(name = "total")
			protected Integer total;

			public ExecutedTestsDetails.Total.Project.TestSuite1 getTestSuite() {
				return testSuite;
			}

			public void setTestSuite(ExecutedTestsDetails.Total.Project.TestSuite1 value) {
				this.testSuite = value;
			}

			public String getAuthChange() {
				return authChange;
			}

			public void setAuthChange(String value) {
				this.authChange = value;
			}

			public String getAuthFail() {
				return authFail;
			}

			public void setAuthFail(String value) {
				this.authFail = value;
			}

			public Integer getChange() {
				return change;
			}

			public void setChange(Integer value) {
				this.change = value;
			}

			public Integer getChangePass() {
				return changePass;
			}

			public void setChangePass(Integer value) {
				this.changePass = value;
			}

			public Integer getChangeTotal() {
				return changeTotal;
			}

			public void setChangeTotal(Integer value) {
				this.changeTotal = value;
			}

			public Integer getFail() {
				return fail;
			}

			public void setFail(Integer value) {
				this.fail = value;
			}

			public String getName() {
				return name;
			}

			public void setName(String value) {
				this.name = value;
			}

			public Integer getPass() {
				return pass;
			}

			public void setPass(Integer value) {
				this.pass = value;
			}

			public Integer getTotal() {
				return total;
			}

			public void setTotal(Integer value) {
				this.total = value;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "testSuite" })
			public static class TestSuite1 {
				@XmlElement(name = "TestSuite", required = true)
				protected ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2 testSuite;
				@XmlAttribute(name = "authChange")
				protected String authChange;
				@XmlAttribute(name = "authFail")
				protected String authFail;
				@XmlAttribute(name = "change")
				protected Integer change;
				@XmlAttribute(name = "changePass")
				protected Integer changePass;
				@XmlAttribute(name = "changeTotal")
				protected Integer changeTotal;
				@XmlAttribute(name = "fail")
				protected Integer fail;
				@XmlAttribute(name = "id")
				protected String id;
				@XmlAttribute(name = "name")
				protected String name;
				@XmlAttribute(name = "pass")
				protected Integer pass;
				@XmlAttribute(name = "total")
				protected Integer total;

				public ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2 getTestSuite() {
					return testSuite;
				}

				public void setTestSuite(
						ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2 value) {
					this.testSuite = value;
				}

				public String getAuthChange() {
					return authChange;
				}

				public void setAuthChange(String value) {
					this.authChange = value;
				}

				public String getAuthFail() {
					return authFail;
				}

				public void setAuthFail(String value) {
					this.authFail = value;
				}

				public Integer getChange() {
					return change;
				}

				public void setChange(Integer value) {
					this.change = value;
				}

				public Integer getChangePass() {
					return changePass;
				}

				public void setChangePass(Integer value) {
					this.changePass = value;
				}

				public Integer getChangeTotal() {
					return changeTotal;
				}

				public void setChangeTotal(Integer value) {
					this.changeTotal = value;
				}

				public Integer getFail() {
					return fail;
				}

				public void setFail(Integer value) {
					this.fail = value;
				}

				public String getId() {
					return id;
				}

				public void setId(String value) {
					this.id = value;
				}

				public String getName() {
					return name;
				}

				public void setName(String value) {
					this.name = value;
				}

				public Integer getPass() {
					return pass;
				}

				public void setPass(Integer value) {
					this.pass = value;
				}

				public Integer getTotal() {
					return total;
				}

				public void setTotal(Integer value) {
					this.total = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "testSuite" })
				public static class TestSuite2 {
					@XmlElement(name = "TestSuite", required = true)
					protected ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3 testSuite;
					@XmlAttribute(name = "auth")
					protected String auth;
					@XmlAttribute(name = "authChange")
					protected String authChange;
					@XmlAttribute(name = "authFail")
					protected String authFail;
					@XmlAttribute(name = "change")
					protected Integer change;
					@XmlAttribute(name = "changePass")
					protected Integer changePass;
					@XmlAttribute(name = "changeTotal")
					protected Integer changeTotal;
					@XmlAttribute(name = "fail")
					protected Integer fail;
					@XmlAttribute(name = "id")
					protected String id;
					@XmlAttribute(name = "name")
					protected String name;
					@XmlAttribute(name = "pass")
					protected Integer pass;
					@XmlAttribute(name = "root")
					protected String root;
					@XmlAttribute(name = "total")
					protected Integer total;

					public ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3 getTestSuite() {
						return testSuite;
					}

					public void setTestSuite(
							ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3 value) {
						this.testSuite = value;
					}

					public String getAuth() {
						return auth;
					}

					public void setAuth(String value) {
						this.auth = value;
					}

					public String getAuthChange() {
						return authChange;
					}

					public void setAuthChange(String value) {
						this.authChange = value;
					}

					public String getAuthFail() {
						return authFail;
					}

					public void setAuthFail(String value) {
						this.authFail = value;
					}

					public Integer getChange() {
						return change;
					}

					public void setChange(Integer value) {
						this.change = value;
					}

					public Integer getChangePass() {
						return changePass;
					}

					public void setChangePass(Integer value) {
						this.changePass = value;
					}

					public Integer getChangeTotal() {
						return changeTotal;
					}

					public void setChangeTotal(Integer value) {
						this.changeTotal = value;
					}

					public Integer getFail() {
						return fail;
					}

					public void setFail(Integer value) {
						this.fail = value;
					}

					public String getId() {
						return id;
					}

					public void setId(String value) {
						this.id = value;
					}

					public String getName() {
						return name;
					}

					public void setName(String value) {
						this.name = value;
					}

					public Integer getPass() {
						return pass;
					}

					public void setPass(Integer value) {
						this.pass = value;
					}

					public String getRoot() {
						return root;
					}

					public void setRoot(String value) {
						this.root = value;
					}

					public Integer getTotal() {
						return total;
					}

					public void setTotal(Integer value) {
						this.total = value;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "testSuite" })
					public static class TestSuite3 {
						@XmlElement(name = "TestSuite", required = true)
						protected ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4 testSuite;
						@XmlAttribute(name = "auth")
						protected String auth;
						@XmlAttribute(name = "authChange")
						protected String authChange;
						@XmlAttribute(name = "authFail")
						protected String authFail;
						@XmlAttribute(name = "change")
						protected Integer change;
						@XmlAttribute(name = "changePass")
						protected Integer changePass;
						@XmlAttribute(name = "changeTotal")
						protected Integer changeTotal;
						@XmlAttribute(name = "fail")
						protected Integer fail;
						@XmlAttribute(name = "id")
						protected String id;
						@XmlAttribute(name = "name")
						protected String name;
						@XmlAttribute(name = "pass")
						protected Integer pass;
						@XmlAttribute(name = "total")
						protected Integer total;

						public ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4 getTestSuite() {
							return testSuite;
						}

						public void setTestSuite(
								ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4 value) {
							this.testSuite = value;
						}

						public String getAuth() {
							return auth;
						}

						public void setAuth(String value) {
							this.auth = value;
						}

						public String getAuthChange() {
							return authChange;
						}

						public void setAuthChange(String value) {
							this.authChange = value;
						}

						public String getAuthFail() {
							return authFail;
						}

						public void setAuthFail(String value) {
							this.authFail = value;
						}

						public Integer getChange() {
							return change;
						}

						public void setChange(Integer value) {
							this.change = value;
						}

						public Integer getChangePass() {
							return changePass;
						}

						public void setChangePass(Integer value) {
							this.changePass = value;
						}

						public Integer getChangeTotal() {
							return changeTotal;
						}

						public void setChangeTotal(Integer value) {
							this.changeTotal = value;
						}

						public Integer getFail() {
							return fail;
						}

						public void setFail(Integer value) {
							this.fail = value;
						}

						public String getId() {
							return id;
						}

						public void setId(String value) {
							this.id = value;
						}

						public String getName() {
							return name;
						}

						public void setName(String value) {
							this.name = value;
						}

						public Integer getPass() {
							return pass;
						}

						public void setPass(Integer value) {
							this.pass = value;
						}

						public Integer getTotal() {
							return total;
						}

						public void setTotal(Integer value) {
							this.total = value;
						}

						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "testSuite" })
						public static class TestSuite4 {
							@XmlElement(name = "TestSuite", required = true)
							protected ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5 testSuite;
							@XmlAttribute(name = "auth")
							protected String auth;
							@XmlAttribute(name = "authChange")
							protected String authChange;
							@XmlAttribute(name = "authFail")
							protected String authFail;
							@XmlAttribute(name = "change")
							protected Integer change;
							@XmlAttribute(name = "changePass")
							protected Integer changePass;
							@XmlAttribute(name = "changeTotal")
							protected Integer changeTotal;
							@XmlAttribute(name = "fail")
							protected Integer fail;
							@XmlAttribute(name = "id")
							protected String id;
							@XmlAttribute(name = "name")
							protected String name;
							@XmlAttribute(name = "pass")
							protected Integer pass;
							@XmlAttribute(name = "total")
							protected Integer total;

							public ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5 getTestSuite() {
								return testSuite;
							}

							public void setTestSuite(
									ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5 value) {
								this.testSuite = value;
							}

							public String getAuth() {
								return auth;
							}

							public void setAuth(String value) {
								this.auth = value;
							}

							public String getAuthChange() {
								return authChange;
							}

							public void setAuthChange(String value) {
								this.authChange = value;
							}

							public String getAuthFail() {
								return authFail;
							}

							public void setAuthFail(String value) {
								this.authFail = value;
							}

							public Integer getChange() {
								return change;
							}

							public void setChange(Integer value) {
								this.change = value;
							}

							public Integer getChangePass() {
								return changePass;
							}

							public void setChangePass(Integer value) {
								this.changePass = value;
							}

							public Integer getChangeTotal() {
								return changeTotal;
							}

							public void setChangeTotal(Integer value) {
								this.changeTotal = value;
							}

							public Integer getFail() {
								return fail;
							}

							public void setFail(Integer value) {
								this.fail = value;
							}

							public String getId() {
								return id;
							}

							public void setId(String value) {
								this.id = value;
							}

							public String getName() {
								return name;
							}

							public void setName(String value) {
								this.name = value;
							}

							public Integer getPass() {
								return pass;
							}

							public void setPass(Integer value) {
								this.pass = value;
							}

							public Integer getTotal() {
								return total;
							}

							public void setTotal(Integer value) {
								this.total = value;
							}

							@XmlAccessorType(XmlAccessType.FIELD)
							@XmlType(name = "", propOrder = { "test" })
							public static class TestSuite5 {
								@XmlElement(name = "Test", required = true)
								protected ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5.Test test;
								@XmlAttribute(name = "auth")
								protected String auth;
								@XmlAttribute(name = "authChange")
								protected String authChange;
								@XmlAttribute(name = "authFail")
								protected String authFail;
								@XmlAttribute(name = "change")
								protected Integer change;
								@XmlAttribute(name = "changePass")
								protected Integer changePass;
								@XmlAttribute(name = "changeTotal")
								protected Integer changeTotal;
								@XmlAttribute(name = "fail")
								protected Integer fail;
								@XmlAttribute(name = "id")
								protected String id;
								@XmlAttribute(name = "name")
								protected String name;
								@XmlAttribute(name = "pass")
								protected Integer pass;
								@XmlAttribute(name = "total")
								protected Integer total;

								public ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5.Test getTest() {
									return test;
								}

								public void setTest(
										ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5.Test value) {
									this.test = value;
								}

								public String getAuth() {
									return auth;
								}

								public void setAuth(String value) {
									this.auth = value;
								}

								public String getAuthChange() {
									return authChange;
								}

								public void setAuthChange(String value) {
									this.authChange = value;
								}

								public String getAuthFail() {
									return authFail;
								}

								public void setAuthFail(String value) {
									this.authFail = value;
								}

								public Integer getChange() {
									return change;
								}

								public void setChange(Integer value) {
									this.change = value;
								}

								public Integer getChangePass() {
									return changePass;
								}

								public void setChangePass(Integer value) {
									this.changePass = value;
								}

								public Integer getChangeTotal() {
									return changeTotal;
								}

								public void setChangeTotal(Integer value) {
									this.changeTotal = value;
								}

								public Integer getFail() {
									return fail;
								}

								public void setFail(Integer value) {
									this.fail = value;
								}

								public String getId() {
									return id;
								}

								public void setId(String value) {
									this.id = value;
								}

								public String getName() {
									return name;
								}

								public void setName(String value) {
									this.name = value;
								}

								public Integer getPass() {
									return pass;
								}

								public void setPass(Integer value) {
									this.pass = value;
								}

								public Integer getTotal() {
									return total;
								}

								public void setTotal(Integer value) {
									this.total = value;
								}

								@XmlAccessorType(XmlAccessType.FIELD)
								@XmlType(name = "")
								public static class Test {
									@XmlAttribute(name = "auth")
									protected String auth;
									@XmlAttribute(name = "authChange")
									protected String authChange;
									@XmlAttribute(name = "authFail")
									protected String authFail;
									@XmlAttribute(name = "change")
									protected Integer change;
									@XmlAttribute(name = "changePass")
									protected Integer changePass;
									@XmlAttribute(name = "changeTotal")
									protected Integer changeTotal;
									@XmlAttribute(name = "fail")
									protected Integer fail;
									@XmlAttribute(name = "id")
									protected String id;
									@XmlAttribute(name = "name")
									protected String name;
									@XmlAttribute(name = "pass")
									protected Integer pass;
									@XmlAttribute(name = "tool")
									protected String tool;
									@XmlAttribute(name = "total")
									protected Integer total;

									public String getAuth() {
										return auth;
									}

									public void setAuth(String value) {
										this.auth = value;
									}

									public String getAuthChange() {
										return authChange;
									}

									public void setAuthChange(String value) {
										this.authChange = value;
									}

									public String getAuthFail() {
										return authFail;
									}

									public void setAuthFail(String value) {
										this.authFail = value;
									}

									public Integer getChange() {
										return change;
									}

									public void setChange(Integer value) {
										this.change = value;
									}

									public Integer getChangePass() {
										return changePass;
									}

									public void setChangePass(Integer value) {
										this.changePass = value;
									}

									public Integer getChangeTotal() {
										return changeTotal;
									}

									public void setChangeTotal(Integer value) {
										this.changeTotal = value;
									}

									public Integer getFail() {
										return fail;
									}

									public void setFail(Integer value) {
										this.fail = value;
									}

									public String getId() {
										return id;
									}

									public void setId(String value) {
										this.id = value;
									}

									public String getName() {
										return name;
									}

									public void setName(String value) {
										this.name = value;
									}

									public Integer getPass() {
										return pass;
									}

									public void setPass(Integer value) {
										this.pass = value;
									}

									public String getTool() {
										return tool;
									}

									public void setTool(String value) {
										this.tool = value;
									}

									public Integer getTotal() {
										return total;
									}

									public void setTotal(Integer value) {
										this.total = value;
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
