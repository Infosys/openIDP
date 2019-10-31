/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.JsonClass;

public class ConvertOracleAnalysis {
	private ConvertOracleAnalysis() {
	};

	public static JsonClass convert(String inputPath, JsonClass jsonClass) {
		try(FileInputStream file = new FileInputStream(new File(inputPath))) {
			List<CodeAnalysis> ca = new ArrayList<>();
			
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet("Report");
			Iterator<Row> rowIterator = sheet.iterator();
			CodeAnalysis c;
			Row row;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// 3 is LOC and threshold=1000
					// 4 is Parameters ,t=5
					// asd
					if (cell.getStringCellValue().equals(" ")) {
						continue;
					}
					if (cell.getColumnIndex() == 3 && (Integer.parseInt(cell.getStringCellValue()) >= 1000)) {
						c = new CodeAnalysis();
						c.setId(String.valueOf(cell.getColumnIndex()));
						c.setCategory("OracleAnalysis");
						c.setMessage("LOC Threshold exceeding");
						c.setSeverity("high");
						c.setruleName("LOC for a single module too high");
						ca.add(c);
					}
					if (cell.getColumnIndex() == 4 && (Integer.parseInt(cell.getStringCellValue()) >= 5)) {
						c = new CodeAnalysis();
						c.setId(String.valueOf(cell.getColumnIndex()));
						c.setCategory("OracleAnalysis");
						c.setMessage("Parameters threshold exceeding");
						c.setSeverity("high");
						c.setruleName("No of Parameters passed too high");
						ca.add(c);
					}
					if (cell.getColumnIndex() == 5 && (Integer.parseInt(cell.getStringCellValue()) >= 20)) {
						c = new CodeAnalysis();
						c.setId(String.valueOf(cell.getColumnIndex()));
						c.setCategory("OracleAnalysis");
						c.setMessage("Cyclomatic Complexity exceeding");
						c.setSeverity("high");
						c.setruleName("Cyclomatic complexity too high");
						ca.add(c);
					}
					if (cell.getColumnIndex() == 6 && (Integer.parseInt(cell.getStringCellValue()) >= 1)) {
						c = new CodeAnalysis();
						c.setId(String.valueOf(cell.getColumnIndex()));
						c.setCategory("OracleAnalysis");
						c.setMessage("Exception not handelled");
						c.setSeverity("high");
						c.setruleName("Number of queries not exception handelled too high");
						ca.add(c);
					}
					if (cell.getColumnIndex() == 7
							&& (Integer.parseInt(cell.getStringCellValue().replace("%", "")) <= 5)) {
						c = new CodeAnalysis();
						c.setId(String.valueOf(cell.getColumnIndex()));
						c.setCategory("OracleAnalysis");
						c.setMessage("No Comments added");
						c.setSeverity("high");
						c.setruleName("Comment Ratio too Low");
						ca.add(c);
					}
				}
			}
			System.out.println("Oracle code analysis parsed successfully");
			jsonClass.setCodeAnalysis(ca);
			return jsonClass;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in converting Oracle analysis report");
			return jsonClass;
		}
	}
}
