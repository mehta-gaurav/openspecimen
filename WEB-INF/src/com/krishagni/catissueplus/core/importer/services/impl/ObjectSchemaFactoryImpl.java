package com.krishagni.catissueplus.core.importer.services.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.krishagni.catissueplus.core.common.service.TemplateService;
import com.krishagni.catissueplus.core.importer.domain.ObjectSchema;
import com.krishagni.catissueplus.core.importer.domain.ObjectSchema.Record;
import com.krishagni.catissueplus.core.importer.services.ObjectSchemaBuilder;
import com.krishagni.catissueplus.core.importer.services.ObjectSchemaFactory;


public class ObjectSchemaFactoryImpl implements ObjectSchemaFactory {
	private TemplateService templateService;
	
	private Map<String, ObjectSchema> schemaMap = new HashMap<String, ObjectSchema>();
	
	private Map<String, ObjectSchemaBuilder> schemaBuilders = new HashMap<String, ObjectSchemaBuilder>();
	
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setSchemaMap(Map<String, ObjectSchema> schemaMap) {
		this.schemaMap = schemaMap;
	}
	
	public void setSchemaBuilders(Map<String, ObjectSchemaBuilder> schemaBuilders) {
		this.schemaBuilders = schemaBuilders;
	}
	
	public void setSchemaResources(List<String> schemaResources) {
		for (String schemaResource : schemaResources) {
			ObjectSchema schema = parseSchema(schemaResource);
			schemaMap.put(schema.getName(), schema);
		}
	}
	
	@Override
	public ObjectSchema getSchema(String name) {
		return getSchema(name, Collections.<String, Object>emptyMap());
	}
	
	@Override
	public ObjectSchema getSchema(String name, Map<String, Object> params) {
		ObjectSchema schema = schemaMap.get(name);
		if (schema != null) {
			return schema;
		}
		
		ObjectSchemaBuilder builder = schemaBuilders.get(name);
		if (builder != null) {
			schema = builder.getObjectSchema(params);
		}
		
		return schema;
	}

	@Override
	public void registerSchema(String name, ObjectSchema schema) {
		schemaMap.put(name, schema);
	}
	
	private ObjectSchema parseSchema(String schemaResource) {
		InputStream in = null;
		try {
			in = preprocessSchema(schemaResource);
			ObjectSchema schema = ObjectSchema.parseSchema(in);
			Record extnRecord = schema.getExtensionRecord();
			if (extnRecord != null) {
				populateExtensionRecord(extnRecord);
			}
			
			return schema;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	private void populateExtensionRecord(Record extnRecord) {
		String entityType = extnRecord.getEntityType();
		if (entityType == null) {
			return;
		}

		ObjectSchema extension = null;
		ObjectSchemaBuilder builder = schemaBuilders.get(extnRecord.getType());
		if (builder != null) {
			extension = builder.getObjectSchema(entityType);
		}
		
		if (extension != null) {
			Record record = extension.getRecord();
			extnRecord.setCaption(record.getCaption());
			extnRecord.setAttribute(record.getAttribute());
			extnRecord.setSubRecords(record.getSubRecords());
		}
	}
	
	private InputStream preprocessSchema(String schemaResource) {
		String template = templateService.render(schemaResource, new HashMap<String, Object>());
		return new ByteArrayInputStream( template.getBytes() );
	}
}
