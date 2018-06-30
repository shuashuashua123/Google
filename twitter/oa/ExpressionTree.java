class ExpTreeNode {
	List<ExpTreeNode> children = new ArrayList<>();
	char c;

	public ExpTreeNode() {
	}

	public ExpTreeNode(char c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "ExpTreeNode [children=" + children.size() + ", c=" + c + "]";
	}
}

public class ExpressionTree {

	private ExpTreeNode root;

	private boolean simplified;

	public ExpressionTree(String s) {
		this.root = this.getExpTreeNode(s);
	}

	@Override
	public String toString() {
		return this.toString(root);
	}

	private ExpTreeNode getExpTreeNode(String s) {
		if (s.isEmpty()) {
			return null;
		}
		Stack<ExpTreeNode> nodes = new Stack<>();
		ExpTreeNode cur = new ExpTreeNode();
		ExpTreeNode root = cur;
		List<ExpTreeNode> letters = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isWhitespace(c)) {
				continue;
			}
			switch (c) {
			case '(':
				addLettersToChildren(cur, letters);
				nodes.push(cur);
				cur = new ExpTreeNode();
				nodes.peek().children.add(cur);
				break;
			case ')':
				addLettersToChildren(cur, letters);
				cur = nodes.pop();
				break;
			default:
				// letters
				letters.add(new ExpTreeNode(c));
			}
		}

		addLettersToChildren(cur, letters);
		return root;
	}

	private void addLettersToChildren(ExpTreeNode cur, List<ExpTreeNode> letters) {
		if (letters.isEmpty()) {
			return;
		}
		cur.children.addAll(letters);
		letters.clear();
	}

	public ExpTreeNode reverse(ExpTreeNode root) {
		if (root == null || root.children.isEmpty()) {
			return root;
		}

		List<ExpTreeNode> children = root.children;
		for (int i = 0, j = children.size() - 1; i < j; i++, j--) {
			ExpTreeNode tmp = children.get(i);
			children.set(i, children.get(j));
			children.set(j, tmp);
		}
		for (ExpTreeNode child : children) {
			reverse(child);
		}
		return root;
	}

	public String toString(ExpTreeNode root) {
		if (root == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		toString(root, sb, -1);
		return sb.substring(1, sb.length() - 1).toString();
	}

	private void toString(ExpTreeNode cur, StringBuilder sb, int index) {
		if (cur.children.isEmpty()) {
			sb.append(cur.c);
			return;
		}

		if (!this.simplified || index != 0) {
			sb.append('(');
		}
		for (int i = 0; i < cur.children.size(); i++) {
			ExpTreeNode node = cur.children.get(i);
			toString(node, sb, i);
		}
		if (!this.simplified || index != 0) {
			sb.append(')');
		}
	}

	public static void main(String[] args) {
		ExpressionTree t = new ExpressionTree("(AB) C((D E)F)");
		System.out.println(t);
		t.reverse(t.root);
		System.out.println(t);
		t.reverse(t.root);
		System.out.println(t);
		t.simplified = true;
		System.out.println(t);
		t.reverse(t.root);
		System.out.println(t);
	}