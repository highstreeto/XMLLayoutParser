/*
Copyright 2013 Martin Hochstrasser

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package at.highstreeto.xnllayoutparser;

/**
 * 
 * @author highstreeto
 */
public class LayoutParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3591382682086628704L;

	public LayoutParseException() {
		super();
	}

	public LayoutParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public LayoutParseException(String message) {
		super(message);
	}

	public LayoutParseException(Throwable cause) {
		super(cause);
	}
}
