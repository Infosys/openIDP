/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
export const toolsList = {
  CI: {
    SCM: ["SVN", "GIT", "TFS"],
    CODE_ANALYSIS: ["PMD", "FINDBUGS", "CHECKSTYLE", "SONAR"],
    UNIT_TESTING: ["JUNIT", "MSTEST"],
    CODE_COVERAGE: ["COBERTURA", "EMMA"],
    BUILD: ["ANT", "MAVEN", "MSBUILD"]
  },
  CD: { APPLICATION_SERVER: ["TOMCAT", "IIS", "WEBSPHERE"] },
  CT: {
    PERFORMANCE_TEST: ["JMETER"],
    FUNCTIONAL_TEST: ["SELENIUM", "SAHI", "MTM"],
    SECURITY_TEST: ["APPSCAN"]
  }
};
