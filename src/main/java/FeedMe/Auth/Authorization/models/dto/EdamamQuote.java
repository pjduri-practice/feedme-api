package FeedMe.Auth.Authorization.models.dto;

import FeedMe.Auth.Authorization.models.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class EdamamQuote {

    private String uri;
    private int from;
    private int to;
    private int count;
    private Object _links;
    private List<Object> hits;

    public EdamamQuote() {
    }

    public EdamamQuote(String uri, int from, int to, int count, Object _links, List<Object> hits) {
        this.uri = uri;
        this.from = from;
        this.to = to;
        this.count = count;
        this._links = _links;
        this.hits = hits;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }

    public List<Object> getHits() {
        return hits;
    }

    public void setHits(List<Object> hits) {
        this.hits = hits;
    }
}
