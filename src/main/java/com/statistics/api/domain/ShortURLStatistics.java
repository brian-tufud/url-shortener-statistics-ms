package com.statistics.api.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "short_url_statistics")
public class ShortURLStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url")
    private String shortURL;

    @Column(name = "times_used")
    private Integer timesUsed;

    @ManyToOne()
    @JoinColumn(name = "long_url")
    private LongURLStatistics longURL;

    @OneToMany(mappedBy = "shortURLStatistics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientLocationInformation> connections = new ArrayList<>();
}
