package com.tasksprints.auction.domain.auction.repository;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomAuctionRepositoryV1 {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Auction> findByCategoryUsingEntityManager(AuctionCategory auctionCategoryName) {
        String jpql = "SELECT a FROM auction a WHERE a.auctionCategory = :category";
        return entityManager.createQuery(jpql, Auction.class)
            .setParameter("category", auctionCategoryName)
            .getResultList();
    }
}
