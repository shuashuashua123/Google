package twitter;

public class HackingTime {

	public String decryptMsg(String encrypted, String key) {
		int keyIndex = 0, n = key.length();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < encrypted.length(); i++) {
			char c = encrypted.charAt(i);
			if (!Character.isAlphabetic(c)) {
				res.append(c);
				continue;
			}

			res.append(this.reverseRotateChar(c, key.charAt(keyIndex++) - '0'));
			if (keyIndex == n) {
				keyIndex = 0;
			}
		}

		return res.toString();
	}

	public String findKey(String encrypt, String decrypt) {
		StringBuilder sb = new StringBuilder();
		assert (encrypt.length() == decrypt.length());
		for (int i = 0; i < decrypt.length(); i++) {
			char d = decrypt.charAt(i);
			if (!Character.isAlphabetic(d)) {
				continue;
			}
			char e = encrypt.charAt(i);
			int rotation = e - d;
			if (rotation < 0) {
				rotation += 26;
			}
			sb.append((char) (rotation + '0'));
		}
		return sb.toString();
	}

	private char rotateChar(char c, int rotation) {
		if (!Character.isAlphabetic(c)) {
			return c;
		}
		int beg = Character.isUpperCase(c) ? 65 : 97;
		char begChar = (char) beg;
		return (char) (((c - begChar) + rotation) % 26 + beg);
	}

	private char reverseRotateChar(char c, int rotation) {
		if (!Character.isAlphabetic(c)) {
			return c;
		}
		int beg = Character.isUpperCase(c) ? 65 : 97;
		char begChar = (char) beg;
		int r = (c - begChar) - rotation;
		if (r < 0) {
			r += 26;
		}
		return (char) (r + beg);
	}

	private String findRepeatedString(String s) {
		for (int i = 1; i < s.length(); i++) {
			String repeat = s.substring(0, i);
			if (isRepeatedString(repeat, s)) {
				return repeat;
			}
		}

		return s;
	}

	private boolean isRepeatedString(String repeat, String s) {
		StringBuilder sb = new StringBuilder();
		while (sb.length() < s.length()) {
			sb.append(repeat);
		}

		return sb.toString().startsWith(s);
	}

	public static void main(String[] args) {
		HackingTime h = new HackingTime();
		System.out.println(h.rotateChar('+', 11));
		System.out.println(h.reverseRotateChar('+', 11));
		System.out.println(h.rotateChar('H', 4));
		System.out.println(h.reverseRotateChar('L', 4));
		System.out.println(h.rotateChar('t', 7));
		System.out.println(h.reverseRotateChar('a', 7));
		System.out.println(h.decryptMsg("Li, ailu jw au facntll", "4071321"));
		System.out.println(h.findRepeatedString("40540540"));
		System.out.println(h.findRepeatedString("4054054"));
		System.out.println(h.findRepeatedString("405405"));
		System.out.println(h.findRepeatedString("4054"));
		System.out.println(h.findRepeatedString("405"));
		System.out.println(h.findRepeatedString("4"));
		String key = h.findKey("-Atvt hrqgse, Cnikg", "-Your friend, Alice");
		System.out.println(key);
		String repeat = h.findRepeatedString(key);
		System.out.println(repeat);
		System.out.println(h.decryptMsg("-Atvt hrqgse, Cnikg", key));
		String encrypt = "Otjfvknou kskgnl, K mbxg iurtsvcnb ksgq hoz atv. Vje xcxtyqrl vt ujg smewfv vrmcxvtg rwqr ju vhm ytsf elwepuqyez. -Atvt hrqgse, Cnikg";
		int count = 0;
		for (int i = 0; i < encrypt.length(); i++) {
			if (Character.isAlphabetic(encrypt.charAt(i))) {
				count++;
			}
		}
		System.out.println(count + " " + key.length());
		// repeat.length() * x + key.length() >= count => x >= (count - key.length()) / repeat.length()
		int x = (count - key.length()) / repeat.length() + 1;
		x = (x * repeat.length() + key.length() - count) % repeat.length();
		System.out.println(x);
		if (x > 0) {
			repeat = repeat.substring(x) + repeat.substring(0, x);
		}
		System.out.println(repeat);
//		StringBuilder keySB = new StringBuilder(key);
//		while (keySB.length() < count) {
//			keySB.insert(0, repeat);
//		}
//		key = keySB.substring(keySB.length() - count).toString();
//		System.out.println(key);
//		System.out.println(h.decryptMsg(encrypt, key));
//		key = h.findRepeatedString(key);
//		System.out.println(key);
		System.out.println(h.decryptMsg(encrypt, repeat));
	}

}
