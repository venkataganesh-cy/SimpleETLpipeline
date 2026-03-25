package com.etldemo.titanic_pipeline.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.etldemo.titanic_pipeline.model.PassengerRecord;

@Service
public class CsvReaderService {

	public List<PassengerRecord> readCsv() throws Exception{
		List<PassengerRecord> records = new ArrayList<>();
		
		BufferedReader  reader = new BufferedReader(
				new InputStreamReader(
						new ClassPathResource("Titanic-Dataset.csv").getInputStream()
						)
				);
		
		String line;
		boolean firstLine = true;
		
		while((line = reader.readLine()) != null) {
			if(firstLine) {
				firstLine = false;
				continue;
			}
			
			String[] cols = line.split(",",-1);
			if(cols.length < 12) continue;
			
			PassengerRecord r = new PassengerRecord();
			r.setPassengerId(Integer.parseInt(cols[0].trim()));
			r.setSurvived(cols[1].trim());
            r.setPClass(cols[2].trim());
            r.setName(cols[3].trim() + " " + cols[4].trim()); // name has a comma inside!
            r.setSex(cols[5].trim());
            r.setAge(cols[6].trim());
            r.setSibSp(cols[7].trim());
            r.setParch(cols[8].trim());
            r.setTicket(cols[9].trim());
            r.setFare(cols[10].trim());
            r.setCabin(cols[11].trim());
            r.setEmbarked(cols.length > 12 ? cols[12].trim() : "");
            
            records.add(r);
		}
		
//		System.out.println(records);
		reader.close();
		return records;
	}
}
 