package com.framework.distribute.session.util;

public final class MACAddressParser {
	/**
	 * No instances needed.
	 */
	private MACAddressParser() {
		super();
	}
	
	/**
	 * Attempts to find a pattern in the given String.
	 * 
	 * @param in the String, may not be <code>null</code>
	 * @return the substring that matches this pattern or <code>null</code>
	 */
	static String parse(String in) {
		
		String out = in;
		
		// lanscan
		
		int hexStart = out.indexOf("0x");
		if (hexStart != -1 && out.indexOf("ETHER") != -1) {
			int hexEnd = out.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				out = out.substring(hexStart, hexEnd);
			}
		}
		
		else {
			
			int octets = 0;
			int lastIndex, old, end;
			
			if (out.indexOf(':') > -1) {
				out = out.replace(':', '-');
			}
			
			lastIndex = out.lastIndexOf('-');
			
			if (lastIndex > out.length() - 2) {
				out = null;
			} else {
				
				end = Math.min(out.length(), lastIndex + 3);
				
				++octets;
				old = lastIndex;
				while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
					lastIndex = out.lastIndexOf('-', --lastIndex);
					if (old - lastIndex == 3 || old - lastIndex == 2) {
						++octets;
						old = lastIndex;
					}
				}
				
				if (octets == 5 && lastIndex > 1) {
					out = out.substring(lastIndex - 2, end).trim();
				} else {
					out = null;
				}
				
			}
			
		}
		
		if (out != null && out.startsWith("0x")) {
			out = out.substring(2);
		}
		
		return out;
	}
}
