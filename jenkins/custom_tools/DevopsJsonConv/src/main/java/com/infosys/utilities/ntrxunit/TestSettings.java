/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.ntrxunit;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "description", "deployment", "execution", "properties" })
public class TestSettings {
	@XmlElement(name = "Description", required = true)
	protected String description;
	@XmlElement(name = "Deployment", required = true)
	protected TestSettings.Deployment deployment;
	@XmlElement(name = "Execution", required = true)
	protected TestSettings.Execution execution;
	@XmlElement(name = "Properties", required = true)
	protected Object properties;
	@XmlAttribute(name = "id")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public TestSettings.Deployment getDeployment() {
		return deployment;
	}

	public void setDeployment(TestSettings.Deployment value) {
		this.deployment = value;
	}

	public TestSettings.Execution getExecution() {
		return execution;
	}

	public void setExecution(TestSettings.Execution value) {
		this.execution = value;
	}

	public Object getProperties() {
		return properties;
	}

	public void setProperties(Object value) {
		this.properties = value;
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

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Deployment {
		@XmlAttribute(name = "runDeploymentRoot")
		protected String runDeploymentRoot;
		@XmlAttribute(name = "useDefaultDeploymentRoot")
		protected String useDefaultDeploymentRoot;
		@XmlAttribute(name = "userDeploymentRoot")
		protected String userDeploymentRoot;

		public String getRunDeploymentRoot() {
			return runDeploymentRoot;
		}

		public void setRunDeploymentRoot(String value) {
			this.runDeploymentRoot = value;
		}

		public String getUseDefaultDeploymentRoot() {
			return useDefaultDeploymentRoot;
		}

		public void setUseDefaultDeploymentRoot(String value) {
			this.useDefaultDeploymentRoot = value;
		}

		public String getUserDeploymentRoot() {
			return userDeploymentRoot;
		}

		public void setUserDeploymentRoot(String value) {
			this.userDeploymentRoot = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "testTypeSpecific", "agentRule" })
	public static class Execution {
		@XmlElement(name = "TestTypeSpecific", required = true)
		protected TestSettings.Execution.TestTypeSpecific testTypeSpecific;
		@XmlElement(name = "AgentRule", required = true)
		protected TestSettings.Execution.AgentRule agentRule;

		public TestSettings.Execution.TestTypeSpecific getTestTypeSpecific() {
			return testTypeSpecific;
		}

		public void setTestTypeSpecific(TestSettings.Execution.TestTypeSpecific value) {
			this.testTypeSpecific = value;
		}

		public TestSettings.Execution.AgentRule getAgentRule() {
			return agentRule;
		}

		public void setAgentRule(TestSettings.Execution.AgentRule value) {
			this.agentRule = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "dataCollectors" })
		public static class AgentRule {
			@XmlElement(name = "DataCollectors", required = true)
			protected TestSettings.Execution.AgentRule.DataCollectors dataCollectors;
			@XmlAttribute(name = "name")
			protected String name;

			public TestSettings.Execution.AgentRule.DataCollectors getDataCollectors() {
				return dataCollectors;
			}

			public void setDataCollectors(TestSettings.Execution.AgentRule.DataCollectors value) {
				this.dataCollectors = value;
			}

			public String getName() {
				return name;
			}

			public void setName(String value) {
				this.name = value;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "dataCollector" })
			public static class DataCollectors {
				@XmlElement(name = "DataCollector", required = true)
				protected TestSettings.Execution.AgentRule.DataCollectors.DataCollector dataCollector;

				public TestSettings.Execution.AgentRule.DataCollectors.DataCollector getDataCollector() {
					return dataCollector;
				}

				public void setDataCollector(
						TestSettings.Execution.AgentRule.DataCollectors.DataCollector value) {
					this.dataCollector = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "configuration" })
				public static class DataCollector {
					@XmlElement(name = "Configuration", required = true)
					protected TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration configuration;
					@XmlAttribute(name = "assemblyQualifiedName")
					protected String assemblyQualifiedName;
					@XmlAttribute(name = "friendlyName")
					protected String friendlyName;
					@XmlAttribute(name = "uri")
					protected String uri;

					public TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration getConfiguration() {
						return configuration;
					}

					public void setConfiguration(
							TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration value) {
						this.configuration = value;
					}

					public String getAssemblyQualifiedName() {
						return assemblyQualifiedName;
					}

					public void setAssemblyQualifiedName(String value) {
						this.assemblyQualifiedName = value;
					}

					public String getFriendlyName() {
						return friendlyName;
					}

					public void setFriendlyName(String value) {
						this.friendlyName = value;
					}

					public String getUri() {
						return uri;
					}

					public void setUri(String value) {
						this.uri = value;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "codeCoverage" })
					public static class Configuration {
						@XmlElement(name = "CodeCoverage", required = true)
						protected TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage codeCoverage;

						public TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage getCodeCoverage() {
							return codeCoverage;
						}

						public void setCodeCoverage(
								TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage value) {
							this.codeCoverage = value;
						}

						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "regular" })
						public static class CodeCoverage {
							@XmlElement(name = "Regular", required = true)
							protected TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular regular;

							public TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular getRegular() {
								return regular;
							}

