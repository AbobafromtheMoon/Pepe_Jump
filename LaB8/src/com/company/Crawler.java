package com.company;
import java.net.*;
import java.util.*;
import java.util.regex.*;

class Crawler
{
    public static void main(String[] args) {
        if (args.length != 2)
        {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        String url = args[0];
        int depth = 0;
        try { depth = Integer.parseInt(args[1]); }
        catch (Exception e)
        {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        Crawler crawler = new Crawler(url, depth);
        crawler.find();
    }
    private HashMap<String, URLDepthPair> links = new HashMap<>(); // массив пар переменных string и экземпл. класса URLDepthPair
    private LinkedList<URLDepthPair> pool = new LinkedList<>(); // пул ссылок
    int depth = 0;

    public Crawler(String url, int depth)
    {
        this.depth = depth;
        pool.add(new URLDepthPair(url, 0));
    }
    public void find() {
        int t = Thread.activeCount(); // Кол-во активных потоков
        while (pool.size() > 0 || Thread.activeCount() > t) // Пока пул не пустой и кол-во активных потоков больше t
        {
            if (pool.size() > 0) {
                URLDepthPair link = pool.pop(); // создаем экземпляр класса и кладем в него самый верхний элемент из пула ссылок
                CrawlerThread task = new CrawlerThread(link); // создаем новый объект и передаем в конструктор объекта ссылку
                Thread thread = new Thread(task); // создаем новый вычислительный поток
                thread.start(); // запускаем поток (Многопоточность реализована - для каждый ссылки новый поток )
            }
        }
        System.out.println("\nНайдено: " + links.size() + "\n");
        for (URLDepthPair link : links.values())
            System.out.println(link);
    }
    private class CrawlerThread implements Runnable
    {
        private URLDepthPair link;
        public CrawlerThread(URLDepthPair link_)
        {
            link = link_;
        }

        @Override
        public void run() // метод поиска ссылки в тексте
        {
            if (links.containsKey(link.getURL())) return;
            links.put(link.getURL(), link);
            if (link.getDepth() >= depth) return;
            try {
                URL url = new URL(link.getURL());
                HttpURLConnection con = (HttpURLConnection) url.openConnection(); // установка HTTp соединения
                con.setRequestMethod("GET");
                Scanner scan = new Scanner(con.getInputStream()); // сканер для поиска ссылок на странице
                Pattern LINK_REGEX = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1"); /* регулярное выражение(используется класс pattern и метод match, применяемы к сканеру)
                 мета - символы для поиска тега ссылки в HTML коде(в скобках) */
                while (scan.findWithinHorizon(LINK_REGEX, 0) != null) { // Цикл для поиска ссылок
                    String newURL = scan.match().group(2);
                    if (newURL.startsWith("/"))
                        newURL = link.getURL() + newURL;
                    else if (!newURL.startsWith("http"))
                        continue;
                    URLDepthPair newLink = new URLDepthPair(newURL, link.getDepth() + 1); // помещаем новую найденную ссылку и глубину + 1,
                    pool.add(newLink);// добавляем этот объект в пулл
                }
            } catch (Exception e) {}
        }
    }
}
