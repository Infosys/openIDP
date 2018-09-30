/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class SchedulerServiceApplication {
	

	public static void main(String[] args) {
		/*String argg[]={"dummyv07:8085/jenkins","dummy","password","Test_App_TFSTestFinal1_Copy","2"};
		String jenkinsServer=argg[0];
		String username=argg[1];
		String password=argg[2];
		String jobName=argg[3];
		String buildId=argg[4];
		GetJobParameter.getJobParameter(jenkinsServer, username, password, jobName, buildId);*/
		SpringApplication.run(SchedulerServiceApplication.class, args);
	}
}
