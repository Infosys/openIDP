/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.infosys.json.Siebel;

public class ConvertSiebelPackageContent {
	private ConvertSiebelPackageContent() {
	}

	public static void convert(String path, Siebel siebel) {
		try(BufferedReader in = new BufferedReader(new FileReader(path))) {
			String line;
			List<String> nonRepoList = new ArrayList<>();
			List<String> repoList = new ArrayList<>();
			if (path.toLowerCase().contains("_repo_")) {
				while ((line = in.readLine()) != null) {
					repoList.add(line);
				}
				siebel.setRepoList(repoList);
			} else {
				while ((line = in.readLine()) != null) {
					nonRepoList.add(line);
				}
				siebel.setNonRepoList(nonRepoList);
			}
		} catch (Exception e) {
			System.out.println("Conversion error for " + path + "Error: " + e);
		}
	}
}
