package com.sds.inventory.resources;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QualityControllerTest {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void hookup() {
		assertNotNull(objectMapper);
		assertNotNull(mvc);
	}

    @Test
    public void calculateValueForSulfurasForAnyGivenDay() throws Exception {
    	List<String> strings = Stream.of("Sulfuras").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/3")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("50"));
        
        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/30")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("50"));
    }
    
    
    @Test
    public void calculateValueForRegularItemAfter3Days() throws Exception {
    	List<String> strings = Stream.of("+5 Dexterity Vest").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/3")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("17"));
    }
    
    @Test
    public void calculateValueForRegularItemAfterExcedingSellInDays() throws Exception {
    	List<String> strings = Stream.of("+5 Dexterity Vest").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/12")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("6"));
        
        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/17")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));
    }
    
    @Test
    public void calculateValueForAgedBrie() throws Exception {
    	List<String> strings = Stream.of("Aged Brie").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/1")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("1"));
    }
    
    @Test
    public void calculateValueForAgedBrieExcedingSellDays() throws Exception {
    	List<String> strings = Stream.of("Aged Brie").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/5")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));
        
        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/3")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));
    }
    
    @Test
    public void calculateValueForConcertBackstagePasses() throws Exception {
    	
    	List<String> strings = Stream.of("Concert backstage passes").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/2")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("22"));
        
    }
    
    @Test
    public void calculateValueForConcertBackstagePassesAfterSellInDays() throws Exception {
    	
    	List<String> strings = Stream.of("Concert backstage passes").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/16")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));
        
    }
    
    @Test
    public void calculateValueForConcertBackstagePassesWithSellinDaysWithin10Days() throws Exception {
    	
    	List<String> strings = Stream.of("Concert backstage passes").collect(Collectors.toList());

        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/8")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("31"));
        
        mvc.perform(MockMvcRequestBuilders.post("/calculate/days/15")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("45"));
        
    }
    
    @Test
    public void calculateValueForItems() throws Exception { 
    	List<String> strings = Stream.of("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras" , "Concert backstage passes").collect(Collectors.toList());
    	mvc.perform(MockMvcRequestBuilders.post("/calculate/days/2")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("97"));
    	
    	mvc.perform(MockMvcRequestBuilders.post("/calculate/days/30")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("50"));
    	
    	mvc.perform(MockMvcRequestBuilders.post("/calculate/days/8")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(strings)))
                .andExpect(status().isOk())
                .andExpect(content().json("93"));
    }
    

}
