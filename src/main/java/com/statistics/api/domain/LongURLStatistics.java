package com.statistics.api.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="long_url_statistics")
public class LongURLStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="long_url", columnDefinition = "varchar(1337)")
    private String longURL;

    @OneToMany(mappedBy = "longURL", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShortURLStatistics> shortURLs = new ArrayList<>();

}
