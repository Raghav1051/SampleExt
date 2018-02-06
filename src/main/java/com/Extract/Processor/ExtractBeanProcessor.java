package com.Extract.Processor;

import org.springframework.batch.item.ItemProcessor;

import com.Extract.util.ExtractPojo;

public class ExtractBeanProcessor implements ItemProcessor<ExtractPojo, ExtractPojo> {

	public ExtractPojo process(ExtractPojo item) throws Exception {
		String outFileName = item.getClientID() +"_"+ item.getBatchSeq()+"_"+ item.getSystdate();
		item.setOutFileName(outFileName);
		return item; 
	}

	
}
