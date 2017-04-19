package com.softtek.mdm.helper.breadcrumb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Link {

	/** label. */
	String label();

	/** family. */
	String family();

	/** parent link id. */
	String parent() default "";
	
	String belong() default "";

}
