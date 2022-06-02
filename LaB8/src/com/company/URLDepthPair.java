package com.company;

class URLDepthPair {
    private String url;
    private int depth;
    public URLDepthPair(String url, int depth)
    {
        this.url = url;
        this.depth = depth;
    }
    public String getURL() {
        return url;
    }
    public int getDepth() {
        return depth;
    }
    public String toString() { // Метод string для представления рез-ов в консоле
        return "Глубина: " + depth + "\t  URL: " + url;
    }
}
