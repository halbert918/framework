package com.rest.common;

import java.io.Reader;

/**
 * Created by admin on 2017/9/22.
 */
public interface IResourceLoader {

	/**
	 *
	 * @return
     */
	Reader loadDefaultResource();

	/**
	 *
	 * @param relativePath
     * @return
     */
	Reader loadResource(String relativePath);

	/**
	 *
	 * @return
     */
	String getName();

	/**
	 * Created by admin on 2017/9/22.
	 */
	class ResourceIsDirectoryException extends RuntimeException {

		public ResourceIsDirectoryException(String message) {
			super(message);
		}

	}

}
