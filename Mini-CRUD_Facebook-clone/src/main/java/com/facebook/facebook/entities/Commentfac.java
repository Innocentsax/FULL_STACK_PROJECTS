package com.facebook.facebook.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Commentfac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime timestamp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private FacebookUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ToString.Exclude
    @OneToMany(mappedBy = "commentfac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    public Commentfac(String text,FacebookUser user, Post post) {
        this.text = text;
        this.user = user;
        this.post = post;
    }

}
