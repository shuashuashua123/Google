package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TimeSeriesDataAggregation {

	class Entry implements Comparable<Entry> {
		String date;
		String project;
		int count;
		public Entry(String date, String project, int count) {
			this.date = date;
			this.project = project;
			this.count = count;
		}
		@Override
		public int compareTo(Entry o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	public List<String> aggregate(String start, String end, List<String> entries) {
		List<String> res = new ArrayList<>();
		// key is date, value is <project, count>
		Map<String, Map<String, Integer>> m = new TreeMap<>(Collections.reverseOrder());
		TreeMap<String, Map<String, Integer>> n = new TreeMap<>(Collections.reverseOrder());
//		Arrays.binarySearch(a, key)
//		Collections.binarySearch(list, key)
		for (String entry : entries) {
			Entry e = this.parseEntry(entry);
			if (end.compareTo(e.date) < 0 || start.compareTo(e.date) > 0) {
				continue;
			}
			
			Map<String, Integer> val = m.computeIfAbsent(e.date, k -> new TreeMap<>());
			val.put(e.project, val.getOrDefault(e.project, 0) + e.count);
		}

		for (Map.Entry<String, Map<String, Integer>> entry : m.entrySet()) {
			StringBuilder sb = new StringBuilder();
			sb.append(entry.getKey()).append(", ");
			List<String> l = new ArrayList<>();
			for (Map.Entry<String, Integer> e : entry.getValue().entrySet()) {
				l.add(e.getKey() + ", " + e.getValue());
			}
			sb.append(String.join(", ", l));
			res.add(sb.toString());
		}
		return res;
	}
	
	private Entry parseEntry(String s) {
		String[] arr = s.split("\\,");
		assert(arr.length == 3);
		return new Entry(arr[0].trim().substring(0, 7), arr[1].trim(), Integer.parseInt(arr[2].trim()));
	}
	
	public static void main(String[] args) {
		// 2015-08, 2016-04
		List<String> entries = new ArrayList<>();
		entries.add("2015-08-15, clicks, 635");
		entries.add("2016-03-24, app_installs, 683");
		entries.add("2015-04-05, favorites, 763");
		entries.add("2016-01-22, favorites, 788");
		entries.add("2015-12-26, clicks, 525");
		entries.add("2016-06-03, retweets, 101");
		entries.add("2015-12-02, app_installs, 982");
		entries.add("2016-09-17, app_installs, 770");
		entries.add("2015-11-07, impressions, 245");
		entries.add("2016-10-16, impressions, 567");

		TimeSeriesDataAggregation t = new TimeSeriesDataAggregation();
		for (String s : t.aggregate("2015-08", "2016-04", entries)) {
			System.out.println(s);
		}
		entries.add("2015-08-17, clicks, 177");
		for (String s : t.aggregate("2015-08", "2016-04", entries)) {
			System.out.println(s);
		}
	}
}
