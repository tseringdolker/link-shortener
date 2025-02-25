import java.util.*;

public class LinkShortener {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private Map<String, String> urlMap = new HashMap<>(); // long to short URL
    private Map<String, String> reverseMap = new HashMap<>(); // short to long URL
    private Map<String, Integer> accessCount = new HashMap<>(); // short URL access count
    private Random random = new Random();
    
    // Generate a short URL
    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            shortUrl.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return shortUrl.toString();
    }
    
    // Shorten URL
    public String shortenUrl(String longUrl, String customShortUrl) {
        if (urlMap.containsKey(longUrl)) {
            return urlMap.get(longUrl);
        }
        
        if (customShortUrl != null && !customShortUrl.isEmpty()) {
            if (reverseMap.containsKey(customShortUrl)) {
                return "Custom short URL already exists!";
            }
            urlMap.put(longUrl, customShortUrl);
            reverseMap.put(customShortUrl, longUrl);
            accessCount.put(customShortUrl, 0);
            return customShortUrl;
        }
        
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (reverseMap.containsKey(shortUrl)); // Ensure uniqueness
        
        urlMap.put(longUrl, shortUrl);
        reverseMap.put(shortUrl, longUrl);
        accessCount.put(shortUrl, 0);
        return shortUrl;
    }
    
    // Expand short URL
    public String expandUrl(String shortUrl) {
        if (reverseMap.containsKey(shortUrl)) {
            accessCount.put(shortUrl, accessCount.get(shortUrl) + 1); // Increment access count
            return reverseMap.get(shortUrl);
        }
        return "Invalid short URL";
    }
    
    // Display access statistics
    public void displayStatistics(String shortUrl) {
        if (accessCount.containsKey(shortUrl)) {
            System.out.println("Short URL: " + shortUrl + " has been accessed " + accessCount.get(shortUrl) + " times.");
        } else {
            System.out.println("Short URL not found.");
        }
    }
    
    public static void main(String[] args) {
        LinkShortener shortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nChoose an option: \n1. Shorten URL \n2. Expand URL \n3. View Statistics \n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.println("Enter the long URL:");
                    String longUrl = scanner.nextLine();
                    System.out.println("Enter a custom short URL (or press Enter to generate one):");
                    String customShortUrl = scanner.nextLine();
                    String result = shortener.shortenUrl(longUrl, customShortUrl.isEmpty() ? null : customShortUrl);
                    System.out.println("Shortened URL: " + result);
                    break;
                case 2:
                    System.out.println("Enter the short URL:");
                    String shortInput = scanner.nextLine();
                    System.out.println("Original URL: " + shortener.expandUrl(shortInput));
                    break;
                case 3:
                    System.out.println("Enter the short URL to view statistics:");
                    String statInput = scanner.nextLine();
                    shortener.displayStatistics(statInput);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}