package com.rdg.topscore.config;

import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.DoubleType;

public class HibernateDialect extends Oracle12cDialect {

	public HibernateDialect() {
		registerFunction("rand", new SQLFunctionTemplate(DoubleType.INSTANCE, "dbms_random.value"));
	}
}