							public void setRegular(
									TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular value) {
								this.regular = value;
							}

							@XmlAccessorType(XmlAccessType.FIELD)
							@XmlType(name = "", propOrder = { "codeCoverageItem" })
							public static class Regular {
								@XmlElement(name = "CodeCoverageItem", required = true)
								protected TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular.CodeCoverageItem codeCoverageItem;

								public TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular.CodeCoverageItem getCodeCoverageItem() {
									return codeCoverageItem;
								}

								public void setCodeCoverageItem(
										TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular.CodeCoverageItem value) {
									this.codeCoverageItem = value;
								}

								@XmlAccessorType(XmlAccessType.FIELD)
								@XmlType(name = "")
								public static class CodeCoverageItem {
									@XmlAttribute(name = "binaryFile")
									protected String binaryFile;
									@XmlAttribute(name = "instrumentInPlace")
									protected String instrumentInPlace;
									@XmlAttribute(name = "pdbFile")
									protected String pdbFile;

									public String getBinaryFile() {
										return binaryFile;
									}

									public void setBinaryFile(String value) {
										this.binaryFile = value;
									}

									public String getInstrumentInPlace() {
										return instrumentInPlace;
									}

									public void setInstrumentInPlace(String value) {
										this.instrumentInPlace = value;
									}

									public String getPdbFile() {
										return pdbFile;
									}

									public void setPdbFile(String value) {
										this.pdbFile = value;
									}
								}
							}
						}
					}
				}
			}
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "unitTestRunConfig", "webTestRunConfiguration" })
		public static class TestTypeSpecific {
			@XmlElement(name = "UnitTestRunConfig", required = true)
			protected TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig unitTestRunConfig;
			@XmlElement(name = "WebTestRunConfiguration", required = true)
			protected TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration webTestRunConfiguration;

			public TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig getUnitTestRunConfig() {
				return unitTestRunConfig;
			}

			public void setUnitTestRunConfig(
					TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig value) {
				this.unitTestRunConfig = value;
			}

			public TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration getWebTestRunConfiguration() {
				return webTestRunConfiguration;
			}

			public void setWebTestRunConfiguration(
					TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration value) {
				this.webTestRunConfiguration = value;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "assemblyResolution" })
			public static class UnitTestRunConfig {
				@XmlElement(name = "AssemblyResolution", required = true)
				protected TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution assemblyResolution;
				@XmlAttribute(name = "testTypeId")
				protected String testTypeId;

				public TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution getAssemblyResolution() {
					return assemblyResolution;
				}

				public void setAssemblyResolution(
						TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution value) {
					this.assemblyResolution = value;
				}

				public String getTestTypeId() {
					return testTypeId;
				}

				public void setTestTypeId(String value) {
					this.testTypeId = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "testDirectory" })
				public static class AssemblyResolution {
					@XmlElement(name = "TestDirectory", required = true)
					protected TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution.TestDirectory testDirectory;

					public TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution.TestDirectory getTestDirectory() {
						return testDirectory;
					}

					public void setTestDirectory(
							TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution.TestDirectory value) {
						this.testDirectory = value;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "")
					public static class TestDirectory {
						@XmlAttribute(name = "useLoadContext")
						protected String useLoadContext;

						public String getUseLoadContext() {
							return useLoadContext;
						}

						public void setUseLoadContext(String value) {
							this.useLoadContext = value;
						}
					}
				}
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "browser" })
			public static class WebTestRunConfiguration {
				@XmlElement(name = "Browser", required = true)
				protected TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser browser;
				@XmlAttribute(name = "testTypeId")
				protected String testTypeId;

				public TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser getBrowser() {
					return browser;
				}

				public void setBrowser(
						TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser value) {
					this.browser = value;
				}

				public String getTestTypeId() {
					return testTypeId;
				}

				public void setTestTypeId(String value) {
					this.testTypeId = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "headers" })
				public static class Browser {
					@XmlElement(name = "Headers", required = true)
					protected TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers headers;
					@XmlAttribute(name = "MaxConnections")
					protected Integer maxConnections;
					@XmlAttribute(name = "name")
					protected String name;

					public TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers getHeaders() {
						return headers;
					}

					public void setHeaders(
							TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers value) {
						this.headers = value;
					}

					public Integer getMaxConnections() {
						return maxConnections;
					}

					public void setMaxConnections(Integer value) {
						this.maxConnections = value;
					}

					public String getName() {
						return name;
					}

					public void setName(String value) {
						this.name = value;
					}

					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "header" })
					public static class Headers {
						@XmlElement(name = "Header", required = true)
						protected List<TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers.Header> header;

						public List<TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers.Header> getHeader() {
							if (header == null) {
								header = new ArrayList<TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers.Header>();
							}
							return this.header;
						}

						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "")
						public static class Header {
							@XmlAttribute(name = "name")
							protected String name;
							@XmlAttribute(name = "value")
							protected String value;

							public String getName() {
								return name;
							}

							public void setName(String value) {
								this.name = value;
							}

							public String getValue() {
								return value;
							}

							public void setValue(String value) {
								this.value = value;
							}
						}
					}
				}
			}
		}
	}
}
