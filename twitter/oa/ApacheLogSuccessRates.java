package twitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Log implements Comparable<Log> {
	String date;

	String url;

	public Log(String date, String url) {
		this.date = date;
		this.url = url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.url);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		Log other = (Log) o;
		return Objects.equals(this.date, other.date) && Objects.equals(this.url, other.url);
	}

	@Override
	public int compareTo(Log o) {
		if (!Objects.equals(this.date, o.date)) {
			return this.date.compareTo(o.date);
		}
		return this.url.compareTo(o.url);
	}
}

class Count {
	int total;
	int success;
}

public class ApacheLogSuccessRates {
	private static final Pattern logPattern = Pattern
			.compile("^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) .+");
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	private static final SimpleDateFormat formatteroutput = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm", Locale.US);

	public static void main(String args[]) throws Exception {
		Log log1 = new Log("d1", "list");
		Log log2 = new Log("d1", "create");
		System.out.println(log1.equals(log2));
//		/*
//		 * Enter your code here. Read input from STDIN. Print output to STDOUT
//		 */
//
//		Scanner sc = new Scanner(System.in);
		Map<Log, Count> logs = new TreeMap<>();
		logs.put(log1, new Count());
		logs.put(log2, new Count());
		System.out.println(logs.size());
//		while (sc.hasNextLine()) {
//			String line = sc.nextLine();
//			Matcher m = logPattern.matcher(line);
//			if (!m.matches()) {
//				continue;
//			}
//
//			String date;
//			try {
//				date = getDate(m.group(4));
//			} catch (Exception ex) {
//				continue;
//			}
//
//			Count count = logs.computeIfAbsent(new Log(date, getUrl(m.group(5))), k -> new Count());
//			int statusCode;
//			try {
//				statusCode = Integer.parseInt(m.group(6));
//			} catch (NumberFormatException ex) {
//				continue;
//			}
//
//			count.total++;
//			if (statusCode < 500 || statusCode >= 600) {
//				count.success++;
//			}
//		}
//		sc.close();
//
//		for (Map.Entry<Log, Count> entry : logs.entrySet()) {
//			Log log = entry.getKey();
//			Count c = entry.getValue();
//			System.out.println(log.date + " " + log.url + c.success / c.total);
//		}

	}

	private static String getDate(String s) throws ParseException {
		String input = s.split(" ")[0];
		Date date = formatter.parse(input);
		return String.format("%04d-%02d-%02dT%02d:%02d", date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());
	}

	private static String getUrl(String s) {
		String input = s.split(" ")[1];
		int index = input.indexOf('?');
		return index == -1 ? input : input.substring(0, index);
	}

}