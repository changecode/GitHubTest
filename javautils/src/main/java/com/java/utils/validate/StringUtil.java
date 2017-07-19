package com.java.utils.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tim
 */
public class StringUtil {

	/**
	 * 将半角转换成全角
	 * 
	 * @param str
	 * @return
	 */
	public static String toFull(String str) {
		String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
		String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０", "！", "＠", "＃", "＄", "％", "︿", "＆", "＊",
				"（", "）", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ",
				"ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ",
				"Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；",
				"：", "'", "\"", "，", "〈", "。", "〉", "／", "？" };
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int position = source.indexOf(str.charAt(i));
			if (position != -1) {
				result += decode[position];
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 进行string 判断是否为空
	 * 
	 * @param object
	 * @param delval
	 * @return
	 */
	public static boolean strIsNull(Object object) {
		return object == null ? true : false;
	}

	/**
	 * 反转字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String reverseStr(String str) {
		if (str == null)
			return null;
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * 截取字符串 支持中文
	 * 
	 * @param sourceStr
	 * @param length
	 * @return
	 */
	public static String subStr(String sourceStr, int length) {
		String resultStr = "";
		if (sourceStr == null || "".equalsIgnoreCase(sourceStr) || sourceStr.length() < length) {
			return resultStr;
		}
		char[] chars = resultStr.toCharArray();
		int strNum = 0;
		int strGBKnum = 0;
		for (int i = 0; i < resultStr.length(); i++) {
			if (chars[i] >= 0xa1) {
				strNum = strNum + 2;
				strGBKnum++;
			} else {
				strNum++;
			}
		}
		resultStr = sourceStr.substring(0, strNum - strGBKnum);
		return resultStr;
	}

	/**
	 * 过滤html
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String escapeHtml(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static String removeCrlf(String str) {
		if (str == null)
			return null;
		return StringUtil.join(StringUtil.tokenizeToStringArray(str, "\t\n\r\f"), " ");
	}

	private static final Map<String, String> XML = new HashMap<String, String>();

	static {
		XML.put("apos", "'");
		XML.put("quot", "\"");
		XML.put("amp", "&");
		XML.put("lt", "<");
		XML.put("gt", ">");
	}

	public static String unescapeXml(String str) {
		if (str == null)
			return null;
		for (String key : XML.keySet()) {
			String value = XML.get(key);
			str = StringUtil.replace(str, "&" + key + ";", value);
		}
		return str;
	}

	public static String escapeXml(String str) {
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			String escapedStr = getEscapedStringByChar(c);
			if (escapedStr == null)
				sb.append(c);
			else
				sb.append(escapedStr);
		}
		return sb.toString();
	}

	public static String escapeXml(String str, String escapeChars) {
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (escapeChars.indexOf(c) < 0) {
				sb.append(c);
				continue;
			}
			String escapedStr = getEscapedStringByChar(c);
			if (escapedStr == null)
				sb.append(c);
			else
				sb.append(escapedStr);
		}
		return sb.toString();
	}

	private static String getEscapedStringByChar(char c) {
		String escapedStr = null;
		for (String key : XML.keySet()) {
			String value = XML.get(key);
			if (c == value.charAt(0)) {
				escapedStr = "&" + key + ";";
			}
		}
		return escapedStr;
	}

	public static String removePrefix(String str, String prefix) {
		return removePrefix(str, prefix, false);
	}

	public static String removePrefix(String str, String prefix, boolean ignoreCase) {
		if (str == null)
			return null;
		if (prefix == null)
			return str;
		if (ignoreCase) {
			if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
				return str.substring(prefix.length());
			}
		} else {
			if (str.startsWith(prefix)) {
				return str.substring(prefix.length());
			}
		}
		return str;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String getExtension(String str) {
		if (str == null)
			return null;
		int i = str.lastIndexOf('.');
		if (i >= 0) {
			return str.substring(i + 1);
		}
		return null;
	}

	public static String insertBefore(String content, String compareToken, String insertString) {
		if (content.indexOf(insertString) >= 0)
			return content;
		int index = content.indexOf(compareToken);
		if (index >= 0) {
			return new StringBuffer(content).insert(index, insertString).toString();
		} else {
			throw new IllegalArgumentException(
					"not found insert location by compareToken:" + compareToken + " content:" + content);
		}
	}

	public static String insertAfter(String content, String compareToken, String insertString) {
		if (content.indexOf(insertString) >= 0)
			return content;
		int index = content.indexOf(compareToken);
		if (index >= 0) {
			return new StringBuffer(content).insert(index + compareToken.length(), insertString).toString();
		} else {
			throw new IllegalArgumentException(
					"not found insert location by compareToken:" + compareToken + " content:" + content);
		}
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	public static boolean contains(String str, String... keywords) {
		if (str == null)
			return false;
		if (keywords == null)
			throw new IllegalArgumentException("'keywords' must be not null");

		for (String keyword : keywords) {
			if (str.contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static String defaultString(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public static String defaultIfEmpty(Object value, String defaultValue) {
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value.toString();
	}

	public static String makeAllWordFirstLetterUpperCase(String sqlName) {
		String[] strs = sqlName.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		if (strs.length == 0) {
			return capitalize(sqlName);
		}
		for (int i = 0; i < strs.length; i++) {
			if (preStr.length() == 1) {
				result += strs[i];
			} else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		return result;
	}

	public static int indexOfByRegex(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.start();
		}
		return -1;
	}

	public static String toJavaVariableName(String str) {
		return uncapitalize(toJavaClassName(str));
	}

	public static String toJavaClassName(String str) {
		return makeAllWordFirstLetterUpperCase(StringUtil.toUnderscoreName(str));
	}

	public static String getJavaClassSimpleName(String clazz) {
		if (clazz == null)
			return null;
		if (clazz.lastIndexOf(".") >= 0) {
			return clazz.substring(clazz.lastIndexOf(".") + 1);
		}
		return clazz;
	}

	public static String removeMany(String inString, String... keywords) {
		if (inString == null) {
			return null;
		}
		for (String k : keywords) {
			inString = replace(inString, k, "");
		}
		return inString;
	}

	public static void appendReplacement(Matcher m, StringBuffer sb, String replacement) {
		replacement = StringUtil.replace(replacement, "$", "\\$");
		m.appendReplacement(sb, replacement);
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/** ����ĸ��copy from spring */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/** ����ĸСдcopy from spring */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	/** copy from spring */
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	private static final Random RANDOM = new Random();

	public static String randomNumeric(int count) {
		return random(count, false, true);
	}

	public static String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars,
			Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * Convert a name in camelCase to an underscored name in lower case. Any
	 * upper case letters are converted to lower case with a preceding
	 * underscore.
	 * 
	 * @param filteredName
	 *            the string containing original name
	 * @return the converted name
	 */
	public static String toUnderscoreName(String name) {
		if (name == null)
			return null;

		String filteredName = name;
		if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}
		if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}

