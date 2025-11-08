import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
    int frequency = 0;
}

class Trie {
    TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEnd = true;
        node.frequency++;
    }

    List<String> getSuggestions(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }
        List<String> results = new ArrayList<>();
        collect(node, prefix, results);
        return results;
    }

    private void collect(TrieNode node, String word, List<String> results) {
        if (node.isEnd) results.add(word + " (" + node.frequency + ")");
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collect(entry.getValue(), word + entry.getKey(), results);
        }
    }
}

public class ProductAutocomplete {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Sample product list (you can add more)
        String[] products = {"apple", "app", "application", "apply", "banana", "band", "bat"};
        for (String p : products) trie.insert(p);

        // Simulate user searches (frequency tracking)
        trie.insert("app");
        trie.insert("app");
        trie.insert("apply");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter search prefix: ");
        String prefix = sc.nextLine();

        List<String> suggestions = trie.getSuggestions(prefix);
        if (suggestions.isEmpty())
            System.out.println("No suggestions found.");
        else
            System.out.println("Suggestions: " + suggestions);
    }
}
