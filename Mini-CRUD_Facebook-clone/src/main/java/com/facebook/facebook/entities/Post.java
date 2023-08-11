package com.facebook.facebook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facebook_user_id", nullable = false)
    private FacebookUser user;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentfac> commentfacs = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @Column
    private LocalDateTime timestamp;
    @Column(nullable = false)
    private Integer likeCount = 0;
}
