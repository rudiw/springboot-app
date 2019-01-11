package com.mitrais.study.bootcamp.config.rs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.zalando.jackson.datatype.money.MoneyModule;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

public class JsonUtils {
	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectWriter writer;

	public static final ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	public static final List<Supplier<Module>> modules = (List) ImmutableList.copyOf(new Supplier[] {
		Suppliers.ofInstance(new JodaModule()),
		Suppliers.ofInstance(new GuavaModule()),
		Suppliers.ofInstance(new MoneyModule()),
		Suppliers.ofInstance(new JscienceModule())
	});

	static {
		final Jackson2ObjectMapperFactoryBean jacksonMapperFactory = new JacksonMapperFactoryImpl(
				modules);
		mapper = jacksonMapperFactory.get();
		writer = mapper.writer();
	}
	
	public static String asJson(Object obj) {
		try {
			return writer.writeValueAsString(obj);
		} catch (Exception e) {
			log.error("Cannot serialize " + obj.getClass().getName() + " as JSON", e);
			return null;
		}
	}

}