		StringBuffer result = new StringBuffer();
		if (filteredName != null && filteredName.length() > 0) {
			result.append(filteredName.substring(0, 1).toLowerCase());
			for (int i = 1; i < filteredName.length(); i++) {
				String preChart = filteredName.substring(i - 1, i);
				String c = filteredName.substring(i, i + 1);
				if (c.equals("_")) {
					result.append("_");
					continue;
				}
				if (preChart.equals("_")) {
					result.append(c.toLowerCase());
					continue;
				}
				if (c.matches("\\d")) {
					result.append(c);
				} else if (c.equals(c.toUpperCase())) {
					result.append("_");
					result.append(c.toLowerCase());
				} else {
					result.append(c);
				}
			}
		}
		return result.toString();
	}

	public static String removeEndWiths(String inputString, String... endWiths) {
		for (String endWith : endWiths) {
			if (inputString.endsWith(endWith)) {
				return inputString.substring(0, inputString.length() - endWith.length());
			}
		}
		return inputString;
	}


	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	public static String[] tokenizeToStringArray(String str, String seperators) {
		if (str == null)
			return new String[0];
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List<Object> result = new ArrayList<Object>();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public static String join(List<Object> list, String seperator) {
		return join(list.toArray(new Object[0]), seperator);
	}

	public static String replace(int start, int end, String str, String replacement) {
		String before = str.substring(0, start);
		String after = str.substring(end);
		return before + replacement + after;
	}

	public static String join(Object[] array, String seperator) {
		if (array == null)
			return null;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			result.append(array[i]);
			if (i != array.length - 1) {
				result.append(seperator);
			}
		}
		return result.toString();
	}

	public static int containsCount(String string, String keyword) {
		if (string == null)
			return 0;
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			int indexOf = string.indexOf(keyword, i);
			if (indexOf < 0) {
				break;
			}
			count++;
			i = indexOf;
		}
		return count;
	}

	public static String getByRegex(String str, String regex) {
		return getByRegex(str, regex, 0);
	}

	public static String getByRegex(String str, String regex, int group) {
		if (regex == null)
			throw new NullPointerException();
		if (group < 0)
			throw new IllegalArgumentException();
		if (str == null)
			return null;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(group);
		}
		return null;
	}

	public static String removeIbatisOrderBy(String sql) {
		// Pattern p =
		// Pattern.compile("<is\\w+\\s+[\\w\\s='\"]+>\\s*order\\s+by.*?</\\w+>");
		// <is\w+\s+[\w\s='"]+>\s*order\s+by.*?</\w+>
		String orderByRemovedSql = sql.replaceAll("(?si)<\\w+[^>]*?>\\s*order\\s+by\\s+[^<]+?</\\w+>", "")
				.replaceAll("(?i)<\\w+[\\w\\s='\"]+prepend[\\w\\s='\"]*?order\\s+by\\s*['\"][^>]*?>[^<]+</\\w+>", "")
				.replaceAll("(?i)\\s*order\\s+by\\s+.*", "");
		return removeXmlTagIfBodyEmpty(
				removeXmlTagIfBodyEmpty(removeXmlTagIfBodyEmpty(removeXmlTagIfBodyEmpty(orderByRemovedSql))));
	}

	public static String removeXmlTagIfBodyEmpty(String sql) {
		return sql.replaceAll("<\\w+[^>]*?>\\s+</\\w+>", "");
	}

}
