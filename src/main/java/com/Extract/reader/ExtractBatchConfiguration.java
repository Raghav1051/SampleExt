package com.Extract.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.Extract.Processor.ExtractBeanProcessor;
import com.Extract.rowMappers.ClaimsRowMapper;
import com.Extract.util.ExtractPojo;

@Configuration
@PropertySource("classpath:application.properties")
public class ExtractBatchConfiguration {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
   /* @Autowired
    private AppProps appProps;*/
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    
    @Value("${spring.datasource.username}")
    private String userName;
    
    @Value("${spring.datasource.password}")
    private String password;

//    @Autowired
//    public DataSource dataSource;
    
    /*@Autowired
    public void setAppProps(AppProps appProps) {
    	
    	this.appProps = appProps;
    }*/
    

    @Bean
    public JdbcCursorItemReader<ExtractPojo> reader(DataSource dataSource) {
    	System.out.println("reading Item");
    	JdbcCursorItemReader<ExtractPojo> reader = new JdbcCursorItemReader<ExtractPojo>();
    	reader.setSql("select clientID, batchSeq, createDate from ubbatch");
        reader.setDataSource(dataSource);
        reader.setRowMapper(new ClaimsRowMapper());
        return reader;
    }

    @Bean
    public ExtractBeanProcessor processor() {
    	System.out.println("processing Item");
        return new ExtractBeanProcessor();
    }

    @Bean
    public ItemWriter<ExtractPojo> writer() {
    	System.out.println("writing Item");
    	FlatFileItemWriter<ExtractPojo> writer = new FlatFileItemWriter<ExtractPojo>();
    	writer.setResource(new ClassPathResource("extract.csv"));
    	DelimitedLineAggregator<ExtractPojo> delLineAgg = new DelimitedLineAggregator<ExtractPojo>();
    	delLineAgg.setDelimiter(",");
    	BeanWrapperFieldExtractor<ExtractPojo> fieldExtractor = new BeanWrapperFieldExtractor<ExtractPojo>();
    	fieldExtractor.setNames(new String[] {"clientID", "batchSeq", "Systdate"});
    	delLineAgg.setFieldExtractor(fieldExtractor);
    	writer.setLineAggregator(delLineAgg);
        return writer;
    }
    
    @Bean
    public DataSource dataSource(){
    	 DriverManagerDataSource dataSource = new DriverManagerDataSource();
         dataSource.setDriverClassName(driverClassName);
         dataSource.setUrl(url);
         dataSource.setUsername(userName);
         dataSource.setPassword(password);
         return dataSource;     
     } 

    @Bean
    public Job claimExtractJob() {
        return jobBuilderFactory.get("claimExtractJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ExtractPojo, ExtractPojo> chunk(10)
                .reader(reader(dataSource()))
                .processor(processor())
                .writer(writer())
                .build();
    }
}
