package com.example.animeproducer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Anime {
    @JsonProperty("mal_id")
    private String malId;
    private String title;
    private String synopsis;
    private double score;
    private int members;
    private Broadcast broadcast;
    private List<Studio> studios;
    @JsonProperty("images")
    private Images images;
    @JsonProperty("url")
    private String url;
    private String status;

    public String getMalId() {
        return malId;
    }

    public void setMalId(String malId) {
        this.malId = malId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

@Data
class Broadcast {
    private String day;
    private String time;
    private String timezone;
    private String string;
}

@Data
class Studio {
    private String name;
}

@Data
class Images {
    private ImageType jpg;
    private ImageType webp;
}

@Data
class ImageType {
    private String image_url;
    private String small_image_url;
    private String large_image_url;
}
