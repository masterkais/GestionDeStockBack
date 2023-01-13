package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Article findByCodeArticle(String code);

    @Query("select a from Article a where a.codeArticle = :code and a.designation = :designation")
    List<Article> findByCustomQuery(@Param("code") String code, @Param("designation") String designation);

    @Query(value="select * from article  where codearticle = :code and designation = :designation",nativeQuery = true)
    List<Article> findByCustomNativeQuery(@Param("code") String code, @Param("designation") String designation);


}
